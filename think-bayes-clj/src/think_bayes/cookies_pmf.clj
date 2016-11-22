(ns think-bayes.cookies-pmf
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

(def likelihood
  (let [mixes {:bowl-1 {:vanilla (/ 3 4)
                        :chocolate (/ 1 4)}
               :bowl-2 {:vanilla (/ 1 2)
                        :chocolate (/ 1 2)}}]
    (fn [data hypothesis]
      (get-in mixes [hypothesis data]))))
