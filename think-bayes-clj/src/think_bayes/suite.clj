(ns think-bayes.suite
  (:require [think-bayes.pmf :as pmf]))

(defn priors
  "Create the (uniform) prior probabilities for `hypotheses`."
  [hypotheses]
  (let [normalizing-factor (count hypotheses)]
    (reduce #(pmf/set-probability %1 %2 (/ 1 normalizing-factor)) {} hypotheses)))


(defn posteriors
  "Update `priors` when `data` observed using `likelihood` function.
  
  The function, `likelihood`, is a function of two variables, `data` and `hypothesis`."
  [priors data likelihood]
  (let [build-up (fn [so-far [hypothesis prior-p]]
                   (assoc so-far hypothesis (* prior-p (likelihood data hypothesis))))]
    (pmf/normalize (reduce build-up {} priors))))

