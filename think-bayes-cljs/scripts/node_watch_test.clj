(require '[cljs.build.api :as b])

(b/watch (b/inputs "src" "test")
         {:main 'think-bayes.core_tests
          :output-to "out/think_bayes_test.js"
          :output-dir "out"
          :target :nodejs
          :verbose true})
