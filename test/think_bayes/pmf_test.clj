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
  (fact "Initialize a suite of probablities"
    ((pmf/suite [:a :b]) :a) => (/ 1 2)
    ((pmf/suite (range 5)) 4) => (/ 1 5))
  (fact "Calculate the posteriors given some data."
    (let [priors (pmf/suite (range 0 (inc 10)))
          likelihood (fn [data hypothesis]
                       (if (= data :vanilla)
                         (/ hypothesis (count priors))
                         (- 1 (/ hypothesis (count priors)))))
          posteriors (pmf/posteriors priors likelihood :vanilla)]
      (pmf/probability posteriors 0) => 0
      (pmf/probability posteriors 1) => (/ 1 55)
      (pmf/probability posteriors 2) => (/ 2 55)
      (pmf/probability posteriors 3) => (/ 3 55)
      (pmf/probability posteriors 4) => (/ 4 55)
      (pmf/probability posteriors 5) => (/ 5 55)
      (pmf/probability posteriors 6) => (/ 6 55)
      (pmf/probability posteriors 7) => (/ 7 55)
      (pmf/probability posteriors 8) => (/ 8 55)
      (pmf/probability posteriors 9) => (/ 9 55)
      (pmf/probability posteriors 10) => (/ 10 55))))


(facts "Solving the cookie problems using PMFs."
  (fact "The cookie problem solved manually using PMFs."
    (let [priors (-> {}
                     (pmf/set-probability :bowl-1 (/ 1 2))
                     (pmf/set-probability :bowl-2 (/ 1 2)))
          products (-> priors
                       (pmf/multiply :bowl-1 (/ 30 40))
                       (pmf/multiply :bowl-2 (/ 20 40)))
          posteriors (pmf/normalize products)]
      (pmf/probability posteriors :bowl-1) => (/ 3 5)
      (pmf/probability posteriors :bowl-2) => (/ 2 5)))
  (fact "The cookie problem solved using pmf functions."
    (let [priors (pmf/suite [:bowl-1 :bowl-2])
          mixes {:bowl-1 {:vanilla (/ 30 40)
                          :chocolate (/ 10 40)}
                 :bowl-2 {:vanilla (/ 20 40)
                          :chocolate (/ 20 40)}}
          likelihood (fn [data hypothesis]
                       (get-in mixes [hypothesis data]))
          posteriors (pmf/posteriors priors likelihood :vanilla)]
      (pmf/probability posteriors :bowl-1) => (/ 3 5)
      (pmf/probability posteriors :bowl-2) => (/ 2 5))))

(facts "Solving the Monty Hall problem using suite and posteriors"
  (fact "Probability of doors A, B and C given contestant picks A and Monty picks B."
    (let [priors (pmf/suite [:a :b :c])
          likelihood (fn [data hypothesis]
                       (cond 
                         (= data hypothesis) 0
                         (= hypothesis :a) (/ 1 2)
                         true 1))
          posteriors (pmf/posteriors priors likelihood :b)]
      (pmf/probability posteriors :a) => (/ 1 3)
      (pmf/probability posteriors :b) => 0
      (pmf/probability posteriors :c) => (/ 2 3))))

(facts :mnms "Solving the M&M problems using suite and posteriors"
  (fact "Probability of 1994 and 1996 given yellow and green"
    (let [mix-1994 {:brown 30
                    :yellow 20
                    :red 20
                    :green 10
                    :orange 10
                    :tan 10}
          mix-1996 {:blue 24
                    :green 20
                    :orange 16
                    :yellow 14
                    :red 13
                    :brown 13}
          hypothesis-a {:bag-1 mix-1994,
                        :bag-2 mix-1996}
          hypothesis-b {:bag-1 mix-1996,
                        :bag-2 mix-1994}
          hypotheses {:a hypothesis-a :b hypothesis-b}
          priors (pmf/suite (keys hypotheses))
          likelihood (fn [[bag color] hypothesis]
                       (get-in hypotheses [hypothesis bag color]))
          posteriors (-> priors
                         (pmf/posteriors likelihood [:bag-1 :yellow])
                         (pmf/posteriors likelihood [:bag-2 :green]))]
      (pmf/probability posteriors :a) => (/ 20 27)
      (pmf/probability posteriors :b) => (/ 7 27))))
