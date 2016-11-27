(ns think-bayes.core-test
  (:require [clojure.test :refer :all]
            [think-bayes.core :refer :all]))

(deftest bayes-law-test
  (testing "Verify implementation of Bayes' law"
    (is (= (/ 3 5) (bayes-law (/ 1 2) (/ 3 4) (/ 5 8))))))

(deftest posterior-test
  (testing "`posterior` with known values"
    (let [hypotheses [:bowl1 :bowl2]
          priors {:bowl1 (/ 1 2) :bowl2 (/ 1 2)}
          likelihoods {[:vanilla :bowl1] (/ 3 4)
                       [:vanilla :bowl2] (/ 1 2)
                       [:chocolate :bowl1] (/ 1 4)
                       [:chocolate :bowl2] (/ 1 2)}]
      (are [x y z] (= x ((posteriors hypotheses y priors likelihoods) z))
        (/ 3 5) :vanilla :bowl1
        (/ 2 5) :vanilla :bowl2
        (/ 1 3) :chocolate :bowl1
        (/ 2 3) :chocolate :bowl2))))
 
