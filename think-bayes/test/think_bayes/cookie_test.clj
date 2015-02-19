(ns think-bayes.cookie-test
  (:require (clojure [test :refer :all])
            [think-bayes.cookie :as cookie]
            [think-bayes.pmf :as pmf]))

(deftest cookie-test
  (testing "No hypotheses"
    (let [no-hypothesis-cookie (cookie/init)]
      (testing "Empty PMF"
        (is (empty? no-hypothesis-cookie))
        (is (empty? (cookie/update no-hypothesis-cookie
                                   :unused :unused))))))
  (testing "A single hypothesis"
    (let [single-hypothesis-cookie (cookie/init ["lorem"])]
      (testing "PMF has single hypothesis with weight 1"
        (is (= {"lorem" 1} single-hypothesis-cookie)))
      (testing "Posterior has weight 2"
        (is (= {"lorem" 2}
               (cookie/update single-hypothesis-cookie
                              :unused (fn [_d _h] 2)))))))
  (testing "Many hypotheses"
    (let [many-hypotheses-cookie (cookie/init (range 1 7))]
      (testing "PMF has many hypotheses all with weight 1"
        (is (= (zipmap (range 1 7) (repeat 6 1))
              many-hypotheses-cookie)))
      (testing "Update and posterior has correct weights"
        (is (= 1/6
               (pmf/get-mass
                 (cookie/update many-hypotheses-cookie
                                3
                                (fn [_d _h] 1/6))
                 3))))))
  (testing "Cookie problem"
    (let [hypotheses ["Bowl 1" "Bowl 2"]
          cookie-pmf (cookie/init hypotheses)
          mixes {"Bowl 1" {:vanilla 3/4, :chocolate 1/4},
                 "Bowl 2" {:vanilla 1/2, :chocolate 1/2}}
          likelihood (fn [d h] (-> mixes (get h) (get d)))]
      (testing "Update after seeing :vanilla"
        (let [updated-pmf
              (cookie/update cookie-pmf :vanilla likelihood)]
          (is (= #{3/5 2/5}
                 (set (map #(pmf/probability updated-pmf %)
                            (keys updated-pmf))))))))))