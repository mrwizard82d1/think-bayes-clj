(ns think-bayes.pmf)

(defn set-probability [pmf value probability]
  "Set the `probability` of `value` in `pmf`." 
  (assoc pmf value probability))

(defn set-probabilities [pmf value-probability-map]
  "Set the probability of multiple values (the keys of `value-probability-map`) to the values of `value-probability-map`."
  (merge pmf value-probability-map))

(defn increase
  ([pmf value]
   "Increase the probability mass of `value` by one."
   (increase pmf value 1))
  ([pmf value by]
   "Increase the probability mass of `value` by `by`."
   (assoc pmf value (+ (get pmf value 0) by))))

(defn normalize [pmf]
  "Normalize this pmf into a probability density function."
  (reduce (fn [m [k v]] (assoc m k (/ v (reduce + (vals pmf))))) {} pmf))

(defn probability [pmf value]
  "Return the probability of value."
  (pmf value))

(defn multiply [pmf value factor]
  "Multiply the probability mass of `value` by `factor`."
  (set-probability pmf value (* (probability pmf value) factor)))

(defn suite [hypotheses]
  "Construct a suite: a PMF with equal probabilities for each hypothesis in `hypotheses`."
  (normalize (reduce #(increase %1 %2) {} hypotheses)))

(defn posteriors [priors likelihood datum]
  "Calculate the posteriors after seeing `datum`."
  (let [hypotheses (keys priors)
        products (reduce #(set-probability %1 %2 (* (priors %2)
                                                    (likelihood datum %2)))
                         {}
                         hypotheses)
        posteriors (normalize products)]
    posteriors))
