(ns think-bayes.posterior-tests
  (:require (clojure [test :refer :all])
            [think-bayes.posterior :as posterior]))

(deftest normalizing-constant-test
  (testing "Empty prior"
    (let [prior {}
          likelihood {}]
      (is (= 0 (posterior/normalizing-constant :none prior likelihood))))))

(deftest cookie-problem-test
  (let [prior {:bowl1 (/ 1 2) :bowl2 (/ 1 2)}
        likelihood {:vanilla {:bowl1 (/ 3 4) :bowl2 (/ 1 2)}
                    :chocolate {:bowl1 (/ 1 4) :bowl2 (/ 1 2)}}]
    (testing "Normalizing constant"
      (is (= (/ 5 8) (posterior/normalizing-constant :vanilla prior likelihood)))
      (is (= (/ 3 8) (posterior/normalizing-constant :chocolate prior likelihood))))
    (testing "Posterior"
      (is (= (/ 3 5) (posterior/posterior :vanilla :bowl1 prior likelihood)))
      (is (= (/ 2 5) (posterior/posterior :vanilla :bowl2 prior likelihood)))
      (is (= (/ 1 3) (posterior/posterior :chocolate :bowl1 prior likelihood)))
      (is (= (/ 2 3) (posterior/posterior :chocolate :bowl2 prior likelihood))))
    (testing "Datum-hypothesis pairs"
      (is (= [[:vanilla :bowl1] [:vanilla :bowl2] [:chocolate :bowl1] [:chocolate :bowl2]]
             (posterior/datum-hypothesis-pairs likelihood))))
    (testing "Posterior distribution"
      (is (= {:vanilla {:bowl1 (/ 3 5) :bowl2 (/ 2 5)}
              :chocolate {:bowl1 (/ 1 3) :bowl2 (/ 2 3)}}
             (posterior/posterior-distribution prior likelihood))))))
