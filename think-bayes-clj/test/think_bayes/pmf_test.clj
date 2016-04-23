(ns think-bayes.pmf-test
  (:require [clojure.test :as t]
            [think-bayes.pmf :as pmf]))

(t/deftest make-test
  (t/are [e s] (= e (pmf/make s))
    {} []
    {:a 1} #{:a}
    {"glabra" 5
     "recidiva" 3
     "senis" 2
     "retis" 1} (clojure.string/split "glabra glabra senis recidiva glabra recidiva senis glabra recidiva glabra retis" #"\s+")))
 
(t/deftest mass-test
  (let [empty-pmf {}
        single-pmf {:a 7}
        many-pmf {:glabra 5 :recidiva 3 :senis 2 :retis 1}]
    (t/are [e p m] (= e (pmf/mass p m))
      0 empty-pmf :a
      7 single-pmf :a
      0 single-pmf :b
      5 many-pmf :glabra
      0 many-pmf "glabra")))
