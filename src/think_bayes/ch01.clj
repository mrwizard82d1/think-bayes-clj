(ns think-bayes.ch01)


(defn cookie []
  (let [probability-bowl-1 (/ 1 2)
        probability-vanilla (/ 50 80)
        probability-vanilla-from-bowl-1 (/ 30 40)]
    (/ (* probability-bowl-1 probability-vanilla-from-bowl-1)
       probability-vanilla)))
