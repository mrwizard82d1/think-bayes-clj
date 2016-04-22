(ns think-bayes.core
  (:require [clojure.browser.repl :as repl]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn -main []
  (println "Hello world!"))

(set! *main-cli-fn* -main)
