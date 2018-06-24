(ns think-bayes.cookie-eating-test
  (:require [midje.sweet :refer :all]
            [think-bayes.pmf :as pmf]))

(defn make-mixes [bowl-1 bowl-2]
  "Make a mixture of `bowl-1` and `bowl-2`."
  {:bowl-1 bowl-1
   :bowl-2 bowl-2})

(defn make-likelihood [mixes]
  (fn [data hypothesis]
    (/ (get-in mixes [hypothesis data])
       (reduce + (vals (mixes hypothesis))))))

(defn eat-cookie [mixture bowl flavor]
  "Eat a `flavor` cookie from `bowl` in `mixture`."
  (update-in mixture [bowl flavor] dec))

(facts "The cookie problem - but I eat cookies when picked."
       (fact "Draw two vanilla cookies from bowl-1 with probability (/ 29 42)."
             (let [priors (pmf/suite [:bowl-1 :bowl-2])
                   bowl-1 {:vanilla 30, :chocolate 10}
                   bowl-2 {:vanilla 20, :chocolate 20}
                   mixes (make-mixes bowl-1 bowl-2)
                   likelihood (make-likelihood mixes)
                   posteriors-vanilla-bowl-1 (pmf/posteriors priors
                                                             likelihood
                                                             :vanilla)
                   eat-vanilla-bowl-1-mixes (eat-cookie mixes
                                                        :bowl-1
                                                        :vanilla)
                   likelihood-post-vanilla (make-likelihood eat-vanilla-bowl-1-mixes)
                   posteriors
                   (pmf/posteriors posteriors-vanilla-bowl-1
                                   (make-likelihood eat-vanilla-bowl-1-mixes)
                                   :vanilla)]
               (pmf/probability posteriors :bowl-1) => (/ 29 42)
               (pmf/probability posteriors :bowl-2) => (/ 13 42))))

