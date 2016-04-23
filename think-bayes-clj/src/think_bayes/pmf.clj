(ns think-bayes.pmf)


(defn mass
  "Returns the mass of the value, v, in a PMF. If no such value exists, returns 0."
  [pmf v]
  {:pre [(not (nil? pmf))]}
  (pmf v 0))


(defn transform
  "Returns a new PMF by replacing the mass of value, v, with the result of applying a transformation function, f, to that mass.

  Remember that after applying f, the PMF will **not** be normalized."
  [pmf v f]
  {:pre [(not (nil? pmf))]}
  (assoc pmf v (f (mass pmf v))))


(defn make
  "Makes a probability mass function (PMF) from a sequence of values, vs.

  A probability mass function is a mapping between values and probabilities, frequencies, or 'weights.' (See 
  https://en.wikipedia.org/wiki/Probability_mass_function for more details.)

  In this implementation, a PMF only maps to probabilities after being normalized. Prior to normalization (or after 
  change that denormalize the probabilities), the masses corresponding to the values are more like frequencies or 
  weights."
  [vs]
  (reduce #(transform %1 %2 inc) {} vs))
