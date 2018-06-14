(ns think-bayes.table-test
  (:require [midje.sweet :refer :all]
            [think-bayes.table :as tbt]))

(facts "Bayes' table method"
  (fact "M&M problem"
    (tbt/mnm-posteriors [:yellow :green]) => {:mnm-mix-1994 (/ 20 27)
                                              :mnm-mix-1996 (/ 7 27)})
  (fact "Monty Hall problem"
    (tbt/monty-hall-posteriors :open-b) => {:behind-a (/ 1 3),
                                            :behind-b 0,
                                            :behind-c (/ 2 3)}))
