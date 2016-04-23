(ns think-bayes.pmf-test
  (:require [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest pmf-make
  (t/are [e s] (= e (pmf/make s))
    {} []
    {:a 1} #{:a}
    {"the" 2
     "quick" 1
     "little" 1
     "brown" 1
     "fox" 1
     "jumped" 1
     "over" 1
     "lazy" 1
     "grey" 1
     "lambs" 1} (clojure.string/split "the quick little brown fox jumped over the lazy grey lambs" #"\s+")))
 
