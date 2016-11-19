(ns think-bayes.m-n-ms
  (require [think-bayes.core :as core]))

(defn probability-bowl-1-given-vanilla 
  "Calculate the probability that a vanilla cookie came from bowl 1 using a table of Bayesian probabilities."
  []
  (let [hypotheses [:bowl1 :bowl2]
        p-hypotheses {:bowl1 (/ 1 2)
                      :bowl2 (/ 1 2)}
        ;; `p-hypotheses` is actually a **function** for calculating the (prior) probability of each hypothesis
        p-data-given-hypothesis {[:vanilla :bowl1] (/ 3 4)
                                 [:vanilla :bowl2] (/ 1 2)}
        ;; Similarly, a function for calculating the probability of the data given a hypothesis. Note that this function is incomplete
        ;; because it has no probabilities for chocolate cookies.
        probability-table (core/posterior hypotheses :vanilla p-hypotheses p-data-given-hypothesis)]
    (probability-table :bowl1)))
