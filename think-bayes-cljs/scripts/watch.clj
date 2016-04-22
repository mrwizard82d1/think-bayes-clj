(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'think-bayes-cljs.core
   :output-to "out/think_bayes_cljs.js"
   :output-dir "out"})
