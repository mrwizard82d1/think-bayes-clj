(ns think-bayes.monty-python
  (:require [think-bayes.pmf :as pmf]))

(defn make
  "Make a 'Monty Python' model from `hypotheses`."
  [hypotheses]
  (-> (pmf/make hypotheses)
      pmf/normalize))

(defn likelihood [d h]
  (cond
    (= d h) 0
    (= h \A) (/ 1 2)
    :else 1))

(defn posterior 
 "Calculate the posterior of the (prior) PMF given data using likelihood." 
  [pmf data likelihood]
  (pmf/normalize (reduce #(->> (likelihood data %2)
                               (pmf/scale-mass %1 %2))
                         pmf
                         (keys pmf))))
