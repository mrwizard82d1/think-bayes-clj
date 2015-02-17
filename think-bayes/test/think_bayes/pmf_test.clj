(ns think-bayes.pmf-test
  (:require [clojure.test :refer :all]
            [think-bayes.pmf :refer :all]))

(deftest pmf-test
  (testing "Empty PMF"
    (is (= {1 (/ 1 6)}
           (set-mass {} 1 (/ 1 6)) )))
  (testing "Fill PMF"
    (is (= {1 1 2 (/ 1 2) 3 (/ 1 3)
            4 (/ 1 4) 5 (/ 1 5) 6 (/ 1 6)}
           (reduce #(set-mass %1 %2 (/ 1 %2)) {} (range 1 (inc 6))))))
  (testing "Non-empty PMF"
    (is (= {1 (/ 1 6) 2 (/ 1 3)}
           (set-mass {1 (/ 1 6)} 2 (/ 1 3))
           ))))
