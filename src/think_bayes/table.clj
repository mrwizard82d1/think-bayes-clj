(ns think-bayes.table)

(def mnm-hypotheses #{:mnm-mix-1994 :mnm-mix-1996})

(def mnm-priors {:mnm-mix-1994 (/ 1 2)
                 :mnm-mix-1996 (/ 1 2)})

(def mnm-mix-1994 {:brown 30
                   :yellow 20
                   :red 20
                   :green 10
                   :orange 10
                   :tan 10})

(def mnm-mix-1996 {:blue 24
                   :green 20
                   :orange 16
                   :yellow 14
                   :red 13
                   :brown 13})

(def mnm-pmf {:mnm-mix-1996 mnm-mix-1996
              :mnm-mix-1994 mnm-mix-1994})

(defn likelihood [data hypothesis & pairs]
  (reduce * 
          (get-in mnm-pmf [hypothesis data])
          (map #(get-in mnm-pmf (reverse %1)) (partition 2 pairs))))

(defn mnm-posteriors [colors]
  (let [priors (map mnm-priors mnm-hypotheses)
        likelihoods [(apply likelihood (interleave colors mnm-hypotheses))
                     (apply likelihood (interleave (reverse colors) mnm-hypotheses))]
        products (map * priors likelihoods)
        normalizing-constant (reduce + products)
        posteriors (zipmap mnm-hypotheses (map #(/ %1 normalizing-constant) products))]
    posteriors))

