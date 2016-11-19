(ns think-bayes.cookies)

(defn bayes-law [prior likelihood normalizing-constant]
  (/ (* prior likelihood)
     normalizing-constant))

(defn probability-bowl-1-given-vanilla []
  (let [probability-bowl-1 (/ 1 2)
        probability-vanilla-given-bowl-1 (/ 3 4)
        total-probability (/ 5 8)]
    (bayes-law probability-bowl-1 probability-vanilla-given-bowl-1 total-probability)))


