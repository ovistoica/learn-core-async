(ns os.practical.blocking-io
  (:require [clojure.core.async :refer [<!! >!! chan]]))

(def logging-chan (chan 24))

(defn log
  [& args]
  (>!! logging-chan (apply str args)))

(future
 (loop []
   (when-some [v (<!! logging-chan)]
     (println v)
     (recur))))

(do (future
     (dotimes [x 100]
       (log "(..." x "...)")))
    (future
     (dotimes [x 100]
       (log "(..." x "...)"))))

