(ns think-bayes.pmf-test
  (:require [clojure.test :as t]
            [think-bayes.pmf :as pmf]))


(def many-pmf {:glabra 5 :recidiva 3 :senis 2 :retis 1})


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
        single-pmf {:a 7}]
    (t/are [e p m] (= e (pmf/mass p m))
      0 empty-pmf :a
      7 single-pmf :a
      0 single-pmf :b
      5 many-pmf :glabra
      0 many-pmf "glabra")))

(t/deftest transform-test
  (t/are [e v f] (= e (pmf/mass (pmf/transform many-pmf v f) v))
    1 :glabra #(/ % 5)
    (Math/sqrt 2) :senis #(Math/sqrt %))
  (t/is (= 1 (pmf/mass (pmf/transform {} :glabra inc) :glabra))))
