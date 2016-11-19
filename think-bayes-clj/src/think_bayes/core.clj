(ns think-bayes.core)

(defn bayes-law [prior likelihood normalizing-constant]
  (/ (* prior likelihood)
     normalizing-constant))

(defn posterior
  "Calculate the posterior distribution (probability mass function) using Bayes law.

   To construct this distribution, we need a sequence of `hypotheses`, the observed datum (called **data** based on colloquial
   usage) and two functions. The first function, `priors`, calculates the prior probability of a hypothesis. The second function, 
   `likelihoods`, calculates the probability of a datum given that a hypothesis is true. The argument to `likelihoods` is a 
   single item, a `vector` of the form [datum hypothesis]."
  [hypotheses observed-data priors likelihoods]
  (let [prior-likelihood-product (fn [observed-data hypothesis]
                                   (* (priors hypothesis) (likelihoods [observed-data hypothesis])))
        prior-likelihood-products (map #(prior-likelihood-product observed-data %1) hypotheses)
        normalizing-constant (reduce + prior-likelihood-products)
        posteriors (map #(/ %1 normalizing-constant) prior-likelihood-products)]
    (zipmap hypotheses posteriors)))
