(ns think-bayes.eating
  (:require [think-bayes.suite :as suite]))

(def eating-hypotheses [:bowl1 :bowl2])

(def eating-pmf (suite/make eating-hypotheses))

(def mixture 
  (atom {:bowl1 {:vanilla 30 :chocolate 10}
         :bowl2 {:vanilla 20 :chocolate 20}}))

(defn yum-yum [m d]
  (reduce #(assoc %1 
                  (first %2)
                  (if (= (first %2) d)
                    (dec (second %2))
                    (second %2)))
          {}
          m))

(defn yum [m d]
  (reduce #(assoc %1 (first %2) (yum-yum (second %2) d)) {} m))

(defn likelihood [d h]
  (get-in @mixture [h d] 0))

(defn posterior [suite datum likelihood]
  (let [result (suite/posterior suite datum likelihood)]
    (swap! mixture yum datum)
    result))
