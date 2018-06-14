(ns think-bayes.pmf)

(defn set-probability [pmf value probability]
  (assoc pmf value probability))
