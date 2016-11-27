(ns think-bayes.dice-test
  (:require [think-bayes.dice :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]
            [think-bayes.test-helpers :as th]))

(t/deftest dice-test
  (t/testing "dice/likelihood tests"
    (t/is (= (/ 1 6) (sut/likelihood 6 6)))
    (t/is (= 0 (sut/likelihood 7 6))))
  (let [hypotheses [4 6 8 12 20]
        priors (sut/priors hypotheses)]
    (t/testing "dice/priors"
      (t/is (= (repeat 5 (/ 1 5))
               (map #(pmf/probability priors %1) hypotheses))))
    (t/testing "dice/posteriors"
      (let [posteriors (sut/posteriors priors 6)]
        (t/are [x y] (= x (pmf/probability posteriors y))
          0 4
          (/ 20 51) 6
          (/ 5 17) 8
          (/ 10 51) 12
          (/ 2 17) 20)))
    (t/testing "dice/posteriors-seq"
      (let [actual-seq (sut/posteriors-seq priors [6 6 8 7 7 5 4])]
        (t/is (= [6 {4 0 
                     6 (/ 20 51) 
                     8 (/ 5 17) 
                     12 (/ 10 51) 
                     20 (/ 2 17)}] (nth actual-seq 1)))
        (let [actual-values [0.0 0.0 0.943248453672 0.0552061280613 0.0015454182665]
              expect-values (vals (second actual-seq))]
          (map #(t/is (th/float= %1 %2 0.000000000001)) actual-values expect-values))))))
