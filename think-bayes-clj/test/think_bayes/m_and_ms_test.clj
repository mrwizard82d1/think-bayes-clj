(ns think-bayes.m-and-ms-test
  (:require [clojure.test :as t]
            [think-bayes.m-and-ms :as m-and-ms]
            [think-bayes.pmf :as pmf]))

(let [m-and-ms-pmf (m-and-ms/make [:bag1 :bag2])]
  (t/deftest make-test
    (t/are [e v]
           (= e (pmf/probability m-and-ms-pmf v))
           (/ 1 2) :bag1
           (/ 1 2) :bag2))
  (let [posterior (m-and-ms/posterior m-and-ms-pmf
                                      [:yellow :green] 
                                      m-and-ms/likelihood)]
    (t/deftest make-test
      (t/are [e h]
             (= e (pmf/probability posterior h))
             (/ 20 27) :bag1
             (/ 7 27) :bag2))))


