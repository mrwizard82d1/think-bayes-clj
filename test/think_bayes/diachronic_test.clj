(ns think-bayes.diachronic-test
  (:require [midje.sweet :refer :all]
            [think-bayes.diachronic :as tbdc]))

(facts "cookie problem"
  (fact "diachronic returns 3/5"
    (tbdc/posterior (/ 1 2) (/ 3 4) (/ 5 8)) => (/ 3 5)))
