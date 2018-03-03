(ns think-bayes.ex03
 (:require [think-bayes.train :as train]
           [think-bayes.suite :as suite]))

(defn zipf-train-companies
  "Generate a train company distribution according to Zipf's law (https://en.wikipedia.org/wiki/Zipf%27s_law). 
  The largest train company will have `max-locomotive-count` locomotives."
  [max-locomotive-count]
  (map #(vector (int (* max-locomotive-count (Math/pow % -1))) %) 
       (range 1 (inc max-locomotive-count))))

(defn- company-locomotive-numbers
  "Convert a locomotive distribution, `train-companies`, into a collection of locomotive numbers."
  [train-companies]
  (flatten (map #(repeat (first %) (second %)) train-companies)))

(defn zipf-hypotheses 
  "Generate a collection of hypotheses based on Zipf's law. with a maximum of `max-locomotive-count`."
  [max-locomotive-count]
  (company-locomotive-numbers (zipf-train-companies max-locomotive-count)))

(def priors suite/priors)

(defn generate-likelihood-function
  "Generates a likelihood function that encloses all the `hypotheses`. 

  The returned value calculates the probability of `data` given `hypothesis` is true.

  Specifically, this function assumes that `hypothesis` is the number of locomotives of the largest company.
  It uses the analysis from 
  `http://stats.stackexchange.com/questions/70096/locomotive-problem-with-various-size-companies` to 
  calculate the likelihood of seeing locomotive number `data`. Further, this function assumes that the 
  distribution of company sizes follows a power law."
  [hypotheses]
  (let [train-count (reduce + hypotheses)]
    (fn [data hypothesis]
      (if (> data hypothesis
        0
        (let [companies-with-train-number (count (filter #(<= data %) hypotheses))]
          (/ companies-with-train-number train-count))))))
  
(def posteriors suite/posteriors)

