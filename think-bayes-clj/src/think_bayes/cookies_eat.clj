(ns think-bayes.cookies-eat
  (:require [think-bayes.suite :as suite]))

(def priors (suite/priors [:bowl-1 :bowl-2]))

(def initial-bowls {:bowl-1 {:vanilla 30 :chocolate 10}
                    :bowl-2 {:vanilla 20 :chocolate 20}})

;; This `var` could be private; except that I may need to reset it. Hmmm.
(def bowls (atom initial-bowls))

(defn likelihood!
  "Calculate the likelihood of `data` given a `hypothesis`.

  This function has a side-effect because the observer eats the cookies."
  [data hypothesis]
  (let [result (/ (get-in @bowls [hypothesis data]) (reduce + (vals (@bowls hypothesis))))]
    (swap! bowls #(update-in %1 [hypothesis data] dec))
    result))

(defn posteriors
  "Calculate the posterior probability after seeing `data` with `priors`."
  [priors data]
  (suite/posteriors priors data likelihood!))

