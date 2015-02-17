(ns think-bayes.pmf)

(defn set-mass [pmf value mass]
  (assoc pmf value mass))

(defn increment-mass [pmf value]
  (assoc pmf value (inc (get pmf value 0))))