(ns think-bayes.core)

(defn first-element [sequence default]
  (if (empty? sequence)
    default
    (first sequence)))
