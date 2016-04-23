(ns think-bayes.pmf-tests
  (:require [cljs.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest make-test
  (t/are [e s] (= e (pmf/make s))
    {} []))

