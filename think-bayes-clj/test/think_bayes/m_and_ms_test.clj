(ns think-bayes.m-and-ms-test
  (:require [clojure.test :as t]
            [think-bayes.m-and-ms :as m-and-ms]
            [think-bayes.pmf :as pmf]))

(defn likelihood [d h]
  (let [mix {:bag1 {:brown 30
                    :yellow 20
                    :red 20
                    :green 10
                    :orange 10
                    :tan 10}
             :bag2 {:blue 24
                    :green 20
                    :orange 16
                    :yellow 14
                    :red 13
                    :brown 13}}]
    ;; In other similar situations, I've chosen to *NOT* return 0; however,
    ;; because I expect that clients may request the likelihood of a blue
    ;; M&M from bag1 or the likelihood of a tan M&M from bag2.
    (get-in mix [h d] 0)))

(let [m-and-ms-pmf (m-and-ms/make [:bag1 :bag2])]
  (t/deftest make-test
    (t/are [e v]
           (= e (pmf/probability m-and-ms-pmf v))
           (/ 1 2) :bag1
           (/ 1 2) :bag2))
  (let [posterior (m-and-ms/posterior m-and-ms-pmf
                                      [:yellow :green] 
                                      likelihood)]
    (t/deftest posterior-test
      (t/are [e h]
             (= e (pmf/probability posterior h))
             (/ 20 27) :bag1
             (/ 7 27) :bag2)))
  (let [posterior (m-and-ms/posterior m-and-ms-pmf
                                      [:yellow :blue] 
                                      likelihood)]
    (t/deftest posterior-test
      (t/are [e h]
             (= e (pmf/probability posterior h))
             1 :bag1
             0 :bag2)))
  (let [posterior (m-and-ms/posterior m-and-ms-pmf
                                      [:green :red] 
                                      likelihood)]
    (t/deftest posterior-test
      (t/are [e h]
             (= e (pmf/probability posterior h))
             (/ 13 53) :bag1
             (/ 40 53) :bag2))))


