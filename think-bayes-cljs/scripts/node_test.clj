(require 'cljs.build.api)

(println "Building for (test) node...")

(cljs.build.api/build (cljs.build.api/inputs "src" "test")
                      {:main 'think-bayes.core_tests
                       :output-to "out/think_bayes_tests.js"
                       :output-dir "out"
                       :target :nodejs
                       :verbose true})
