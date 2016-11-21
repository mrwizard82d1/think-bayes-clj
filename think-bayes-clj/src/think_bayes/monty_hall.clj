(ns think-bayes.monty-hall
  (require [think-bayes.core :as core]))

(defn should-switch-if-monty-picks-random
  "Solve the 'Monty Hall' problem.

   In this problem, Monty Hall offers 3 doors: one of which has a very valuable prize and two of which not so much. You pick one. He
   then shows you one of the doors you **did not** pick. (He selects this door randomly.) Should you switch?"
  []
  (let [hypotheses [:car-behind-a :car-behind-b :car-behind-c]
        p-hypothesis (reduce #(assoc %1 %2 (/ 1 3)) {} hypotheses)
        p-data-given-hypothesis (fn [[datum hypothesis]]
                                  (cond
                                    (and (= hypothesis :car-behind-a)
                                         (= (first datum) :open-b)) (/ 1 2)
                                    (and (= hypothesis :car-behind-b)
                                         (= (first datum) :open-b)) 0
                                    (and (= hypothesis :car-behind-c)
                                         (= (first datum) :open-b)) 1))
        posteriors (core/posteriors hypotheses [:open-b :no-car] p-hypothesis p-data-given-hypothesis)]
    (posteriors :car-behind-c)))

(defn should-switch-if-monty-always-picks-b
  "Solve the 'Monty Hall' problem.

   In this problem, Monty Hall offers 3 doors: one of which has a very valuable prize and two of which not so much. You pick one. He
   then shows you one of the doors you **did not** pick. (He selects this door randomly.) Should you switch?"
  []
  (let [hypotheses [:car-behind-a :car-behind-b :car-behind-c]
        p-hypothesis (reduce #(assoc %1 %2 (/ 1 3)) {} hypotheses)
        p-data-given-hypothesis (fn [[datum hypothesis]]
                                  (cond
                                    (and (= hypothesis :car-behind-a)
                                         (= (first datum) :open-b)) 1
                                    (and (= hypothesis :car-behind-b)
                                         (= (first datum) :open-b)) 0
                                    (and (= hypothesis :car-behind-c)
                                         (= (first datum) :open-b)) 1))
        posteriors (core/posteriors hypotheses [:open-b :no-car] p-hypothesis p-data-given-hypothesis)]
    (posteriors :car-behind-c)))
