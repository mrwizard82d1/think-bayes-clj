(ns think-bayes.pmf)


(defn mass
  "Returns the mass of the value, v, in a PMF. If no such value exists, returns 0."
  [pmf v]
  {:pre [(not (nil? pmf))]}
  (pmf v))


(def change-mass "Returns a new PMF by replacing the mass of value, v, with to." assoc)


(defn change-mass-by
  "Returns a new PMF with the mass of the value, v, transformed by the function by.

  In general, invoking this function denormalizes the masses (probabilities)." 
  [pmf v by]
  {:pre [(not (nil? pmf))]}
  (assoc pmf v (by (mass pmf v))))


(defn scale-mass
  "Returns a new PMF with the mass of the value, v, multiplied by the factor, by."
  [pmf v by]
  {:pre [(not (nil? pmf))]}
  (assoc pmf v (* (mass pmf v) by)))


(defn make
  "Makes a probability mass function (PMF) from a sequence of values, vs.

  A probability mass function is a mapping between values and probabilities, frequencies, or 'weights.' (See 
  https://en.wikipedia.org/wiki/Probability_mass_function for more details.)

  In this implementation, a PMF only maps to probabilities after being normalized. Prior to normalization (or after 
  change that denormalize the probabilities), the masses corresponding to the values are more like frequencies or 
  weights."
  [vs]
  (reduce #(assoc %1 %2 (inc (%1 %2 0))) {} vs))


(defn normalize
  "Normalize the PMF."
  [pmf]
  {:pre [(not (nil? pmf))
         (not (empty? pmf))]}
  (let [total (reduce + (vals pmf))]
    (reduce (fn [pmf [v p]]
              (assoc pmf v (/ p total)))
            {}
            pmf)))
