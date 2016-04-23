(ns think-bayes.core-test
  (:require [clojure.test :as t]))

(t/run-tests 'think-bayes.pmf-test
             'think-bayes.table-test)

