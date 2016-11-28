(ns think-bayes.cdf
  [:require [think-bayes.pmf :as pmf]])

(defn probability [[_ probability]]
  probability)

(defn hypothesis [[hypothesis _]]
  hypothesis)

(defn sorted-probabilities [pmf]
  (map #(vector %1 (pmf/probability pmf %1)) (sort (keys pmf))))

(defn cdf-pairs [ordered-probabilities]
  (reduce #(conj %1 [(hypothesis %2) (+ (probability (last %1)) (probability %2))]) 
                          [(first ordered-probabilities)] 
                          (rest ordered-probabilities)))

(defn make-cdf
  "Creates a cumulative distribution function (CDF) from `pmf`.

  As an implementation note, the hypothesis-probability pairs returned by this function are sorted on **both**
  values. The hypotheses are sorted in (natural) ascending order because of the call to `sort` in 
  `sorted-probabilities`. The probabilities are sorted in descending order because these values are the 
  **cumulative** probability (which is a monotonically increasing function between 0 and 1)."
  [pmf]
  (cdf-pairs (sorted-probabilities pmf)))

(defn percentile
  "Returns the hypothesis whose CDF is greater than `percentage`."
  [cdf percentage]
  (let [percentile (/ percentage 100.)]
    (->> cdf
        (drop-while #(< (probability %) percentile))
        first
        hypothesis)))

(defn credible-interval
  "Calculates the credible interval, by default a 90% intervale."
  ([cdf percentage]
   (let [lower-percentile (/ (- 100 percentage) 2.)
         upper-percentile (- 100 lower-percentile)]
     [(percentile cdf lower-percentile) (percentile cdf upper-percentile)]))
  ([cdf]
   (credible-interval cdf 90)))
  
