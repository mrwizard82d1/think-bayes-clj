(ns think-bayes.ex03-test
  (:require  [clojure.test :as t]
             [think-bayes.ex03 :as ex03]
             [think-bayes.pmf :as pmf]))

(t/deftest example3-test
  (t/testing "zipf-train-companies"
    (t/are [expected-distribution max-count] (= expected-distribution (ex03/zipf-train-companies max-count))
      [[1 1]] 1
      [[2 1] [1 2]] 2
      [[3 1] [1 2] [1 3]] 3
      [[4 1] [2 2] [1 3] [1 4]] 4
      [[10 1] [5 2] [3 3] [2 4] [2 5] [1 6] [1 7] [1 8] [1 9] [1 10]] 10))
  (t/testing "zipf-hypotheses"
    (t/are [expected max-count] (= expected (ex03/zipf-hypotheses max-count))
      [1] 1
      [1 1 2] 2
      [1 1 1 2 3] 3
      [1 1 1 1 2 2 3 4] 4
      [1 1 1 1 1 1 1 1 1 1 2 2 2 2 2 3 3 3 4 4 5 5 6 7 8 9 10] 10))
  #_(t/testing "generate-likelihood-function"
    (t/are [expecteds hypotheses data] (= expecteds 
                                          (map #((ex03/generate-likelihood-function hypotheses) data %) 
                                               hypotheses))
      [1] [[1 1]] 1
      [(/ 1 2) 1] [[1 2] [2 1]] 1
      1 [[1 2] [2 1]] 2))
  #_(t/testing "Single company"
    (let [hypotheses (map #(vector 1 %) (range 1 101))
          priors (ex03/priors hypotheses)
          _ (println priors)
          posteriors-60 (ex03/posteriors priors 60)]
      (t/is (= (repeat 59 0) (map pmf/probability (range 1 60)))))))
