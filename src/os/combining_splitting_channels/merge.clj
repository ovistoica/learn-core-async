(ns os.combining-splitting-channels.merge
  (:refer-clojure :exclude [merge])
  (:require [clojure.core.async :refer [merge >!! <!! chan]]))

(let [c1 (chan 10)
      c2 (chan 10)
      cm (merge [c1 c2])]
  (>!! c1 1)
  (>!! c1 2)
  (>!! c2 3)
  (>!! c2 4)

  (dotimes [x 4]
    (println (<!! cm))))
