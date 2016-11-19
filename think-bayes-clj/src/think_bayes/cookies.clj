(ns think-bayes.cookies
  (require [think-bayes.core :as core]))

(defn probability-bowl-1-given-vanilla []
  (let [probability-bowl-1 (/ 1 2)
        probability-bowl-2 (/ 1 2)
        probability-vanilla-given-bowl-1 (/ 3 4)
        probability-vanilla-given-bowl-2 (/ 1 2)
        total-probability (+ (* probability-bowl-1 probability-vanilla-given-bowl-1)
                             (* probability-bowl-2 probability-vanilla-given-bowl-2))]
    (core/bayes-law probability-bowl-1 probability-vanilla-given-bowl-1 total-probability)))


