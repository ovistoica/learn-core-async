(ns os.combining-splitting-channels.reduce-and-into
  (:require [clojure.core.async :as async]))

(let [c (async/chan)]
  (async/onto-chan! c (range 10))
  (async/<!! (async/reduce conj [] c)))

(let [c (async/chan)]
  (async/onto-chan! c (range 10))
  (async/<!! (async/into #{} c)))
