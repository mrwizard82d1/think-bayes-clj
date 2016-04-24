(ns think-bayes.cookie-test
  (:require [clojure.test :as t]
            [think-bayes.cookie :as cookie]
            [think-bayes.pmf :as pmf]))


(t/deftest make-test
  (t/are [e v] (= e (pmf/probability (cookie/make ["Bowl 1" "Bowl 2"]) v))
         (/ 1 2) "Bowl 1"
         (/ 1 2) "Bowl 2"))


(t/deftest posterior-test
  (let [cookie-pmf (cookie/make ["Bowl 1" "Bowl 2"])]
    (t/are [e h] (= e (pmf/probability (cookie/posterior cookie-pmf :vanilla) h))
      (/ 3 5) "Bowl 1"
      (/ 2 5) "Bowl 2")))
