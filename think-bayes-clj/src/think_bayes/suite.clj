(ns think-bayes.suite
  (:require [think-bayes.pmf :as pmf]))

(defn make
  "Construct a suite for a set of hypotheses. 

  A suite is a probability mass function (PMF) in which all hypothesis have an equal prior
  probability."
  [hypotheses]
  (pmf/normalize (pmf/make hypotheses)))


(defn print-suite
  "Print the probabilities for the suite."
  [pmf]
  (doseq [[h p] pmf]
    (println h p)))

(defn posterior
  "Calculate the posterior PMF of hypothesis given data."
  [suite data likelihood]
  (pmf/normalize 
   (reduce #(assoc %1 
                   (first %2)
                   (* (pmf/probability suite (first %2))
                      (likelihood data (first %2))))
           {}
           suite)))

