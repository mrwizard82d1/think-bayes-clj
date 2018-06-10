(ns think-bayes.ch01-test
  (:require [midje.sweet :refer :all]
            [think-bayes.ch01 :as tb.ch01]))

(facts "raw cookie problem"
  (fact "it returns 3/5"
    (tb.ch01/cookie) => (/ 3 5)))
