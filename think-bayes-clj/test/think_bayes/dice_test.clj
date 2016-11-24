(ns think-bayes.dice-test
  (:require [think-bayes.dice :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest dice-test
  (t/testing "dice/likelihood tests"
    (t/is (= (/ 1 6) (sut/likelihood 6 6)))
    (t/is (= 0 (sut/likelihood 7 6))))
  (t/testing "dice/priors"
    (let [hypotheses [4, 6, 8, 12, 20]
          priors (sut/priors hypotheses)]
      (t/is (= (repeat 5 (/ 1 5))
               (map #(pmf/probability priors %1) hypotheses)))
      (t/testing "dice/posteriors"
        (let [posteriors (sut/posteriors priors 6)]
          (t/is (= 0 (pmf/probability posteriors 4)))
          (t/is (= (/ 20 51) (pmf/probability posteriors 6)))
          (t/is (= (/ 5 17) (pmf/probability posteriors 8)))
          (t/is (= (/ 10 51) (pmf/probability posteriors 12)))
          (t/is (= (/ 2 17) (pmf/probability posteriors 20))))))))
