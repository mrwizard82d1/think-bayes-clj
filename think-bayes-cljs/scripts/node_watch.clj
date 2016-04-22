(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'think-bayes.core
   :output-to "out/think_bayes.js"
   :output-dir "out"})
