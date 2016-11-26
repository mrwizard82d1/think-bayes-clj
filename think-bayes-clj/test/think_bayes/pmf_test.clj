(ns think-bayes.pmf-test
  (:require [clojure.test :refer :all]
            [think-bayes.pmf :as sut]))

(deftest pmf-test
  (testing "Of empty PMF"
    (let [empty-pmf {}]
      (is (= 3 (sut/probability (sut/set-probability empty-pmf :dont-care 3) :dont-care)))
      (is (= 1 (sut/probability (sut/inc empty-pmf :a-key) :a-key)))))
  (testing "Of PMF with single existing key"
    (let [pmf-with-key (-> {} (sut/set-probability :a-key 4))]
      (is (= 5 (sut/probability (sut/set-probability pmf-with-key :a-key 5) :a-key)))
      (is (= 1 (sut/probability (sut/normalize pmf-with-key) :a-key)))
      (is (= 24 (sut/probability (sut/scale pmf-with-key :a-key 6) :a-key)))))
  (testing "Of PMF with many existing key"
    (let [pmf-with-keys (-> {} 
                            (sut/set-probability :a-key 4)
                            (sut/set-probability :b-key 9)
                            (sut/set-probability :c-key 2))]
      (is (= (/ 4 15) (sut/probability (sut/normalize pmf-with-keys) :a-key)))
      (is (= (/ 9 15) (sut/probability (sut/normalize pmf-with-keys) :b-key)))
      (is (= (/ 2 15) (sut/probability (sut/normalize pmf-with-keys) :c-key)))))
  (testing "Of `sut.mean`"
    (let [sut-with-single-key (sut/inc {} 44)
          sut-with-many-keys (-> {}
                                 (sut/set-probability 4 (/ 1 3))
                                 (sut/set-probability 9 (/ 1 3))
                                 (sut/set-probability 2 (/ 1 3))
                                 (sut/normalize))]
      (is (= 44 (sut/mean sut-with-single-key)))
      (is (= 5 (sut/mean sut-with-many-keys))))))
