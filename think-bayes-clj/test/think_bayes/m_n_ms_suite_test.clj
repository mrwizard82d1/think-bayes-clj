(ns think-bayes.m-n-ms-suite-test
  (:require [think-bayes.m-n-ms-suite :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest m-n-ms-suite-test
  (t/testing "m-n-ms-suite/likelihood"
    (t/are [x y z w] (= (sut/likelihood [y z] w))
      20 :bag-1 :yellow sut/hypothesis-a
      14 :bag-2 :yellow sut/hypothesis-a
      10 :bag-1 :tan sut/hypothesis-a
      nil :bag-2 :tan sut/hypothesis-a
      14 :bag-1 :yellow sut/hypothesis-b
      20 :bag-2 :yellow sut/hypothesis-b
      nil :bag-1 :tan sut/hypothesis-b
      10 :bag-2 :tan sut/hypothesis-b))
  (t/testing "m-n-ms-suite/posterior when selected 'yellow' and 'green': one from each bag."
    (let [priors (sut/priors)
          posteriors (-> priors
                         (sut/posteriors [:bag-1 :yellow])
                         (sut/posteriors [:bag-2 :green]))]
      (t/is (= (/ 20 27) (pmf/probability posteriors sut/hypothesis-a)))
      (t/is (= (/ 7 27) (pmf/probability posteriors sut/hypothesis-b))))))
