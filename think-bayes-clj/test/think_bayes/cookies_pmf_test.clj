(ns think-bayes.cookies-pmf-test
  (:require [think-bayes.cookies-pmf :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest cookies-pmf-test
  (t/testing "cookies-pmf/priors"
    (t/is (= 1 (pmf/probability (sut/priors [:single-hypothesis]) :single-hypothesis)))
    (let [hypotheses [:a :b :c]
          priors (sut/priors hypotheses)]
      (t/is (= (repeat 3 (/ 1 3))
               (map #(pmf/probability priors %1) hypotheses)))))
  (t/testing "cookies-pmf/likelihood"
    (t/is (= (/ 3 4) (sut/likelihood :vanilla :bowl-1)))
    (t/is (= (/ 1 4) (sut/likelihood :chocolate :bowl-1)))
    (t/is (= (/ 1 2) (sut/likelihood :vanilla :bowl-2)))
    (t/is (= (/ 1 2) (sut/likelihood :chocolate :bowl-2))))
  (t/testing "cookies-pmf/posteriors"
    (let [hypotheses [:bowl-1 :bowl-2]
          priors (sut/priors hypotheses)]
      (t/testing "with vanilla data"
        (let [posteriors (sut/posteriors priors :vanilla sut/likelihood)]
          (t/is (= (/ 3 5) (pmf/probability posteriors :bowl-1)))
          (t/is (= (/ 2 5) (pmf/probability posteriors :bowl-2)))
          (t/testing "with vanilla-chocolate data"
            (let [vanilla-chocolate-posteriors (sut/posteriors posteriors :chocolate sut/likelihood)]
              (t/is (= (/ 3 7) (pmf/probability vanilla-chocolate-posteriors :bowl-1)))
              (t/is (= (/ 4 7) (pmf/probability vanilla-chocolate-posteriors :bowl-2)))
              (t/testing "with vanilla-chocolate-vanilla data"
                (let [vanilla-chocolate-vanilla-posteriors 
                      (sut/posteriors vanilla-chocolate-posteriors :vanilla sut/likelihood)]
                  (t/is (= (/ 9 17) (pmf/probability vanilla-chocolate-vanilla-posteriors :bowl-1)))
                  (t/is (= (/ 8 17) (pmf/probability vanilla-chocolate-vanilla-posteriors :bowl-2)))))))))
      (t/testing "with chocolate data"
        (let [posteriors (sut/posteriors priors :chocolate sut/likelihood)]
          (t/is (= (/ 1 3) (pmf/probability posteriors :bowl-1)))
          (t/is (= (/ 2 3) (pmf/probability posteriors :bowl-2))))))))
