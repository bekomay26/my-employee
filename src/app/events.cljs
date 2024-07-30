(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]
            [clojure.string :refer [includes? lower-case]]
            [app.helpers :as helpers]))

(reg-event-db :add-employee
              (fn [db [_ new-employee]]
                  (assoc-in db [:employees] (conj (:employees db) new-employee))))

(reg-event-db :search-employee
              (fn [db [_ text]]
                  (def updated-db (assoc-in db [:search-value] text))
                  (assoc-in updated-db
                            [:search-results]
                            (filter
                              (fn [the-employee] (helpers/filter-employee the-employee text))
                              (:employees updated-db)))
                  ))
