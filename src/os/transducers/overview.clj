(ns os.transducers.overview)

(defn -map [f col]
  (reduce
   (fn [acc v]
     (conj acc (f v)))
   []
   col))

(defn -filter [f col]
  (reduce
   (fn [acc v]
     (if (f v)
       (conj acc v)
       acc))
   []
   col))

;; base for a transducer
(defn -map-t
  [f]
  (fn [rf]
    (fn [acc v]
      (rf acc (f v)))))


;; base for a transducer
(defn -filter-t
  [f]
  (fn [rf]
    (fn [acc v]
      (if (f v)
        (rf acc  v)
        acc))))


(def inc-xf (comp (-map-t inc)
                  (-filter-t even?)))

(reduce (inc-xf conj) [] [1 2 3 4])
