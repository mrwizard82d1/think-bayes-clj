(ns think-bayes.table)

(defn mnm-likelihood [data hypothesis & pairs]
  (let [mnm-mix-1994 {:brown 30
                      :yellow 20
                      :red 20
                      :green 10
                      :orange 10
                      :tan 10}
        mnm-mix-1996 {:blue 24
                      :green 20
                      :orange 16
                      :yellow 14
                      :red 13
                      :brown 13}
        mnm-pmf {:mnm-mix-1996 mnm-mix-1996
                 :mnm-mix-1994 mnm-mix-1994}]
    (reduce * 
            (get-in mnm-pmf [hypothesis data])
            (map #(get-in mnm-pmf (reverse %1)) (partition 2 pairs)))))

(defn mnm-posteriors [colors]
  (let [mnm-hypotheses #{:mnm-mix-1994 :mnm-mix-1996}
        mnm-priors {:mnm-mix-1994 (/ 1 2)
                    :mnm-mix-1996 (/ 1 2)}
        priors (map mnm-priors mnm-hypotheses)
        likelihoods [(apply mnm-likelihood (interleave colors mnm-hypotheses))
                     (apply mnm-likelihood (interleave (reverse colors) mnm-hypotheses))]
        products (map * priors likelihoods)
        normalizing-constant (reduce + products)
        posteriors (zipmap mnm-hypotheses (map #(/ %1 normalizing-constant) products))]
    posteriors))

(defn monty-hall-likelihood [data hypothesis & pairs]
  (let [monty-hall-pmf {:behind-a {:open-b (/ 1 2)}
                        :behind-b {:open-b 0}
                        :behind-c {:open-b 1}}]
    (reduce * 
            (get-in monty-hall-pmf [hypothesis data])
            (map #(get-in monty-hall-pmf (reverse %1)) (partition 2 pairs)))))

(defn monty-hall-posteriors [door]
  (let [monty-hall-hypotheses #{:behind-a :behind-b :behind-c}
        monty-hall-priors {:behind-a (/ 1 3)
                           :behind-b (/ 1 3)
                           :behind-c (/ 1 3)}
        priors (map monty-hall-priors monty-hall-hypotheses)
        likelihoods (map #(monty-hall-likelihood door %) monty-hall-hypotheses)
        products (map * priors likelihoods)
        normalizing-constant (reduce + products)
        posteriors (zipmap monty-hall-hypotheses 
                           (map #(/ %1 normalizing-constant) products))]
    posteriors))

