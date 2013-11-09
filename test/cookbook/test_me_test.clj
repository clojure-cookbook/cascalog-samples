(ns cookbook.test-me-test
  (:require [cookbook.test-me :refer :all]
            [cascalog.api :refer [hfs-textline]]
            [midje
              [sweet :refer :all]
              [cascalog :refer :all]]))

(fact "Query should return capitalized versions of the input names."
  (capitalize-authors-query :author-path) => (produces [["LUKE VANDERHART"]
                                                        ["RYAN NEUFELD"]])
  (provided
    (hfs-textline :author-path) => [["Luke Vanderhart"]
                                    ["Ryan Neufeld"]]))
