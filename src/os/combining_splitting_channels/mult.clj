(ns os.combining-splitting-channels.mult
  (:require [clojure.core.async :refer [mult tap >!! <!! chan thread]]) )

(let [c (chan 10)
      m (mult c)
      t1 (chan 10)
      t2 (chan 10)]
  (tap m t1)
  (tap m t2)
  (>!! c 42)

  (thread
   (println "Got from T1: " (<!! t1)))


  (thread
   (println "Got from T2: " (<!! t2)))

  )