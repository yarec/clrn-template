(ns test7.shared.components.demolistview
  (:require
    [reagent.core :as r]
    [test7.shared.ui :as ui]))


(def ds (React.ListView.DataSource. #js{:rowHasChanged (fn[a b] false)}))

(defn list-view-source [v]
  (let [res #js[]]
    (doseq [item v]
      (.push res (r/atom item)))
    (r/atom (js->clj res))))

(def rows (list-view-source (clj->js (range 50))))

(def row-comp (r/reactify-component (fn[props]
                                      (let [row (props :row)]
                                        (print props)
                                        [ui/touchable-highlight {:style {:border-top-width 1 :border-color "#000"} :on-press #(print "list" (str @row))}
                                         [ui/text @row]]))))
(defn demo-list-view []
    [ui/list-view {:dataSource (.cloneWithRows ds (clj->js @rows))
                    :render-row (fn[row]
                                  (js/React.createElement row-comp #js{:row row}))
                    :style {:left 0 :right 0 :height 250 :border-width 1 :border-color "#000"}}]
    )
