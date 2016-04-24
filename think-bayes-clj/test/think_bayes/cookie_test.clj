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
    (t/are [e h] 
           (= e (pmf/probability (cookie/posterior cookie-pmf 
                                                   :vanilla
                                                   cookie/likelihood) h))
      (/ 3 5) "Bowl 1"
      (/ 2 5) "Bowl 2")
    (let [chocolate (cookie/posterior cookie-pmf 
                                      :chocolate
                                      cookie/likelihood)
          chocolate-vanilla (cookie/posterior chocolate
                                              :vanilla
                                              cookie/likelihood)
          chocolate-vanilla-chocolate (cookie/posterior chocolate-vanilla 
                                                        :chocolate
                                                        cookie/likelihood)]
      (t/are [e h] 
             (= e (pmf/probability chocolate h))
             (/ 1 3) "Bowl 1"
             (/ 2 3) "Bowl 2")
      (t/are [e h] 
             (= e (pmf/probability chocolate-vanilla h))
             (/ 3 7) "Bowl 1"
             (/ 4 7) "Bowl 2")
      (t/are [e h] 
             (= e (pmf/probability chocolate-vanilla-chocolate h))
             (/ 3 11) "Bowl 1"
             (/ 8 11) "Bowl 2"))))
