(ns think-bayes.cookie-tests
  (:require (clojure [test :refer :all])
            [think-bayes.suite :as cookie]
            [think-bayes.pmf :as pmf]))

(deftest book-problems
  (testing "Cookie problem"
    (let [hypotheses ["Bowl 1" "Bowl 2"]
          cookie-pmf (cookie/init hypotheses)
          mixes {"Bowl 1" {:vanilla 3/4, :chocolate 1/4},
                 "Bowl 2" {:vanilla 1/2, :chocolate 1/2}}
          likelihood (fn [d h] (-> mixes (get h) (get d)))]
      (testing "After seeing :vanilla"
        (let [updated-pmf
              (cookie/update cookie-pmf :vanilla likelihood)]
          (is (= {"Bowl 1" 3/5 "Bowl 2" 2/5}
                 (zipmap (keys updated-pmf)
                         (map #(pmf/probability updated-pmf %)
                              (keys updated-pmf))))))))))