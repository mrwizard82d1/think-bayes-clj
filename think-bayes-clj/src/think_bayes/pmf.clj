(ns think-bayes.pmf
  (:refer-clojure :rename {inc core-incs}))

(defn probability [pmf probability]
  (pmf probability))

(defn set-probability 
  "Sets the probability of `hypothesis` in `pmf` to be `probability`."
  [pmf hypothesis probability]
  (assoc pmf hypothesis probability))

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

(defn sorted-probabilities [pmf]
  (map #(vector %1 (probability pmf %1)) (sort (keys pmf))))

(defn probabilities [hypothesis-probability-pairs]
  (map second hypothesis-probability-pairs))

(defn total-probability [hypothesis-probability-pairs]
  (reduce + (probabilities hypothesis-probability-pairs)))

(defn cdf-pairs [ordered-probabilities]
  (reduce #(conj %1 [(first %2) (+ (second (last %1)) (second %2))]) 
                          [(first ordered-probabilities)] 
                          (rest ordered-probabilities)))

(defn percentile
  "Returns the hypothesis of `pmf` the sum of whose probabilities is greater than `percentage`."
  [pmf percentage]
  (let [percentile (/ percentage 100.)
        ordered-probabilities (sorted-probabilities pmf)
        cdf-pairs (cdf-pairs ordered-probabilities)]
    (first (first (drop-while #(< (second %) percentile) cdf-pairs)))))

