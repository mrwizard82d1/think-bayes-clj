(ns think-bayes.diachronic)

(defn posterior [prior likelihood normalizing-constant]
  (/ (* prior likelihood)
     normalizing-constant))
  
