(set-env!
 :source-paths #{"src"}
 :resource-paths #{"html"}

 :dependencies '[[adzerk/boot-cljs "1.7.228-1"]
                 [crisptrutski/boot-cljs-test "0.2.1"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(task-options! test-cljs {:js-env :nodesj})

(deftask dev
  "Launch immediate feedback development environment (IFDE)."
  []
  (comp
   (watch)
   (cljs)
   (target :dir #{"target"})))

(deftask testing
  "Add test for CLJ testing purposes."
  []
  (set-env! :source-paths #(conj % "test"))
  identity)
