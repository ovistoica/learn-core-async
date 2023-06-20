(ns os.learn-core-async-basics
  (:require [clojure.core.async :refer [chan put! take! >!! <!!
                                        close! thread go >! <!]])
  (:gen-class))

;; put & take
(comment
 (let [c (chan)]
   (put! c 42 (fn [v] (println "Sent: " v)))
   (take! c (fn [v] (println "Got: " v)))))

;; buffer
(comment
 (let [c (chan 1)]
   (future
    (dotimes [x 3]
      (>!! c x)
      (println "Sent: " x)))
   (future
    (dotimes [x 3]
      (println "Got: " (<!! c))))))


;; Close channel
(comment
 (let [c (chan)]
   (future
    (dotimes [x 2]
      (>!! c x))
    (close! c)
    (println "Closed"))
   (future
    (loop []
      (when-some [v (<!! c)]
        (println "Got: " v)
        (recur)))
    (println "Exiting"))))


;; threads
(comment
 (<!! (thread 42))
 (<!! (thread
       (let [t1 (thread "Thread 1")
             t2 (thread "Thread 2")]
         [(<!! t1)
          (<!! t2)]))))

;; When dealing with core.async
;; it is preferable to use thread and not future as
;; thread returns a channel and not a promise (future)
(comment
 (let [c (chan)]
   (thread
    (dotimes [x 3]
      (>!! c x)
      (println "Put " x)))
   (thread
    (dotimes [x 3]
      (println "Take " (<!! c))))))


(comment
 (<!! (go 42))

 (let [c (chan)]
   (go (dotimes [x 3]
         (>! c x)
         (println "Put: " x)))
   (go (dotimes [x 3]
         (println "Take: " (<! c)))))

 ;; How the go macro is represented for a single put
 (macroexpand '(go (>! (chan) 42)))

 (let [c (chan)]
   (go (doseq [x (range 3)]
         (>! c x)))
   (go (doseq [x (range 3)]
         (println "Take: " (<! c)))))



 )