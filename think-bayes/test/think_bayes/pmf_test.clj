(ns think-bayes.pmf-test
  (:require (clojure [string :as string]
                     [test :refer :all])
            [think-bayes.pmf :refer :all]))

(deftest pmf-test
  (testing "Empty PMF"
    (is (= {1 (/ 1 6)}
           (set-mass {} 1 (/ 1 6)))))
  (testing "Single item PMF"
    (let [single-item (set-mass {} 1 (/ 1 6))]
      (is (= {1 (/ 1 6) 2 (/ 1 3)}
             (set-mass single-item 2 (/ 1 3))))
      (is (= 1 (probability single-item 1)))
      (is (= (/ 1 3) (get-mass (multiply-mass single-item 1 2) 1)))))
  (testing "Many items PMF"
    (let [values (range 1 (inc 6))
          masses (map #(/ 1 %) values)
          many-items-pmf (reduce #(set-mass %1 %2 (/ 1 %2))
                                 {} values)]
      (is (= (zipmap values masses) many-items-pmf))
      (let [total-mass (reduce + masses)]
        (is (= (set (map #(/ % total-mass) masses))
               (set (map #(probability many-items-pmf %)
                         (keys many-items-pmf))))))
      (is (= (/ 3 4)
             (get-mass (multiply-mass many-items-pmf 4 3) 4)))))
  (testing "increment-mass"
    (is (= {"the" 2 "quick" 1 "little" 1 "brown" 1 "fox" 1
            "jumped" 1 "over" 1 "lazy" 1 "grey" 1 "lambs" 1}
           (reduce #(increment-mass %1 %2) {}
                   (string/split "the quick little brown fox jumped over the lazy grey lambs"
                          #"\s+"))))))

(deftest cookie-test
  (let [prior-pmf {"Bowl 1" (/ 1 2)
                   "Bowl 2" (/ 1 2)}
        posterior-pmf (-> prior-pmf
                           (multiply-mass "Bowl 1" (/ 3 4))
                           (multiply-mass "Bowl 2" (/ 1 2)))]
    (testing "Posterior PMF"
      (is (= (/ 6 10) (probability posterior-pmf "Bowl 1")))
      (is (= (/ 2 5) (probability posterior-pmf "Bowl 2"))))))
