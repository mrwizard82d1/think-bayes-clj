(ns think-bayes.monty-python-test
  (:require [clojure.test :as t]
            [think-bayes.monty-python :as monty-python]
            [think-bayes.pmf :as pmf]))

(t/deftest make-test
  (t/are [e v] (= e (pmf/probability (monty-python/make "ABC") v))
         (/ 1 3) \A
         (/ 1 3) \B
         (/ 1 3) \C))

(t/deftest posterior-test
  (let [monty-python-pmf (monty-python/make "ABC")]
    (t/are [e h]
           (= e (pmf/probability (monty-python/posterior monty-python-pmf
                                                     \B
                                                     monty-python/likelihood)
                                 h))
           (/ 1 3) \A
           0 \B
           (/ 2 3) \C)))
