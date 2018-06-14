(ns think-bayes.pmf-test
  (:require [midje.sweet :refer :all]
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
                            (zipmap (range 3) (repeat (/ 1 6)))) 2) => (/ 1 6)))
