(ns os.practical.http-client
  (:require [org.httpkit.client :as http]
            [clojure.core.async :refer [chan <! >! go put! <!! >!!]]
            [cheshire.core :as cheshire]))

(defn http-get
  [url]
  (let [c (chan)]
    (println url)
    (http/get url
              (fn [r] (put! c r)))
    c))

(defn request-&-process
  [nm]
  (go
    (->
     (str "http://imdbapi.poromenos.org/js/?name=%25" nm "%25")
     http-get
     <!
     :body
     (cheshire/parse-string true))))

(comment
 (<!! (request-&-process "Matrix")))