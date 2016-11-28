(ns think-bayes.pmf-test
  (:require [clojure.test :refer :all]
            [think-bayes.pmf :as sut]))

(deftest pmf-test
  (let [empty-pmf {}
        pmf-one-qualitative-key (-> {} (sut/set-probability :a-key 4))
        pmf-many-qualitative-keys (-> {} 
                                      (sut/set-probability :a-key 4)
                                      (sut/set-probability :b-key 9)
                                      (sut/set-probability :c-key 2))]
    (testing "Of empty PMF"
      (is (= 3 (sut/probability (sut/set-probability empty-pmf :dont-care 3) :dont-care)))
      (is (= 1 (sut/probability (sut/inc empty-pmf :a-key) :a-key))))
    (testing "Of PMF with single existing key"
      (let [])
      (is (= 5 (sut/probability (sut/set-probability pmf-one-qualitative-key :a-key 5) :a-key)))
      (is (= 1 (sut/probability (sut/normalize pmf-one-qualitative-key) :a-key)))
      (is (= 24 (sut/probability (sut/scale pmf-one-qualitative-key :a-key 6) :a-key))))
    (testing "Of PMF with many existing key"
      (let [])
      (are [x y] (= x (sut/probability (sut/normalize pmf-many-qualitative-keys) y))
        (/ 4 15) :a-key
        (/ 9 15) :b-key
        (/ 2 15) :c-key)))
  (let [pmf-one-quantitative-key (sut/inc {} 44)
        pmf-many-quantitative-keys (-> {}
                                       (sut/set-probability 4 (/ 1 3))
                                       (sut/set-probability 9 (/ 1 3))
                                       (sut/set-probability 2 (/ 1 3))
                                       (sut/normalize))]
    (testing "Of `sut.mean`"
      (is (= 44 (sut/mean pmf-one-quantitative-key)))
      (is (= 5 (sut/mean pmf-many-quantitative-keys)))))
  
  (let [dice-posteriors (-> {}
                            (sut/set-probability 4 0)
                            (sut/set-probability 6 (/ 20 51))
                            (sut/set-probability 8 (/ 5 17))
                            (sut/set-probability 12 (/ 10 51))
                            (sut/set-probability 20 (/ 2 17)))]
    (testing "sut/percentile, interval, and make-cdf" 
      ;; The following value is the posterior distribution of the dice problem having observed 6.
      (are [eh pct] (= eh (sut/percentile dice-posteriors pct))
        4 0
        6 39.2
        8 68.6
        12 88.2
        20 100))))
