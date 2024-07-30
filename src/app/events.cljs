(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]
            [app.fx :as fx]))

(reg-event-db :add-employee
                 (fn [db [_ new-employee]]
                     (println "employee")
                     (println db)
                     (assoc-in db [:employees] (conj (:employees db) new-employee))))
