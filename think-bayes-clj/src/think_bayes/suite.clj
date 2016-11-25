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

(defn make-data-posterior-pair [priors data likelihood]
  [data (posteriors priors data likelihood)])

(defn posteriors-seq-helper [priors-seq data likelihood]
  (if (nil? (seq data))
    priors-seq
    (let [next-pair (make-data-posterior-pair (last (last priors-seq)) (first data) likelihood)]
      (posteriors-seq-helper (conj priors-seq next-pair) (rest data) likelihood))))

(defn posteriors-seq
  "Calculate the sequence of posterior probability distributions starting with resulting from observing data, 
  `ds`, starting with `priors` and applying the `likelihood` of data given a hypothesis."
  [priors data likelihood]
  (let [priors-seq [[nil priors]]]
    (posteriors-seq-helper priors-seq data likelihood)))
