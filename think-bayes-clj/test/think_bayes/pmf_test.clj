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
    (t/are [p m] (nil? (pmf/mass p m))
        empty-pmf :a
        single-pmf :b
        many-pmf "glabra")
    (t/are [e p m] (= e (pmf/mass p m))
      7 single-pmf :a
      5 many-pmf :glabra)))


(t/deftest change-mass-test
  (t/is (= {:a (/ 1 17)} (pmf/change-mass {:a (/ 1 16)} :a (/ 1 17))))
  (t/is (= (assoc many-pmf :retis (/ 1 2)) (pmf/change-mass many-pmf :retis (/ 1 2)))))


(t/deftest multiply-test
  (t/are [e pmf v by] (= e (pmf/scale-mass pmf v by))
    {:a 21} {:a 7} :a 3
    (assoc many-pmf :senis 4) many-pmf :senis 2))


(t/deftest change-mass-by-test
  (t/are [e v f] (= e (pmf/mass (pmf/change-mass-by many-pmf v f) v))
    1 :glabra #(/ % 5)
    (Math/sqrt 2) :senis #(Math/sqrt %))
  (t/is (= 6 (pmf/mass (pmf/change-mass-by many-pmf :glabra inc) :glabra))))


(t/deftest normalize-test
  (t/is (= 1 (pmf/mass (pmf/normalize {:a 5}) :a)))
  (t/is (= {:glabra (/ 5 11)
            :recidiva (/ 3 11)
            :senis (/ 2 11)
            :retis (/ 1 11)}
           (pmf/normalize many-pmf))))

(t/deftest primitive-cookie-test
  (let [cookie-pmf (-> {"Bowl 1" (/ 1 2)
                        "Bowl 2" (/ 1 2)}
                       (pmf/scale-mass "Bowl 1" (/ 3 4))
                       (pmf/scale-mass "Bowl 2" (/ 1 2))
                       pmf/normalize)]
    (t/is (= (/ 3 5) (pmf/probability cookie-pmf"Bowl 1")))))
