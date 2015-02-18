(ns think-bayes.cookie-test
  (:require (clojure [test :refer :all])
            [think-bayes.cookie :as cookie]))

(deftest cookie-test
  (testing "No hypotheses"
    (let [no-hypothesis-cookie (cookie/init)]
      (testing "Empty PMF"
        (is (empty? no-hypothesis-cookie)))))
  (testing "A single hypothesis"
    (let [single-hypothesis-cookie (cookie/init ["lorem"])]
      (testing "PMF has single hypothesis with weight 1"
        (is (= {"lorem" 1} single-hypothesis-cookie)))))
  (testing "Many hypotheses"
    (let [many-hypotheses-cookie (cookie/init (range 1 7))]
      (testing "PMF has many hypotheses all with weight 1"
        (is (= (zipmap (range 1 7) (repeat 6 1))
              many-hypotheses-cookie))))))