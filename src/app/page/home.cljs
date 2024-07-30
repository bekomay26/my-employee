(ns app.page.home
  (:require
    [clojure.string :as str]
    ["antd" :refer [Table Input]]
    [app.components.employee-form :refer [employee-form]]
    [app.components.toast :refer [toast]]
    [app.components.button :refer [button]]
    [uix.core :as uix :refer [defui $]]
    [app.hooks :as hooks]
    [re-frame.core :as rf]))

(defn sort-dec [a b]
      (compare (str/lower-case a) (str/lower-case b)))

(def table-columns [
                    {:title "Employee" :dataIndex "employee" :sorter (fn [^js a b] (sort-dec (.-employee a) (.-employee b))) :showSorterTooltip {:target "full-header"}}
                    {:title "Department" :dataIndex "department" :sorter (fn [^js a b] (sort-dec (.-department a) (.-department b)))}
                    {:title "Title" :dataIndex "title" :sorter (fn [^js a b] (sort-dec (.-title a) (.-title b)))}
                    {:title "Salary" :dataIndex "salary" :sorter (fn [^js a b] (- (.-salary a) (.-salary b)))}
                    ])


(defui home []
       (let [ref (uix/use-ref) [show-toast set-show-toast!] (uix/use-state false)]

            (defn on-success []
                  (set-show-toast! true))

            (defn on-close-toast []
                  (set-show-toast! false))

            (defn on-search [value _ _]
                  (rf/dispatch [:search-employee value])
                  (js/console.log value))

            (def search-val
              (hooks/use-subscribe [:employee-search-value])
              )

            (def all-employee-results
              (hooks/use-subscribe [:all-employees])
              )

            (def filtered-results
              (hooks/use-subscribe [:filtered-employees])
              )

            ($ :div.home-page
               ($ :div.page-header
                  ($ :p.logo "Employee"))
               ($ :main.page-body
                  ($ toast {:message "Employee successfully created" :is-open show-toast :on-close on-close-toast})
                  ($ button
                     {:type "button" :class "secondary" :on-click #(.showModal @ref)}
                     "+ New Employee")
                  ($ (.-Search Input)
                     {:placeholder "Search by Name/ Department/ Title" :on-search on-search})

                  ;TODO: add search

                  ($ Table
                     (clj->js
                       {:columns table-columns
                        :dataSource (if (zero? (count search-val)) all-employee-results filtered-results)
                        :pagination false}))

                  ($ :dialog.modal {:ref ref}
                     ($ employee-form {:on-close #(.close @ref) :on-success on-success}))))))