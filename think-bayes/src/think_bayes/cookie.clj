(ns think-bayes.cookie
  (:require [think-bayes.pmf :as pmf]))


(defn init
  "Initialize a 'cookie' problem."
  ([] {})
  ([hypotheses]
   (reduce #(pmf/set-mass %1 %2 1) {} hypotheses)))

#_(defn update-hypothesis-mass
  [to-update-pmf hypothesis data]
  (pmf/multiply-mass to-update-pmf hypothesis
                     (likelihood data hypothesis)))

#_(defn update
  "Update the prior probabilities based on data and likelihood."
  [prior data likelihood]
    (reduce #(%1 %2 data) prior (keys prior)))

(defn update-hypothesis-mass
  "Update the probability mass of a single hypothesis."
  [pmf likelihood data hypothesis]
    (pmf/multiply-mass pmf hypothesis (likelihood data hypothesis)))

(defn update
  "Update a prior PMF based on the likelihood of seeing data."
  [prior data likelihood]
    (reduce #(update-hypothesis-mass %1 likelihood data %2)
            prior (keys prior)))