(ns think-bayes.pmf)

(defn set-probability [pmf value probability]
  "Set the `probability` of `value` in `pmf`." 
  (assoc pmf value probability))

(defn set-probabilities [pmf value-probability-map]
  "Set the probability of multiple values (the keys of `value-probability-map`) to the values of `value-probability-map`."
  (merge pmf value-probability-map))
