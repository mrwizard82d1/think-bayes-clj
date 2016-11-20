(ns think-bayes.pmf)

(defn probability [pmf k]
  (pmf k))

(defn set-probability [pmf k v]
  (assoc pmf k v))

