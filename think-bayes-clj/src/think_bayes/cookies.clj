(ns think-bayes.cookies)

(defn bayes-law [prior likelihood normalizing-constant]
  (/ (* prior likelihood)
     normalizing-constant))

(defn probability-bowl-1-given-vanilla []
  (let [probability-bowl-1 (/ 1 2)
        probability-bowl-2 (/ 1 2)
        probability-vanilla-given-bowl-1 (/ 3 4)
        probability-vanilla-given-bowl-2 (/ 1 2)
        total-probability (+ (* probability-bowl-1 probability-vanilla-given-bowl-1)
                             (* probability-bowl-2 probability-vanilla-given-bowl-2))]
    (bayes-law probability-bowl-1 probability-vanilla-given-bowl-1 total-probability)))


