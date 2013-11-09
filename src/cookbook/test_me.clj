(ns cookbook.test-me
  (:require [cascalog.api :refer :all]))

(defn capitalize [s]
  (.toUpperCase s))

(defn capitalize-authors-query [author-path]
  (<- [?capitalized-author]
    ((hfs-textline author-path) ?author)
    (capitalize ?author :> ?capitalized-author)))
