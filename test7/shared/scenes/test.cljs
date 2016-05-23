(ns test7.shared.scenes.test
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
    [clojure.walk :refer [keywordize-keys]]
    [ajax.core :refer [GET POST ajax-request json-request-format json-response-format]]
    [test7.shared.components.gitfeed :as gitfeed]
    [test7.shared.ui :as ui]))

(def github-link
  "http://github.com/test7-react-native")

(def changelog-link
  "https://github.com/test7-react-native/blob/master/CHANGELOG.md")

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



(defn test-scene [{style            :style
                   github-button-fn :github-button-fn
                   changelog-button-fn :changelog-button-fn}]
  [ui/scroll-tab-view  {:style (get style :view) }

   [ui/view {:tab-label "Tab3"
             ;; :style {:height 600}
         }
    [gitfeed/listview style]
    ]

   [ui/view {:tab-label "Tab1"}
    [ui/text {:style (get style :title)} "test scence 2"]
    [ui/text {:style (get style :author)} "somebody" ]
    [test-comp style]
    (github-button-fn github-link)
    (changelog-button-fn changelog-link)]

   [ui/view {:tab-label "Tab2"}
    [ui/scroll {:style (get style :view)}
     [ui/text {:style (get style :author)} "somebody2" ]
     [ui/text "Tab2"]
     [test-comp style]
     ]
    ]
   ]
  )
