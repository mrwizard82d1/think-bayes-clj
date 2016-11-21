(ns think-bayes.m-n-ms-test
  (:require [clojure.test :refer :all]
            [think-bayes.m-n-ms :refer :all]))

(deftest m-n-ms-test
  (testing "Verify p('Bowl 1' | 'vanilla cookie'"
    (is (= (/ 3 5) (probability-bowl-1-given-vanilla))))
  (testing "Verify p('Bag 1 is 1994' | 'yellew M&M'"
    (is (= (/ 20 27) (probability-yellow-1994)))))
