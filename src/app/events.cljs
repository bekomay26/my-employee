(ns app.events
  (:require [re-frame.core :refer [reg-event-db]]
            [clojure.string]
            [app.fx :as fx]))

(reg-event-db :add-employee
              (fn [db [_ new-employee]]
                  (println "employee")
                  (println db)
                  (assoc-in db [:employees] (conj (:employees db) new-employee))))

(defn filter-employee [employee text]
      (println "employee")
      (println employee)
      (println (.-name employee))
      ;filter by name title department
      (let [{name :employee dept :department title :title} employee]
           (if (or
                 (clojure.string/includes? name text)
                 (clojure.string/includes? dept text)
                 (clojure.string/includes? title text)
                 ) true false)
           )
      )
(reg-event-db :search-employee
              (fn [db [_ text]]
                  (def updated-db (assoc-in db [:search-value] text))
                  (assoc-in updated-db
                            [:search-results]
                            (filter
                              (fn [the-employee] (filter-employee the-employee text))
                              (:employees updated-db)))
                  ))
