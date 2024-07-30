(ns app.page.home
  (:require
    [clojure.string :refer [lower-case]]
    ["antd" :refer [Table Input]]
    [app.components.employee-form :refer [employee-form]]
    [app.components.toast :refer [toast]]
    [app.components.button :refer [button]]
    [uix.core :as uix :refer [defui $]]
    [app.hooks :as hooks]
    [re-frame.core :as rf]))

(defn sort-dec [a b]
      (compare (lower-case a) (lower-case b)))

(def table-columns [
                    {:title "Employee" :dataIndex "employee" :sorter (fn [^js a ^js b] (sort-dec (.-employee a) (.-employee b)))}
                    {:title "Department" :dataIndex "department" :sorter (fn [^js a ^js b] (sort-dec (.-department a) (.-department b)))}
                    {:title "Title" :dataIndex "title" :sorter (fn [^js a ^js b] (sort-dec (.-title a) (.-title b)))}
                    {:title "Salary" :dataIndex "salary" :sorter (fn [^js a ^js b] (- (.-salary a) (.-salary b))) :render (fn [text] (clojure.string/replace (str "$ " text) #"\B(?=(\d{3})+(?!\d))" ","))}
                    ])


(defui home []
       (let [ref (uix/use-ref) [show-toast set-show-toast!] (uix/use-state false)]

            (defn on-success []
                  (set-show-toast! true))

            (defn on-close-toast []
                  (set-show-toast! false))

            (defn on-search [value _ _]
                  (rf/dispatch [:search-employee value]))

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
                  ($ :div.new-button-wrapper ($ button
                     {:type "button" :class "secondary" :on-click #(.showModal @ref)}
                     "+ New Employee"))

                  ($ (.-Search Input)
                     {:placeholder "Search by Name/ Department/ Title" :on-search on-search})

                  ($ Table
                     (clj->js
                       {:columns table-columns
                        ;show all employees if there's no search value. Else show the filtered result
                        :dataSource (if (zero? (count search-val)) all-employee-results filtered-results)
                        :pagination false
                        :rowKey "created-at"}))

                  ($ :dialog.modal {:ref ref}
                     ($ employee-form {:on-close #(.close @ref) :on-success on-success}))))))