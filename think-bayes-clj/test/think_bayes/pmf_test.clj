(ns think-bayes.pmf-test
  (:require [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest pmf-make
  (t/are [e s] (= e (pmf/make s))
    {} []
    {:a 1} #{:a}
    {"glabra" 5
     "recidiva" 3
     "senis" 2
     "retis" 1} (clojure.string/split "glabra glabra senis recidiva glabra recidiva senis glabra recidiva glabra retis" #"\s+")))
 
