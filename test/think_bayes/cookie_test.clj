(ns think-bayes.cookie-test
  (:require [midje.sweet :refer :all]
            [think-bayes.cookie :as tb.cookie]
            [think-bayes.diachronic :as tbdc]))

(facts "cookie problem"
  (fact "raw-cookie returns 3/5"
    (tb.cookie/raw-cookie) => (/ 3 5)) 
  (fact "diachronic returns 3/5"
    (tbdc/posterior (/ 1 2) (/ 3 4) (/ 5 8)) => (/ 3 5)))
