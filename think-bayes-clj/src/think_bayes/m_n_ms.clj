(ns think-bayes.m-n-ms)

(defn posterior
  "Calculate the posterior 'distribution' (probability mass function) using Bayes law.

   To construct this 'distribution', we need a sequence of `hypotheses`, the observed datum (called **data** based on colloquial
   usage) and two functions. The first function, `priors`, calculates the prior probability of each hypotheses. The second
   function, `likelihoods`, calculates the probability of some datum given a hypothesis is true. The argument to `likelihoods` is a 
   single item, a `vector` of the form [datum hypothesis]."
  [hypotheses observed-data priors likelihoods]
  (let [bayes-factor (fn [observed-data hypothesis]
                       (* (priors hypothesis) (likelihoods [observed-data hypothesis])))
        bayes-factors (map #(bayes-factor observed-data %1) hypotheses)
        normalizing-factor (reduce + bayes-factors)
        posteriors (map #(/ %1 normalizing-factor) bayes-factors)]
    (zipmap hypotheses posteriors)))

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
        probability-table (posterior hypotheses :vanilla p-hypotheses p-data-given-hypothesis)]
    (probability-table :bowl1)))
