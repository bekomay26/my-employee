(ns app.helpers
  (:require [clojure.string :refer [includes? lower-case]]))

(defn string-contains? [word sub-string]
      ;case insensitive
      (includes? (lower-case word) (lower-case sub-string))
      )

(defn filter-employee [employee text]
      ;filter by name title department
      (let [{name :employee dept :department title :title} employee]
           (if (or
                 (string-contains? name text)
                 (string-contains? dept text)
                 (string-contains? title text)
                 ) true false)
           )
      )