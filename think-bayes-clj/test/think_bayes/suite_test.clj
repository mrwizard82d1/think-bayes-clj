(ns think-bayes.suite-test
  (:require [clojure.test :as t]
            [think-bayes.suite :as suite]
            [think-bayes.pmf :as pmf]))


(let [single-hypothesis-pmf (suite/make [:flumen])
        many-hypotheses [:inaurant :contemptus :miscellanea]
        many-hypotheses-pmf (suite/make many-hypotheses)]
    (t/deftest make-test 
      (t/is (= 1 (pmf/probability single-hypothesis-pmf :flumen)))
      (doseq [h many-hypotheses]
        (t/is (= (/ 1 3) (pmf/probability many-hypotheses-pmf h)))))
    (t/deftest print-test
      (t/is (= (str :flumen " " 1 "\n")
               (with-out-str (suite/print-suite single-hypothesis-pmf))))
      (doseq [h many-hypotheses]
        (t/is (= ":inaurant 1/3\n:contemptus 1/3\n:miscellanea 1/3\n"
                 (with-out-str (suite/print-suite many-hypotheses-pmf)))))))

(t/deftest monty-python-test
  (let [monty-python-suite (suite/make "ABC")
        likelihood (fn [d h]
                     (cond
                       (= d h) 0
                       (= h \A) (/ 1 2)
                       :else 1))]
    (t/is (= {\A (/ 1 3)
              \B 0
              \C (/ 2 3)}
             (suite/posterior monty-python-suite \B likelihood)))))


(t/deftest m-and-ms-test
  (let [m-and-ms-suite (suite/make [[1994 1996] 
                                    [1996 1994]])
        mixes {1994 {:brown 30
                     :yellow 20
                     :red 20
                     :green 10
                     :orange 10
                     :tan 10}
               1996 {:blue 24
                     :green 20
                     :orange 16
                     :yellow 14
                     :red 13
                     :brown 13}}
        m-and-ms-likelihood (fn [d h] 
                              (* (get-in mixes [(first h) (first d)] 0)
                                 (get-in mixes [(second h) (second d)] 0)))]
    (t/is (= {[1994 1996] (/ 20 27)
              [1996 1994] (/ 7 27)}
             (suite/posterior m-and-ms-suite 
                              [:yellow :green] 
                              m-and-ms-likelihood)))
    (t/is (= {[1994 1996] 1
              [1996 1994] 0}
             (suite/posterior m-and-ms-suite 
                              [:tan :green] 
                              m-and-ms-likelihood)))
    (t/is (= {[1994 1996] 0
              [1996 1994] 1}
             (suite/posterior m-and-ms-suite 
                              [:green :tan] 
                              m-and-ms-likelihood)))
    (t/is (= {[1994 1996] (/ 1 2)
              [1996 1994] (/ 1 2)}
             (suite/posterior m-and-ms-suite 
                              [:yellow :yellow] 
                              m-and-ms-likelihood)))))
