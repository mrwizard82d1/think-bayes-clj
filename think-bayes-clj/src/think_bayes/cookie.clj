(ns think-bayes.cookie
  (:require [think-bayes.pmf :as pmf]))


(defn make 
  "Make a 'cookie experiment' with a set of hypotheses." 
  [hypotheses]
  (-> (pmf/make hypotheses)
      pmf/normalize))


(def mix {"Bowl 1" {:vanilla (/ 3 4)
                    :chocolate (/ 1 2)}
          "Bowl 2" {:vanilla (/ 1 2)
                    :chocolate (/ 1 2)}})


(defn likelihood [d h]
  (get-in mix [h d]))


(defn posterior [pmf data]
  (pmf/normalize  (reduce #(->> (likelihood data %2)
                                (pmf/scale-mass %1 %2))
                          pmf
                          (keys pmf))))
