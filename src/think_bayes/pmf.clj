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
  "Normalize this pmf into a probablity density function."
  (reduce (fn [m [k v]] (assoc m k (/ v (count (keys pmf))))) {} pmf))

(defn probability [pmf value]
  "Return the probability of value."
  (pmf value))
