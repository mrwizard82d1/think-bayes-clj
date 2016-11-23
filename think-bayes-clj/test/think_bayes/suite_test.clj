(ns think-bayes.suite-test
  (:require  [think-bayes.suite :as sut]
             [clojure.test :as t]
             [think-bayes.pmf :as pmf]))


(t/deftest suite-test
  (t/testing "suite/priors"
    (t/is (= 1 (pmf/probability (sut/priors [:single-hypothesis]) :single-hypothesis)))
    (let [hypotheses [:a :b :c]
          priors (sut/priors hypotheses)]
      (t/is (= (repeat 3 (/ 1 3))
               (map #(pmf/probability priors %1) hypotheses)))))
  (t/testing "suite/posteriors"
    (let [hypotheses [:bowl-1 :bowl-2]
          priors (sut/priors hypotheses)]
      (let [mixes {:bowl-1 {:vanilla (/ 3 4)
                        :chocolate (/ 1 4)}
               :bowl-2 {:vanilla (/ 1 2)
                        :chocolate (/ 1 2)}}
            likelihood (fn [data hypothesis]
                         (get-in mixes [hypothesis data]))]  
        (t/testing "with vanilla data"
          (let [posteriors (sut/posteriors priors :vanilla likelihood)]
            (t/is (= (/ 3 5) (pmf/probability posteriors :bowl-1)))
            (t/is (= (/ 2 5) (pmf/probability posteriors :bowl-2)))
            (t/testing "with vanilla-chocolate data"
              (let [vanilla-chocolate-posteriors (sut/posteriors posteriors :chocolate likelihood)]
                (t/is (= (/ 3 7) (pmf/probability vanilla-chocolate-posteriors :bowl-1)))
                (t/is (= (/ 4 7) (pmf/probability vanilla-chocolate-posteriors :bowl-2)))
                (t/testing "with vanilla-chocolate-vanilla data"
                  (let [vanilla-chocolate-vanilla-posteriors 
                        (sut/posteriors vanilla-chocolate-posteriors :vanilla likelihood)]
                    (t/is (= (/ 9 17) (pmf/probability vanilla-chocolate-vanilla-posteriors :bowl-1)))
                    (t/is (= (/ 8 17) (pmf/probability vanilla-chocolate-vanilla-posteriors :bowl-2)))))))))
        (t/testing "with chocolate data"
          (let [posteriors (sut/posteriors priors :chocolate likelihood)]
            (t/is (= (/ 1 3) (pmf/probability posteriors :bowl-1)))
            (t/is (= (/ 2 3) (pmf/probability posteriors :bowl-2)))))))))
