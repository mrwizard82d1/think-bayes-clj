(ns think-bayes.pmf)

(defn set-mass [pmf value mass]
  (assoc pmf value mass))

(defn get-mass [pmf value]
  (get pmf value))

(defn increment-mass [pmf value]
  (assoc pmf value (inc (get pmf value 0))))

(defn probability [pmf value]
  (/ (get-mass pmf value)
     (apply + (vals pmf))))

(defn multiply-mass [pmf value factor]
  (assoc pmf value (* factor (get-mass pmf value))))