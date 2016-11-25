(ns think-bayes.dice-test
  (:require [think-bayes.dice :as sut]
            [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

;; The following definition is from "The Clojure Cookbook" (http://gettingclojure.wikidot.com/cookbook:numbers).
(defn float= [x y]
  (let [epsilon 0.000000000001
        scale (if (or (zero? x) (zero? y)) 1 (Math/abs x))]
    (<= (Math/abs (- x y)) (* scale epsilon))))

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
        (t/is (= 0 (pmf/probability posteriors 4)))
        (t/is (= (/ 20 51) (pmf/probability posteriors 6)))
        (t/is (= (/ 5 17) (pmf/probability posteriors 8)))
        (t/is (= (/ 10 51) (pmf/probability posteriors 12)))
        (t/is (= (/ 2 17) (pmf/probability posteriors 20)))))
    (t/testing "dice/posteriors-seq"
      (let [actual-seq (sut/posteriors-seq priors [6 6 8 7 7 5 4])]
        (t/is (= [6 {4 0 
                     6 (/ 20 51) 
                     8 (/ 5 17) 
                     12 (/ 10 51) 
                     20 (/ 2 17)}] (nth actual-seq 1)))
        (let [actual-values [0.0 0.0 0.943248453672 0.0552061280613 0.0015454182665]
              expect-values (vals (second actual-seq))]
          (map #(t/is (float= %1 %2)) actual-values expect-values))))))
