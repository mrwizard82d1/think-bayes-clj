(ns think-bayes.cookies-eat-test
  (:require [think-bayes.cookies-eat :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest cookies-eat-test
  (t/testing "cookies-eat/likelihood (results and side-effects)"
    (let [first (sut/likelihood! :vanilla :bowl-1)]
      (t/is (= (/ 3 4) first))
      (t/is (= 29 (get-in @sut/bowls [:bowl-1 :vanilla])))
      (let [second (sut/likelihood! :chocolate :bowl-1)]
        (t/is (= (/ 10 39) second))
        (t/is (= 9 (get-in @sut/bowls [:bowl-1 :chocolate])))
        (let [third (sut/likelihood! :chocolate :bowl-1)]
          (t/is (= (/ 9 38) third))
          (t/is (= 8 (get-in @sut/bowls [:bowl-1 :chocolate])))))))
  (t/testing "cookies-eat/posteriors"
    ;; Recreate the initial stateful `sut/bowls`
    (reset! sut/bowls sut/initial-bowls)
    (let [first (sut/posteriors sut/priors :vanilla)]
      (t/is (= (/ 3 5) (pmf/probability first :bowl-1)))
      (t/is (= (/ 2 5) (pmf/probability first :bowl-2)))
      (let [second (sut/posteriors first :chocolate)]
        (t/is (= (/ 3 7) (pmf/probability second :bowl-1)))
        (t/is (= (/ 4 7) (pmf/probability second :bowl-2)))
        (let [third (sut/posteriors second :vanilla)]
          (t/is (= (/ 87 163) (pmf/probability third :bowl-1)))
          (t/is (= (/ 76 163) (pmf/probability third :bowl-2))))))))

