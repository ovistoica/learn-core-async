(ns os.backpressure.core
  (:require [clojure.core.async :refer [go <! >! <!! chan close!]]))

;; you can add parallelism to your
;; pipes so that on one station you have
;; 2 workers on that section if you know
;; that this part is too slow
(defn map-pipe
  ([in out f]
   (map-pipe 0 in out f))
  ([p in out f]
   (dotimes [_ p]
     (go (loop []
           (when-some [v (<! in)]
             (>! out (f v))
             (recur)))
         (close! out)))))

(let [in (chan 1024)
      a (chan 1024)
      b (chan 1024)
      c (chan 1024)
      out (chan 1024)]
  (map-pipe in a step-a)
  (map-pipe a b step-b)
  (map-pipe 2 b c step-c)
  (map-pipe c out step-d))