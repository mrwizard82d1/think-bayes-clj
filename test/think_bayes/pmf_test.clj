(ns think-bayes.pmf-test
  (:require [midje.sweet :refer :all]
            [think-bayes.pmf :as pmf]))

(facts "Pmf (Probability mass function)"
  (fact "Set single probability for single value."
    ((pmf/set-probability {} :a (/ 1 6)) :a) => (/ 1 6)
    ((pmf/set-probability {} :a (/ 1 6)) :b) => nil
    ((pmf/set-probability {:a (/ 1 5)} :a (/ 1 6)) :a) => (/ 1 6)))
