(ns cookbook.aggregation
  (:require [cascalog.api :refer :all]
            [cascalog.more-taps :refer [hfs-delimited]]))

(defn init-aggregate-stats [date url user]
  (let [day (.substring date 0 8)]
    {"URL"  {url 1}
     "User" {user 1}
     "Day"  {date 1}}))

(def combine-aggregate-stats
  (partial merge-with (partial merge-with +)))

(defparallelagg aggregate-stats
  :init-var    #'init-aggregate-stats
  :combine-var #'combine-aggregate-stats)

(defmain Main [in out & args]
  (?<-
    (hfs-textline out :sinkmode :replace)
    [?out]
    ((hfs-delimited in :delimiter ",") ?date ?url ?user)
    (aggregate-stats ?date ?url ?user :> ?out)))
