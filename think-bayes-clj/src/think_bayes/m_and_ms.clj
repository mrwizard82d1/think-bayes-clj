(ns think-bayes.m-and-ms
  (:require [think-bayes.pmf :as pmf]
            [clojure.set :as cs]))

(defn make
  "Make an 'M & Ms' model."
  [hypotheses]
  (-> (pmf/make hypotheses)
      pmf/normalize))

(defn likelihood [d h]
  (let [mix {:bag1 {:brown 30
                    :yellow 20
                    :red 20
                    :green 10
                    :orange 10}
             :bag2 {:blue 24
                    :green 20
                    :orange 16
                    :yellow 14
                    :red 13
                    :brown 13}}]
    (get-in mix [h d])))

(defn other
  "Calculate the 'other' hypothesis of a pair of hypotheses."
  [pmf h]
  (first (cs/difference (set (keys pmf)) #{h})))

(defn posterior
  "Calculate the posterior given a prior PMF, datum and a likelihood.

  The datum is different from other datums. Each datum is a pair of colors; the 'difficulty'
  is that we do not know from which bag each color was drawn. As a result, a pair, 
  [:yellow :green], has a mass of 20*20 if bag 1 is from 1994 (and bag 2 is from 1996). And
  the pair [:yellow :green] has a mass of 14*20 if bag 1 is from 1996 and bag 2 is from 1994."
  [pmf datum likelihood]
  (pmf/normalize (reduce #(->> (* (likelihood (first datum) %2)
                                  (likelihood (second datum) (other pmf %2)))
                               (pmf/scale-mass %1 %2))
                         pmf
                         (keys pmf))))
