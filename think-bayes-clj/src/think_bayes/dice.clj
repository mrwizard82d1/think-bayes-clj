(ns think-bayes.dice
  (:require [think-bayes.suite :as suite]))

(defn priors
  "Return the prior probabilities of all the `hypotheses`."
  [hypotheses]
  (suite/priors hypotheses))

(defn likelihood
  "Calculate the probability of rolling `spots` from a die with `sides`."
  [spots sides]
  (if (< sides spots)
    0
    (/ 1 sides)))

(defn posteriors
  "Calculate the posterior probabilities having rolled `spots`."
  [priors spots]
  (suite/posteriors priors spots likelihood))

(defn posteriors-seq
  "Calculate the sequence of posteriors staring with `priors` based on the observed `data` sequence."
  [priors data]
  (suite/posteriors-seq priors data likelihood))

