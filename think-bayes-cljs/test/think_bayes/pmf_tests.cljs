(ns think-bayes.pmf-tests
  (:require [cljs.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest make-test
  (t/is (not (nil? (pmf/make)))))

(t/deftest set-test
  (let [events (range 1 (inc 3))
        to-test (reduce #(pmf/set %1 %2 (/ 1 (count events))) (pmf/make) events)]
    (t/are [e] (= (to-test e) (/ 1 3))
      1
      2
      3)))
