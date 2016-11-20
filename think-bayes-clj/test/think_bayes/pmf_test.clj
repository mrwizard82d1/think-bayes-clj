(ns think-bayes.pmf-test
  (:require [clojure.test :refer :all]
            [think-bayes.pmf :refer :all]))

(deftest pmf-test
  (testing "Verify `set-probability`"
    (let [empty-pmf {}]
      (is (= 3 ((set-probability empty-pmf :dont_care 3) :dont_care))))))
