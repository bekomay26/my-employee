(ns app.components.button
  (:require
    [uix.core :as uix :refer [defui $]]))

(defui button [{:keys [on-click class type children]}]
       ($ :button
          {:on-click on-click :class class :type type}
          children))

