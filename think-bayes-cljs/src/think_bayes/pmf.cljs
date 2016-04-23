(ns think-bayes.pmf)

(defn mass
  "Returns the mass of the value, v, in the probability mass function, pmf."
  [pmf v]
  (pmf v 0))

(defn transform
  "Transforms the probability of the value, v, using a transformation function, f.

  f is a function of a single argument, the untransformed probability."
  [pmf v f]
  (assoc pmf v (f (mass pmf v))))

(defn make
  "Construct a probability mass function from a sequence, s.

  A probability mass function (or PMF) maps discrete values to masses. (See 
  https://en.wikipedia.org/wiki/Probability_mass_function for additional details.)

  In this particular implementation, the mass of a value is only a probability if the masses have been normalized."
  [values]
  (reduce #(transform %1 %2 inc) {} values))

