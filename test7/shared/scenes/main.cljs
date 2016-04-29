(ns test7.shared.scenes.main
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
    [test7.shared.ui :as ui]
    [test7.shared.components.city :refer [city-component]]))

(defn main-scene [{city-wrapper-fn :city-wrapper-fn
                   style           :style}]
  (let [data   (subscribe [:get-data])
        cities (vals @data)]
    [ui/view {:flex 1}
     [ui/view {:style (get style :view)
               :flex  1}
      [ui/scroll
       (for [city cities]
         ^{:key (str "container-" city)}
         [city-component {:city       city
                          :style      (get style :city-card)
                          :wrapper-fn city-wrapper-fn}])]]]))