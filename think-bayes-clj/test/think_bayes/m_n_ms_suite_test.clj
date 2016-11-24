(ns think-bayes.m-n-ms-suite-test
  (:require [think-bayes.m-n-ms-suite :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest m-n-ms-suite-test
  (t/testing "m-n-ms-suite/likelihood"
    (t/is (= 20 (sut/likelihood [:bag-1 :yellow] sut/hypothesis-a)))
    (t/is (= 14 (sut/likelihood [:bag-2 :yellow] sut/hypothesis-a)))
    (t/is (= 10 (sut/likelihood [:bag-1 :tan] sut/hypothesis-a)))
    (t/is (= nil (sut/likelihood [:bag-2 :tan] sut/hypothesis-a)))
    (t/is (= 14 (sut/likelihood [:bag-1 :yellow] sut/hypothesis-b)))
    (t/is (= 20 (sut/likelihood [:bag-2 :yellow] sut/hypothesis-b)))
    (t/is (= nil (sut/likelihood [:bag-1 :tan] sut/hypothesis-b)))
    (t/is (= 10 (sut/likelihood [:bag-2 :tan] sut/hypothesis-b))))
  (t/testing "m-n-ms-suite/posterior when selected 'yellow' and 'green': one from each bag."
    (let [priors (sut/priors)
          posteriors (-> priors
                         (sut/posteriors [:bag-1 :yellow])
                         (sut/posteriors [:bag-2 :green]))]
      (t/is (= (/ 20 27) (pmf/probability posteriors sut/hypothesis-a)))
      (t/is (= (/ 7 27) (pmf/probability posteriors sut/hypothesis-b))))))
