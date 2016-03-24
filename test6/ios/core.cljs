(ns test6.ios.core
  (:require [clojure.string :refer [blank?]]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [test6.handlers]
            [test6.subs]
            [test6.db :as db]
            [test6.shared.ui :as ui]
            [test6.ios.ui :as ios-ui]
            [test6.ios.styles :as s]
            [test6.ios.scenes.root :refer [root-scene]]))

(defn app-root []
  [ios-ui/navigator
   {:initial-route {:title                 "Luno"
                    :component             (r/reactify-component root-scene)
                    :right-button-title    "Add city"
                    :on-right-button-press (fn [_]
                                             (ios-ui/show-dialog {:text     "Please, input city's name"
                                                                  :callback (fn [city]
                                                                              (dispatch [:load-weather city]))}))}
    :style         (get-in s/styles [:app])}])

(defn init []
  (dispatch-sync [:initialize-schema])
  (dispatch [:load-from-db :city])
  (.registerComponent ui/app-registry "test6" #(r/reactify-component app-root)))
