(ns think-bayes.m-n-ms-suite
  (:require [think-bayes.suite :as suite]))

(def mixture-94 {:brown 30
                 :yellow 20
                 :red 20
                 :green 10
                 :orange 10
                 :tan 10})

(def mixture-96 {:blue 24
                 :green 20
                 :orange 16
                 :yellow 14
                 :red 13
                 :brown 13})

(def hypothesis-a {:bag-1 mixture-94 :bag-2 mixture-96})

(def hypothesis-b {:bag-1 mixture-96 :bag-2 mixture-94})

(defn priors
  "Return the prior probabilities of selecting from `hypothesis-a` and `hypothesis-b`."
  []
  (suite/priors [hypothesis-a hypothesis-b]))

(defn likelihood
  "Calculate the likelihood of `data` given a `hypothesis`."
  [data hypothesis]
  (get-in hypothesis data))

(defn posteriors
  "Calculated the posteriors of `priors` after observing `data`."
  [priors data]
  (suite/posteriors priors data likelihood))
