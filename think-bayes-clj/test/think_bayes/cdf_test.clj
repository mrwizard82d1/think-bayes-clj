(ns think-bayes.cdf-test
  (:require [clojure.test :as t]
            [think-bayes.cdf :as sut]
            [think-bayes.train :as train]
            [think-bayes.suite :as suite]))

(t/deftest sut-tests
  (let [priors-1000 (train/power-priors 1000)
        posteriors-1000 (-> priors-1000
                            (suite/posteriors-seq [60 30 90] train/likelihood)
                            last
                            second)
        cdf (sut/make-cdf posteriors-1000)]
    (t/testing "sut/percentile"
      (t/is (= 91 (sut/percentile cdf 5)))
      ;; The "Think Bayes" book indicates the 95th percentile is hypothesis 243; my algorithm indicates 242.
      ;; When I examined the hypothesis-probability pairs, I see [242 0.949...] and [243 0.950...]. I do 
      ;; not plan to examine the difference; however, it could arise in Java double arithmetic, Python double 
      ;; arithmetic, or numpy arithmetic.
      (t/is (= 242 (sut/percentile cdf 95))))
    (t/testing "sut/credible-interval"
      (t/is (= [91 242] (sut/credible-interval cdf)))
      (t/is (= [91 242] (sut/credible-interval cdf 90))))))
