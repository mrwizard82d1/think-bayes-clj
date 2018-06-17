(ns think-bayes.pmf-test
  (:require [midje.sweet :refer :all]
            [clojure.string :as cstr]
            [think-bayes.pmf :as pmf]))

(facts "Pmf (Probability mass function)"
  (fact "Set single probability for single value."
    ((pmf/set-probability {} :a (/ 1 6)) :a) => (/ 1 6)
    ((pmf/set-probability {} :a (/ 1 6)) :b) => nil
    ((pmf/set-probability {:a (/ 1 5)} :a (/ 1 6)) :a) => (/ 1 6))
  (fact "Set probabilities for multiple values."
    ((pmf/set-probabilities {} (zipmap (range 3) (repeat (/ 1 6)))) 2) => (/ 1 6)
    ((pmf/set-probabilities {} (zipmap (range 3) (repeat (/ 1 6)))) 3) => nil
    ((pmf/set-probabilities (zipmap (range 3) (repeat (/ 1 5)))
                            (zipmap (range 3) (repeat (/ 1 6)))) 2) => (/ 1 6))
  (fact "Increment the probability mass of value."
    ((pmf/increase {} :planum) :planum) => 1
    ((pmf/increase {:maritum 5} :maritum 7) :maritum) => 12)
  (fact "Normalize a probability mass to a probability density."
    (let [word-list 
          (cstr/split "The quick little brown fox jumped over the lazy grey lambs" 
                      #"\s") 
          pmf (->> word-list
                  (map cstr/lower-case)
                  (reduce pmf/increase {}))]
      (pmf/normalize pmf) => {"the" (/ 2 11)
                              "quick" (/ 1 11)
                              "little" (/ 1 11)
                              "brown" (/ 1 11)
                              "fox" (/ 1 11)
                              "jumped" (/ 1 11)
                              "over" (/ 1 11)
                              "lazy" (/ 1 11)
                              "grey" (/ 1 11)
                              "lambs" (/ 1 11)}))
  (fact "Return the probability of a value."
    (let [pmf (pmf/set-probabilities {} 
                                     (zipmap (range 1 5) (map #(/ % 10) (range 1 5))))]
      (pmf/probability pmf 1) => (/ 1 10)
      (pmf/probability pmf 2) => (/ 1 5)
      (pmf/probability pmf 3) => (/ 3 10)
      (pmf/probability pmf 4) => (/ 2 5)))
  (fact "Multiply the probability mass by factor."
    (let [before-multiplication (-> {} 
                                    (pmf/set-probability :a (/ 1 4))
                                    (pmf/set-probability :b (/ 3 4)))
          pmf (pmf/multiply before-multiplication :a (/ 1 2))]
      (pmf/probability pmf :a) => (/ 1 8)
      (pmf/probability pmf :b) => (/ 3 4)))
  (fact "The cookie problem solved manually using PMFs."
    (let [priors (-> {}
                     (pmf/set-probability :bowl-1 (/ 1 2))
                     (pmf/set-probability :bowl-2 (/ 1 2)))
          products (-> priors
                       (pmf/multiply :bowl-1 (/ 30 40))
                       (pmf/multiply :bowl-2 (/ 20 40)))
          posteriors (pmf/normalize products)]
      (pmf/probability posteriors :bowl-1) => (/ 3 5)
      (pmf/probability posteriors :bowl-2) => (/ 2 5))))
