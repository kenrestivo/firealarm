(ns firealarm.core-test
  (:use clojure.test
        firealarm.core))



(defn wrap-test
  "Make sure noir is wrapping middleware only once per handler."
  [handler]
  (println "wrapping")
  (try
    (throw (Exception. "wrapping handler"))
    (catch Exception e
      (cst/pst e)))
  (fn [request]
    (println "got request")
    (try
      (throw (Exception. "running handler"))
      (catch Exception e
        (cst/pst e)))
    (handler request)))



(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))