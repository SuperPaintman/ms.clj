(ns ms.core-test
  (:require [clojure.test :refer :all]
            [ms.core :refer :all]))


(deftest string-to-ms
  (testing "should works"
           (ms "1 day"))
  (testing "should preserve \"ms\""
           (is (= (ms "100") 100)))
  (testing "should prepend time if it not exist"
           (is (= (ms "day") 86400000)))
  (testing "should convert \"s\" to ms"
           (is (= (ms "1s") 1000)))
  (testing "should convert from \"m\" to ms"
           (is (= (ms "1m") 60000)))
  (testing "should convert from \"h\" to ms"
           (is (= (ms "1h") 3600000)))
  (testing "should convert \"d\" to ms"
           (is (= (ms "2d") 172800000)))
  (testing "should convert \"y\" to ms"
           (is (= (ms "1y") 31557600000)))
  (testing "should convert \"ms\" to ms"
           (is (= (ms "100ms") 100)))
  (testing "should work with decimals"
           (is (= (ms "1.5h") 5400000)))
  (testing "should work with multiple spaces"
           (is (= (ms "1   s") 1000)))
  (testing "should work with tabs"
           (is (= (ms "1\ts") 1000))
           (is (= (ms "1\t\t\t\t\ts") 1000)))
  (testing "should work with mixed tabs and spaces"
           (is (= (ms "1  \ts") 1000))
           (is (= (ms "1\t\t  \t\ts") 1000)))
  (testing "should be case-insensitive"
           (is (= (ms "1.5H") 5400000)))
  (testing "should work with numbers starting with \".\""
           (is (= (ms ".5s") 500)))
  (testing "should floor" ; TODO: round
           (is (= (ms "0.2ms") 0))
           (is (= (ms "1.5ms") 1))
           (is (= (ms "1.9ms") 1))))

(deftest ms-to-short-string) ; TODO

(deftest ms-to-long-string) ; TODO
