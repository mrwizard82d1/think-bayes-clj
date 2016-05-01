(ns think-bayes.core-test
  (:require [clojure.test :as t]
            [think-bayes pmf-test table-test cookie-test monty-python-test]))

(t/run-tests 'think-bayes.pmf-test
             'think-bayes.table-test
             'think-bayes.cookie-test
             'think-bayes.monty-python-test)

