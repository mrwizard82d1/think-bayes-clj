(ns think-bayes.monty-hall-suite
  (:require [think-bayes.suite :as suite]))

(defn priors 
  "The prior probabilities for the Monty Hall problem."
  []
  (suite/priors [:a :b :c]))

(defn likelihood
  "Calculate the probability of `data` given `hypothesis`."
  [data hypothesis]
  (cond
    (= data hypothesis) 0
    (= hypothesis :a) (/ 1 2)
    :else 1))

(defn posteriors
  "Calculate the posterior probabilities for the Monty Hall problem when `data` observed."
  [data]
  (suite/posteriors (priors) data likelihood))

