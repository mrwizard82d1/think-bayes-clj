(ns think-bayes.train
  (:require [think-bayes.suite :as suite]
            [think-bayes.dice :as dice]))

(defn uniform-priors
  "Create a uniform prior probability distribution with hypotheses from `lower` up to and including `upper`."
  [lower upper]
  (suite/priors (range lower (inc upper))))
  
;; This function is identical to `suite/likelihood` except we force our calculations to be `double` values.
;; 
;; I made this choice because the large fractions that resulted from a uniform probability from 1 to 1000, 
;; inclusive, resulted in **very** large numerators and denominators in the probabilities. Prior to this
;; change, I encountered errors in testing, that seemed to indicate incorrect calculations. I consider
;; this hypothesis to be very unlikely; however, I made the change to `double` precision numbers to make 
;;progress.
(defn likelihood
  "Calculate the probability of seeing train # `train-number` from a company with `trains-count` trains."
  [train-number trains-count]
  (if (< trains-count train-number)
    0
    (/ 1.0 trains-count)))

;; This function is identical to `dice.posteriors`; however, because I want to use `train.likelihood`; I
;; copied the definition to this module.
(defn posteriors
  "Calculate the posterior probabilities having seen `train-number`."
  [priors train-number]
  (suite/posteriors priors train-number likelihood))

