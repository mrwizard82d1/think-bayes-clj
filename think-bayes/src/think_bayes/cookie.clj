(ns think-bayes.cookie
  (:require [think-bayes.pmf :as pmf]))


(defn init
  "Initialize a 'cookie' problem."
  ([] {})
  ([hypotheses]
   (reduce #(pmf/set-mass %1 %2 1) {} hypotheses)))