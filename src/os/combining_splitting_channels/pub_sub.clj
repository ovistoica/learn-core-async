(ns os.combining-splitting-channels.pub-sub
  (:require [clojure.core.async :refer [pub sub <!! >!! thread chan close!]]))

(let [c (chan)
      p (pub c pos?)
      s1 (chan 1)
      s2 (chan 10)]
  (sub p true s1)
  (sub p false s2)


  (>!! c 42)
  (>!! c -42)
  (>!! c -2)
  (>!! c 2)

  (close! c)

  (thread
   (println "S1: " (<!! s1))
   (println "S1: " (<!! s1)))
  (thread
   (println "S2: " (<!! s2))
   (println "S2: " (<!! s2)))

  )
