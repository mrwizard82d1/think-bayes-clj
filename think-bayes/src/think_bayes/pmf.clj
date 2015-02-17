(ns think-bayes.pmf)

(defn set-mass [pmf value mass]
  (assoc pmf value mass))