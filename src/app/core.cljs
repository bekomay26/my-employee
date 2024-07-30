(ns app.core
  (:require
    [uix.core :as uix :refer [defui $]]
    [uix.dom]
    [app.components.button :refer [button]]
    [re-frame.core :as rf]))


(defui app []
    ($ :.app
      ($ button {:class "primary"} "Primary")
      ($ button {:class "secondary"} "Secondary")))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))
