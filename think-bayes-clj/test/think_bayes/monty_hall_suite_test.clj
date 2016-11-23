(ns think-bayes.monty-hall-suite-test
  (:require [think-bayes.monty-hall-suite :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest monty-hall-suite-test
  (t/testing "monty-hall-suite/posteriors"
    (let [posteriors (sut/posteriors :b)]
      (t/is (= (/ 1 3) (pmf/probability posteriors :a)))
      (t/is (= 0 (pmf/probability posteriors :b)))
      (t/is (= (/ 2 3) (pmf/probability posteriors :c))))))
  
