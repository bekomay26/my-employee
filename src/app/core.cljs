(ns app.core
  (:require
    [uix.core :as uix :refer [defui $]]
    [uix.dom]
    [app.page.home :refer [home]]
    [re-frame.core :as rf]
    [app.db]
    [app.subs]
    [app.events]))


(defui app []
       ($ :.app
          ($ home)))

(defonce root
         (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
      (rf/dispatch-sync [:initialize-db])
      (uix.dom/render-root ($ app) root))

(defn ^:export init []
      (render))
