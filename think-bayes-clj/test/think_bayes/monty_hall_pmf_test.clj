(ns think-bayes.monty-hall-pmf-test
  (:require [think-bayes.monty-hall-pmf :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest monty-hall-pmf-test
  (let [priors (sut/priors [:a :b :c])]
    (t/testing "monty-hall-pmf/priors"
      (t/is (= (repeat 3 (/ 1 3))
               (vals priors))))
   (t/testing "monty-hall-pmf/posteriors"
     (let [posteriors (sut/posteriors priors :b sut/likelihood)]
       (t/is (= (/ 2 3) (pmf/probability posteriors :c)))
       (t/is (= (/ 1 3) (pmf/probability posteriors :a)))))))
