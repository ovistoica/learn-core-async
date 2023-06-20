(ns os.alts

  (:require [clojure.core.async :refer [thread alt!! alts!! <!! >!! chan go
                                        <! >! put!]]))

(comment
 (let [c1 (chan 1)
       c2 (chan 2)]
   (>!! c1 42) (>!! c2 44)
   (thread
    (let [[v, c] (alts!! [c1 c2])]
      (println "Value: " v)
      (println "Chan1 ?" (= c1 c))
      (println "Chan2 ?" (= c2 c))
      )))

 (let [c1 (chan 1)
       c2 (chan 1)]
   (thread
    (let [[v c] (alts!! [c1 [c2 42]])]
      (println "Value: " v)
      (println "Chan1 ?" (= c1 c))
      (println "Chan2 ?" (= c2 c)))))


 ;; Alt can specify what to return
 (let [c1 (chan 1)
       c2 (chan 1)]
   (thread
    (println (alt!! [c1] ([v] [:got v])
                    [[c2 42]] ([v] {:sent v})))
    (comment (let [[v c] (alts!! [c1 [c2 42]])]
               (println "Value: " v)
               (println "Chan1 ?" (= c1 c))
               (println "Chan2 ?" (= c2 c))))))




 ;; alt priority

 (let [c1 (chan 1)
       c2 (chan 2)]
   (>!! c1 42)
   (>!! c2 43)
   (thread
    (let [[v c] (alts!! [c1 c2] :priority true)]
      (println "Value: " v)
      (println "Chan1 ?" (= c1 c))
      (println "Chan2 ?" (= c2 c)))))
 )


