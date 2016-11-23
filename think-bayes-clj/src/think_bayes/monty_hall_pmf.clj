(ns think-bayes.monty-hall-pmf
  (:require [think-bayes.pmf :as pmf]))

(defn priors
  "Create the (uniform) prior probabilities for `hypotheses`."
  [hypotheses]
  (let [normalizing-factor (count hypotheses)]
    (reduce #(pmf/set-probability %1 %2 (/ 1 normalizing-factor)) {} hypotheses)))


(defn posteriors
  "Update `priors` when `data` observed."
  [priors data likelihood]
  (let [build-up (fn [so-far [hypothesis prior-p]]
                   (assoc so-far hypothesis (* prior-p (likelihood data hypothesis))))]
    (pmf/normalize (reduce build-up {} priors))))

(defn likelihood
  "Calculated the likelihood of `data` given that `hypthosis` is true.

  Note that this implementation assumes that Monty Hall picks randomly between unselected doors that **do not** have a car."
  [data hypothesis]
  (cond
    (= data hypothesis) 0
    (= hypothesis :a) (/ 1 2)
    :else 1))
