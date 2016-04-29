(ns test7.ios.core
  (:require [clojure.string :refer [blank?]]
            [reagent.core :as r]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [test7.handlers]
            [test7.subs]
            [test7.db :as db]
            [test7.shared.ui :as ui]
            [test7.ios.ui :as ios-ui]
            [test7.ios.styles :as s]
            [test7.ios.scenes.root :refer [root-scene]]))

(defn app-root []
  [root-scene]
  ;;[ios-ui/navigator
  #_[ui/view
   {:initial-route {:title                 "Luno"
                    :component             (r/reactify-component root-scene)
                    :right-button-title    "Add city"
                    :on-right-button-press (fn [_]
                                             (ios-ui/show-dialog {:text     "Please, input city's name"
                                                                  :callback (fn [city]
                                                                              (dispatch [:load-weather city]))}))}
    :style         (get-in s/styles [:app])}
    ]
    )

(defn init []
  (dispatch-sync [:initialize-schema])
  (dispatch [:load-from-db :city])
  (.registerComponent ui/app-registry "test7" #(r/reactify-component app-root)))
