(ns think-bayes.train-test
  (:require [think-bayes.train :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]
            [think-bayes.suite :as suite]
            [think-bayes.test-helpers :as th]))

(t/deftest train-uniform-prior-test
  (let [priors-1000 (sut/uniform-priors 1 1000)]
    (t/testing "sut.priors"
      (t/is (= (repeat 1000 (/ 1 1000))
               (map #(pmf/probability priors-1000 %) (keys priors-1000)))))
    (t/testing "sut.posteriors"
      (let [posteriors-1000 (sut/posteriors priors-1000 60)]
        (doseq [hypothesis (filter #(< % 60) (keys posteriors-1000))]
          (t/is (= 0.0 (pmf/probability posteriors-1000 hypothesis))))
        (t/is (th/float= 0.005905417875 (pmf/probability posteriors-1000 60) 1e-9))
        (t/is (th/float= 3.543250725437914e-4 (pmf/probability posteriors-1000 1000) 1e-9))
        (let [priors-500 (sut/uniform-priors 1 500)
              posteriors-500 (sut/posteriors priors-500 60)
              priors-2000 (sut/uniform-priors 1 2000)
              posteriors-2000 (sut/posteriors priors-2000 60)]
          (t/testing "sut.mean"
            (t/are [x y] (= x (int (Math/rint (pmf/mean y))))
              333 posteriors-1000
              207 posteriors-500
              552 posteriors-2000))
          (let [data-seq [60, 30, 90]
                posteriors-seq-500 (suite/posteriors-seq priors-500 data-seq sut/likelihood)
                posteriors-seq-1000 (suite/posteriors-seq priors-1000 data-seq sut/likelihood)
                posteriors-seq-2000 (suite/posteriors-seq priors-2000 data-seq sut/likelihood)]
            (t/testing "closer means with more observations"
              (t/are [x y] (= x (int (Math/rint (pmf/mean (second (last y))))))
                152 posteriors-seq-500
                164 posteriors-seq-1000
                171 posteriors-seq-2000))))))))
