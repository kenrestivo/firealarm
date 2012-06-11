(ns firealarm.core
  (:import java.util.Calendar java.text.SimpleDateFormat )
  (:require [clj-stacktrace.repl :as cst]
            [clj-http.client :as client])
  (:use [clojure.pprint :only [pprint]]))


(def sender (agent {} :error-mode :continue))

(defn file-reporter
  "Simple file dump, overwrites whatever was there previously.
   Takes the path to the file."
  [file-path]
  (fn [body]
    (spit file-path body)))



(defn post-reporter
  "Generates function to posts the error to another website,
   so that it can send email, in cases where you might not want your
   GMail password out in the open.
   Takes url to post to, name of site to report, and a token."
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
                      :token post-token}})))))
    



(defn exception-wrapper
  "Wraps exceptions by calling the reporter function given."
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

