(ns think-bayes.monty-hall-test
  (:require [think-bayes.monty-hall :as sut]
            [clojure.test :as t]))

(t/deftest monty-hall-test
  (t/testing "Verify `should-switch-if-monty-picks-random`"
    (t/is (= (/ 2 3) (sut/should-switch-if-monty-picks-random))))
  (t/testing "Verify `should-switch-if-monty-always-picks-b`"
    (t/is (= (/ 1 2) (sut/should-switch-if-monty-always-picks-b)))))
