(ns firealarm.core
  (:import java.util.Calendar java.text.SimpleDateFormat )
  (:require [clj-stacktrace.repl :as cst]
            [clj-http.client :as client])
  (:use [clojure.pprint :only [pprint]]))



(defn file-reporter
  "Simple file dump, overwrites whatever was there previously."
  [body]
  (spit "/tmp/noir-error.log" body))


(defn post-reporter
  "Posts the error to another website, so that it can send email.
   In cases where you might not want your GMail password out in the open."
  [body]
  (client/post (get (System/getenv) "FIREALARM_POST_URL")
               {:form-params
                {:subject
                 (str "Error: "
                      (get (System/getenv) "FIREALARM_SITENAME"))
                 :body body
                 :token (get (System/getenv) "FIREALARM_POST_TOKEN")}}))



;;; TODO: allow juxting several of these to compose them nicely

(defn exception-wrapper
  "mode is :dev by default in jetty/noir.
   It's $PORT by default on Heroku. Accept that."
  [mode]
  (let [reporter (if (= mode :dev)
                   file-reporter
                   post-reporter)]
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
            (throw e)))))))

