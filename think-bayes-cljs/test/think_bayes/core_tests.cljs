(ns think-bayes.core-tests
  (:require [cljs.test :as t]
            [think-bayes.pmf-tests :as pmf-tests]))

(enable-console-print!)

#_(t/deftest my-first-test
  (t/is (= 1 2)))

(set! *main-cli-fn* #(t/run-tests 'think-bayes.pmf-tests))
