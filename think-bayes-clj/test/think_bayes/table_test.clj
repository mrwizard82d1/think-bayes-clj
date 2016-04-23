(ns think-bayes.table-test
  (:use clojure.test)
  (:require [think-bayes.table :as table]))

(deftest single-datum
  (let [cookie-table (-> #{:bowl1 :bowl2}
                           (table/make-table)
                           (table/add-prior {:bowl1 (/ 1 2) :bowl2 (/ 1 2)})
                           (table/add-likelihood (fn [d h]
                                                   (get-in {:bowl1 {:vanilla (/ 3 4) :chocolate (/ 1 4)}
                                                            :bowl2 {:vanilla (/ 1 2) :chocolate (/ 1 2)}} [h d]))))]
    (testing "vanilla datum"
      (let [actual-posterior (table/posterior cookie-table :vanilla)]
        (are [x y] (= x (table/probability actual-posterior y))
          (/ 3 5) :bowl1
          (/ 2 5) :bowl2)))
    (testing "chocolate datum"
      (let [actual-posterior (table/posterior cookie-table :chocolate)]
        (are [x y] (= x (table/probability actual-posterior y))
          (/ 1 3) :bowl1
          (/ 2 3) :bowl2)))
    (testing "vanilla datum, vanilla datum"
      (let [actual-posterior (-> cookie-table
                                 (table/posterior :vanilla)
                                 (table/posterior :vanilla))]
        (are [expected hypothesis] (= expected (table/probability actual-posterior hypothesis))
          (/ 9 13) :bowl1
          (/ 4 13) :bowl2)))
    (testing "chocolate datum, vanilla datum, chocolate datum"
      (let [actual-posterior (-> cookie-table
                                 (table/posterior :chocolate)
                                 (table/posterior :vanilla)
                                 (table/posterior :chocolate))]
        (are [expected hypothesis] (= expected (table/probability actual-posterior hypothesis))
            (/ 3 11) :bowl1
            (/ 8 11) :bowl2)))))

(deftest multiple-data
  (let [cookie-table (-> #{:bowl1 :bowl2}
                           (table/make-table)
                           (table/add-prior {:bowl1 (/ 1 2) :bowl2 (/ 1 2)})
                           (table/add-likelihood (fn [d h]
                                                   (get-in {:bowl1 {:vanilla (/ 3 4) :chocolate (/ 1 4)}
                                                            :bowl2 {:vanilla (/ 1 2) :chocolate (/ 1 2)}} [h d]))))]
    (testing "no data"
      (let [actual-posterior-seq (table/posterior-seq cookie-table [])]
        (is (empty? (map table/priors actual-posterior-seq)))))
    (testing "vanilla data"
      (let [actual-posterior-seq (table/posterior-seq cookie-table [:vanilla])]
        (is (= [{:bowl1 (/ 3 5) :bowl2 (/ 2 5)}]
               (map table/priors actual-posterior-seq)))))
    (testing "chocolate data"
      (let [actual-posterior-seq (table/posterior-seq cookie-table [:chocolate])]
        (is (= [{:bowl1 (/ 1 3) :bowl2 (/ 2 3)}]
               (map table/priors actual-posterior-seq)))))
    (testing "vanilla, vanilla data"
      (let [actual-posterior-seq (table/posterior-seq cookie-table [:vanilla :vanilla])]
        (is (= [{:bowl1 (/ 3 5) :bowl2 (/ 2 5)} {:bowl1 (/ 9 13) :bowl2 (/ 4 13)}]
               (map table/priors actual-posterior-seq)))))
    (testing "chocolate, vanilla, chocolate data"
      (let [actual-posterior-seq (table/posterior-seq cookie-table [:chocolate :vanilla :chocolate])]
        (is (= [{:bowl1 (/ 1 3) :bowl2 (/ 2 3)} {:bowl1 (/ 3 7) :bowl2 (/ 4 7)} { :bowl1 (/ 3 11) :bowl2 (/ 8 11)}]
               (map table/priors actual-posterior-seq)))))))

