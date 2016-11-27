(ns think-bayes.monty-hall-suite-test
  (:require [think-bayes.monty-hall-suite :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest monty-hall-suite-test
  (t/testing "monty-hall-suite/posteriors"
    (let [posteriors (sut/posteriors :b)]
      (t/are [e h] (= e (pmf/probability posteriors h))
        (/ 1 3) :a
        0 :b
        (/ 2 3) :c))))
  
