(ns think-bayes.cookies-test
  (:require [clojure.test :refer :all]
            [think-bayes.cookies :refer :all]))

(deftest cookies-test
  (testing "Verify p('Bowl 1' | 'vanilla cookie'"
    (is (= (/ 3 5) (probability-bowl-1-given-vanilla)))))
