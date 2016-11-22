(ns think-bayes.cookies-test
  (:require [clojure.test :refer :all]
            [think-bayes.cookies :refer :all]
            [think-bayes.pmf :as pmf]))

(deftest cookies-test
  (testing "Verify p('Bowl 1' | 'vanilla cookie'"
    (is (= (/ 3 5) (probability-bowl-1-given-vanilla))))
  (testing "Verify p('Bowl 1' | 'vanilla cookie'"
    (let [priors (-> {}
                     (pmf/set-probability "Bowl 1" (/ 1 2))
                     (pmf/set-probability "Bowl 2" (/ 1 2)))
          unnormalized (-> priors
                           (pmf/scale "Bowl 1" (/ 3 4))
                           (pmf/scale "Bowl 2" (/ 1 2)))
          posteriors (pmf/normalize unnormalized)]
      (is (= (/ 3 5) (pmf/probability posteriors "Bowl 1")))
      (is (= (/ 2 5) (pmf/probability posteriors "Bowl 2"))))))
