(ns think-bayes.pmf)

(defn make 
  "Makes a PMF (probability mass function.

   A PMF is useful for modeling discrete probability distributions that **may not** be normalized."
  []
  {})

(defn set
  "Sets the probability mass of an event to value in pmf."
  [pmf event mass]
  (assoc pmf event mass))
