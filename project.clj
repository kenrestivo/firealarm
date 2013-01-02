(defproject firealarm "0.1.3"
  :description "A middleware for sending stacktraces from Noir apps"
  :url "http://github.com/kenrestivo/firealarm"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-stacktrace "0.2.5"] ;; anything less than this, and cljsbuild gets cranky
                 [clj-http "0.4.1"]])
