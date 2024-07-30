(ns app.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub :all-employees
  (fn [db _]
    (:employees db)))
