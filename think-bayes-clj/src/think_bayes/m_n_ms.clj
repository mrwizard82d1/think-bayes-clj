(ns think-bayes.m-n-ms
  (require [think-bayes.core :as core]))

(defn probability-bowl-1-given-vanilla 
  "Calculate the probability that a vanilla cookie came from bowl 1 using a table of Bayesian probabilities."
  []
  (let [hypotheses [:bowl1 :bowl2]
        p-hypotheses {:bowl1 (/ 1 2)
                      :bowl2 (/ 1 2)}
        ;; `p-hypotheses` is actually a **function** for calculating the (prior) probability of each hypothesis
        p-data-given-hypothesis {[:vanilla :bowl1] (/ 3 4)
                                 [:vanilla :bowl2] (/ 1 2)}
        ;; Similarly, a function for calculating the probability of the data given a hypothesis. Note that this function is incomplete
        ;; because it has no probabilities for chocolate cookies.
        probability-table (core/posteriors hypotheses :vanilla p-hypotheses p-data-given-hypothesis)]
    (probability-table :bowl1)))

(defn probability-yellow-1994
  "Calculate the probability that the yellow M&M was from bag 1 and the green M&M was from bag 2."
  []
  (let [hypotheses [[:1994 :1996] [:1996 :1994]]
        p-hypotheses (reduce #(assoc %1 %2 (/ 1 2)) {} hypotheses)
        mnm-colors-by-years {:1994 {:brown 30 
                                    :yellow 20
                                    :red 20
                                    :green 10
                                    :orange 10
                                    :tan 10}
                             :1996 {:blue 24
                                    :green 20
                                    :orange 16
                                    :yellow 14
                                    :red 13
                                    :brown 13}}
        p-data-given-hypothesis (fn [[datum hypothesis]]
                                  (let [p-color-in-bag-1 (get-in mnm-colors-by-years (vec (first (map vector hypothesis datum))))
                                        p-color-in-bag-2 (get-in mnm-colors-by-years (vec (last (map vector hypothesis datum))))]
                                    (* p-color-in-bag-1 p-color-in-bag-2)))
        posteriors (core/posteriors hypotheses [:yellow :green] p-hypotheses p-data-given-hypothesis)]
    (posteriors [:1994 :1996])))
