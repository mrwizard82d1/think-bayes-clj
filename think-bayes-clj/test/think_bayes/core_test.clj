(ns think-bayes.core-test
  (:require [clojure.test :refer :all]
            [think-bayes.core :refer :all]))

(deftest bayes-law-test
  (testing "Verify implementation of Bayes' law"
    (is (= (/ 3 5) (bayes-law (/ 1 2) (/ 3 4) (/ 5 8))))))
 
