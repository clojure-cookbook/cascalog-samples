(ns cookbook.etl
  (:require [cascalog.api :refer :all]
            [clojure.data.json :as json]))

(defn get-vec
  "Wrap the result in a vector for Cascalog to consume."
  [m k]
  (vector
   (get m k)))

(defn vec->csv
  "Turn a vector into a csv string. (Not production quality)."
  [v]
  (apply str (interpose "," v)))

(defmain Main [in out & args]
  (?<- "My Flow"
   (hfs-textline out :sinkmode :replace)
   [?out-csv]
   ((hfs-textline in) ?in-json)
   (json/read-str ?in-json :> ?book-map)
   (get-vec ?book-map "authors" :> ?authors)
   (vec->csv ?authors :> ?out-csv)))
