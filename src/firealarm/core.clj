(ns firealarm.core
  (:import java.util.Calendar java.text.SimpleDateFormat )
  (:require [clj-stacktrace.repl :as cst]
            [clj-http.client :as client])
  (:use [clojure.pprint :only [pprint]]))


(def sender (agent {} :error-mode :continue))

(defn file-reporter
  "Simple file dump, overwrites whatever was there previously.
   The function returns its body arg, so they can be composed"
  [file-path]
  (fn [body]
    (spit file-path body)
    body))


(defn post-reporter
  "Generates function to posts the error to another website,
   so that it can send email.
   In cases where you might not want your GMail password out in the open.
   The function returns its body arg, so they can be composed"
  [post-url site-name post-token]
  (fn [body]
    (send-off
     sender
     (fn [_]
       (client/post post-url
                    {:form-params
                     {:subject
                      (str "Error: " site-name)
                      :body body
                      :token post-token}})))
    body))



(defn exception-wrapper
  "mode is :dev by default in jetty/noir.
   It's $PORT by default on Heroku. Accept that."
  [reporter]
  (fn wrap-exception
    [handler]
    (fn [request]
      (try
        (handler request)
        (catch Throwable e
          (reporter (str ( -> (Calendar/getInstance) .getTime .toString)
                         "\n"
                         (with-out-str  (cst/pst e)
                           (pprint request))))
          (throw e))))))

