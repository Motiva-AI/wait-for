(ns wait-for.core-test
  (:require [wait-for.core :refer [wait-for]]
            [clojure.test :refer :all]))

(deftest wait-for-test
  (testing "success case"
    (let [result (promise)]
      (future
        (Thread/sleep 500)
        (deliver result :done))
      (wait-for #(= :done @result)
                :timeout 1)))
  (testing "failure case"
    (is (thrown? clojure.lang.ExceptionInfo
                 (wait-for #(= 1 0)
                           :timeout 1))))
  (testing "timeout-fn"
    (let [result (promise)]
      (wait-for #(= 1 0)
                :timeout-fn #(deliver result :done)
                :timeout 2)
      (is (= :done (deref result 1 :not-done))))))
