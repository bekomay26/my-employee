(ns app.components.toast
  (:require
    [uix.core :as uix :refer [defui $]]))

(defui toast [{:keys [message is-open on-close]}]
       (let [ref (uix/use-ref)]
            (uix/use-effect
              (fn []
                  (js/setTimeout on-close 5000)
                  (js/clearTimeout) []))

            (uix/use-effect
              (fn []
                  (if is-open (.show @ref) (.close @ref))
                  [is-open]))

            ($ :dialog.toast-dialog {:ref ref}
               ($ :div.toast-container
                  ($ :div.toast-body
                     ($ :p.toast-message message))
                  ($ :div.toast-progress)
                  ($ :button {:class "close-btn toast-close-btn" :on-click #(.close @ref)} "x")))))
