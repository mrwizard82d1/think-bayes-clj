(ns think-bayes.pmf)

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
