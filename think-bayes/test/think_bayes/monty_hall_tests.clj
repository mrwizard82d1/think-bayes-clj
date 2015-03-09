(ns think-bayes.monty-hall-tests
  (:require (clojure [test :refer :all])
            [think-bayes.suite :as monty-hall]
            [think-bayes.pmf :as pmf]))

(deftest book-problems
  (testing "Monty Hall problem"
    (let [hypotheses "ABC"
          monty-hall-prior (monty-hall/init hypotheses)
          likelihood (fn [d h]
                       (cond
                         (= h d) 0
                         (= h \A) 1/2
                         :else 1))]
      (testing "After seeing \\B"
        (let [updated-pmf
              (monty-hall/update monty-hall-prior \B likelihood)]
          (is (= {\A 1/3 \B 0 \C 2/3}
                 (zipmap (keys updated-pmf)
                         (map #(pmf/probability updated-pmf %)
                              (keys updated-pmf))))))))))
