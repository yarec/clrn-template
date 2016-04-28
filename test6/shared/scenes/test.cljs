(ns test6.shared.scenes.test
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
    [test6.shared.ui :as ui]))

(def github-link
  "http://github.com/test6-react-native")

(def changelog-link
  "https://github.com/test6-react-native/blob/master/CHANGELOG.md")

(def bg-url
  "http://pic.qiantucdn.com/58pic/15/32/44/00e58PICdyf_1024.jpg")

(defn test-comp [style]
  [ui/view
   [ui/switch {:on-value-change #(print 11)}]
   [ui/image {:style  (get style :card)
              :source {:uri bg-url}}]

   [ui/image {:style  (get style :card)
              :source {:uri bg-url}}
    [ui/text "text on background"]
    ]
   ]
  )

(def ds (React.ListView.DataSource. #js{:rowHasChanged (fn[a b] false)}))

(defn list-view-source [v]
  (let [res #js[]]
    (doseq [item v]
      (.push res (r/atom item)))
    (r/atom (js->clj res))))

(def rows (list-view-source (clj->js (range 10))))

(def row-comp (r/reactify-component (fn[props]
                                      (let [row (props :row)]
                                        (print props)
                                        [ui/touchable-highlight {:style {:border-top-width 1 :border-color "#000"} :on-press #(print "list" (str @row))}
                                         [ui/text @row]]))))
(defn render-row [row]
  (print row)
  (r/as-element [ui/view
                 [ui/text
                  ;;"wiefowjeo"
                  @row
                  ]
                 ]))

(defn test-scene [{style            :style
                   github-button-fn :github-button-fn
                   changelog-button-fn :changelog-button-fn}]
  [ui/scroll-tab-view  {:style (get style :view)
                        :automaticallyAdjustContentInsets false}
   ;;[ui/scroll {:style (get style :view)}
   [ui/view {:tab-label "Tab1"}
    [ui/text {:style (get style :title)} "test scence 2"]
    [ui/text {:style (get style :author)} "somebody" ]

    [test-comp style]

    [ui/gifted-list-view {
                          ;;:row-view (fn[row] (js/React.createElement row-comp #js{:row row}))
                          :row-view render-row
                          :on-fetch ;;(fn [page, cb, opts ] (print page))
                          (fn [page, callback, options]
                            (js/setTimeout
                             #(callback (clj->js @rows))
                             1000)
                            )
                          :first-load true
                          }
        ]

    #_[ui/list-view {:dataSource (.cloneWithRows ds (clj->js @rows))
                   :render-row render-row
                   ;;:render-row (fn[row] (js/React.createElement row-comp #js{:row row}))
                   :style {:left 0 :right 0 :height 250 :border-width 1 :border-color "#000"}}]

    (github-button-fn github-link)
    (changelog-button-fn changelog-link)]
   ;;]

   [ui/view {:tab-label "Tab2"}
    [ui/scroll {:style (get style :view)}
     [ui/text {:style (get style :author)} "somebody2" ]
     [ui/text "Tab2"]
     [test-comp style]
     [test-comp style]
     [test-comp style]
     [test-comp style]
     ]
    ]
   ]
  )
