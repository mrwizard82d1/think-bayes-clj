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
      (pmf/normalize pmf) => {"the" (/ 2 10)
                              "quick" (/ 1 10)
                              "little" (/ 1 10)
                              "brown" (/ 1 10)
                              "fox" (/ 1 10)
                              "jumped" (/ 1 10)
                              "over" (/ 1 10)
                              "lazy" (/ 1 10)
                              "grey" (/ 1 10)
                              "lambs" (/ 1 10)}))
  (fact "Return the probability of a value."
    (let [pmf (pmf/set-probabilities {} 
                                     (zipmap (range 1 5) (map #(/ % 10) (range 1 5))))]
      (pmf/probability pmf 1) => (/ 1 10)
      (pmf/probability pmf 2) => (/ 1 5)
      (pmf/probability pmf 3) => (/ 3 10)
      (pmf/probability pmf 4) => (/ 2 5))))

