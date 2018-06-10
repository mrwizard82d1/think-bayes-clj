(ns think-bayes.cookie-test
  (:require [midje.sweet :refer :all]
            [think-bayes.cookie :as tb.cookie]))

(facts "cookie problem"
  (fact "raw-cookie returns 3/5"
    (tb.cookie/raw-cookie) => (/ 3 5)))
