(ns think-bayes.table-test
  (:require [midje.sweet :refer :all]
            [think-bayes.table :as tbt]))

(facts "Bayes' table method"
  (fact "M&M problem' table method"
    (tbt/mnm-posteriors [:yellow :green]) => {:mnm-mix-1994 (/ 20 27)
                                              :mnm-mix-1996 (/ 7 27)}))
