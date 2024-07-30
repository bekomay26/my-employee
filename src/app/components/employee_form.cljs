(ns app.components.employee-form
  (:require
    [clojure.string :as str]
    ["react-select" :default Select]
    [app.components.button :refer [button]]
    ["antd" :refer [InputNumber Form Input]]
    [uix.core :as uix :refer [defui $]]
    [re-frame.core :as rf]))


(def employee-options [{:value "engineering" :label "Engineering"}
                       {:value "hr" :label "HR"}
                       {:value "marketing" :label "Marketing"}
                       {:value "product" :label "Product"}
                       {:value "sales" :label "Sales"}])

(defui employee-form [{:keys [on-close on-success]}]
       (def form (let [[the-form] (.useForm Form)] the-form))

       (defn close-modal []
             (.resetFields form)
             (on-close))

       (defn on-dept-change [val va2]
             (js/console.log "fdg ---- values")
             ;(js/console.log val)
             (js/console.log val2)
             )

       (defn on-finish [values]
             (rf/dispatch [:employee/add (assoc (js->clj values) :created-at (js/Date.now) :department (.-label (.-department values)))])
             (close-modal)
             (on-success))

       ($ Form {:form form :on-finish on-finish :class "employee-form"}
          ($ :div.form-header
             ($ :h4.form-title "New Employee")
             ($ :button.close-btn {:type "button" :on-click close-modal} "X"))

          ($ Form.Item {:name "employee" :rules (clj->js [{:required true :message "Required"}])} ($ Input {:type "text" :placeholder "Employee Name" :class "form-input"}))
          ($ Form.Item {:name "department" :rules (clj->js [{:required true :message "Required"}])}
             ($ Select {
                        :options     (clj->js employee-options)
                        :placeholder "Department"
                        :class       "department-list"
                        :on-change   on-dept-change
                        }))

          ($ Form.Item {:name "title" :rules (clj->js [{:required true :message "Required"}])}
             ($ Input {:type "text" :placeholder "Title" :class "form-input"}))
          ($ Form.Item {:name "salary" :rules (clj->js [{:required true :message "Required"}])}
             ($ InputNumber
                (clj->js {
                          :controls    false
                          :min         0
                          :placeholder "Salary"
                          :formatter   (fn [value] (when (> value 0) (str/replace (str "$ " value) #"\B(?=(\d{3})+(?!\d))" ",")))
                          :parser      (fn [value] (str/replace value #"\$\s?|(,*)" ""))
                          })))
          ($ :div.action-buttons
             ($ button {:type "button" :class "secondary" :on-click close-modal} "Cancel")
             ($ button {:type "submit" :class "primary"} "Create")
             )))