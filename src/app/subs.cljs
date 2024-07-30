(ns app.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :all-employees
  (fn [db _]
    (:employees db)))

(reg-sub :filtered-employees
  (fn [db _]
    (:search-results db)))

(reg-sub :employee-search-value
  (fn [db _]
    (:search-value db)))
