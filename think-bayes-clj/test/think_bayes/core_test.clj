(ns think-bayes.core-test
  (:require [clojure.test :as t]
            [think-bayes pmf-test table-test cookie-test 
             monty-python-test m-and-ms-test suite-test]))

(t/run-tests 'think-bayes.pmf-test
             'think-bayes.table-test
             'think-bayes.cookie-test
             'think-bayes.monty-python-test
             'think-bayes.m-and-ms-test
             'think-bayes.suite-test)

