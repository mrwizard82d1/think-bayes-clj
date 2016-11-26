(ns think-bayes.pmf
  (:refer-clojure :rename {inc core-incs}))

(defn probability [pmf k]
  (pmf k))

(defn set-probability [pmf k v]
  (assoc pmf k v))

(defn inc 
  "Increment the 'probabilty' (mass) for `hypothesis` `by-amount` (optional - default value 1)."
  ([pmf hypothesis]
   (inc pmf hypothesis 1))
  ([pmf hypothesis by-amount]
   (set-probability pmf hypothesis (+ (get pmf hypothesis 0) by-amount))))

(defn normalize
  "Normalize the 'probability' masses of `pmf` so they are probabilities (sum to 1)."
  [pmf]
  (let [normalizing-factor (reduce + (vals pmf))
        reduce-f (fn [m [k v]]
                   (assoc m k (/ v normalizing-factor)))]
    (reduce reduce-f {} pmf)))

(defn scale
  "Multiple the 'probability' (mass) for `hypothesis` by `factor`."
  [pmf hypothesis factor]
  (set-probability pmf hypothesis (* (probability pmf hypothesis) factor)))

(defn mean
  "Calculate the weighted arithmetic mean of the hypotheses of a PMF.

  Remember that this function **assumes** all hypotheses are numbers."
  [pmf]
  (reduce #(+ %1 (* (first %2) (second %2))) 0 pmf))
