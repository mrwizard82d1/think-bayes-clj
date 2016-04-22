(require 'cljs.build.api)

(println "Building for node...")

(cljs.build.api/build "src"
                      {:main 'think-bayes.core
                       :output-to "out/think_bayes.js"
                       :output-dir "out"
                       :target :nodejs
                       :verbose true})
