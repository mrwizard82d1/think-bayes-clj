(ns think-bayes.posterior)

(defn normalizing-constant [data prior likelihood]
  "Calculate the normalizing constant (the divisor use to normalize the Bayesian posterior).
   
   This calculation uses the law of total probability. It assumes that all the hypotheses are exhaustive and mutually 
   exclusive."
  (reduce + (for [hypothesis (keys prior)]
              (* (prior hypothesis) (get-in likelihood [data hypothesis])))))

(defn posterior [data hypothesis prior likelihood]
  "Calculate the normalized posterior probability of data given hypothesis.

   The prior probability is calculated by applying the prior (function) to the hypothesis and by applying the likelihood
   function to the data given the hypothesis."
  (/ (* (prior hypothesis) (get-in likelihood [data hypothesis]))
     (normalizing-constant data prior likelihood)))

(defn datum-hypothesis-pairs [likelihood]
  "Calculate all the datum-hypothesis pairs in likelihood."
  (for [datum (keys likelihood)
        hypothesis (keys (likelihood datum))]
    [datum hypothesis]))

(defn posterior-distribution [prior likelihood]
  (reduce #(assoc-in %1 %2 (posterior (first %2) (second %2) prior likelihood)) {} (datum-hypothesis-pairs likelihood)))



