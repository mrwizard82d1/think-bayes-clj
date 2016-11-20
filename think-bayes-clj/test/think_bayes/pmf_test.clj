(ns think-bayes.pmf-test
  (:require [clojure.test :refer :all]
            [think-bayes.pmf :refer :all]))

(deftest pmf-test
  (testing "Verify `set-probability` of empty PMF"
    (let [empty-pmf {}]
      (is (= 3 (probability (set-probability empty-pmf :dont-care 3) :dont-care)))))
  (testing "Verify `set-probability` of PMF with existing key"
    (let [pmf-with-key (-> {} (set-probability :a-key 4))]
      (is (= 5 (probability (set-probability pmf-with-key :a-key 5) :a-key))))))
