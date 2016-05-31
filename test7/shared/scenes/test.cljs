(ns test7.shared.scenes.test
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
    [clojure.walk :refer [keywordize-keys]]
    [ajax.core :refer [GET POST ajax-request json-request-format json-response-format]]
    [test7.shared.components.gitfeed :as gitfeed]
    [test7.shared.components.demolistview :as dlv]
    [test7.shared.components.test :as test]
    [test7.shared.ui :as ui]))

(def github-link
  "http://github.com/test7-react-native")

(def changelog-link
  "https://github.com/test7-react-native/blob/master/CHANGELOG.md")


(defn test-scene [{style            :style
                   github-button-fn :github-button-fn
                   changelog-button-fn :changelog-button-fn}]
  [ui/scroll-tab-view  {:style (get style :view) }

   [ui/view {:tab-label "Tab3"
             :style {:height 600} }
     [gitfeed/listview style]
    ;; [gitfeed/test-list-view]
    ;; [dlv/demo-list-view]
    ]

   [ui/view {:tab-label "Tab1"}
    [ui/text {:style (get style :title)} "test scence 2"]
    [ui/text {:style (get style :author)} "somebody" ]
    [test/test-comp style]
    (github-button-fn github-link)
    (changelog-button-fn changelog-link)]

   [ui/view {:tab-label "Tab2"}
    [ui/scroll {:style (get style :view)}
     [ui/text {:style (get style :author)} "somebody2" ]
     [ui/text "Tab2"]
     [test/test-comp style]
     ]
    ]
   [ui/view {:tab-label "map"}
    [ui/text {:style (get style :author)} "somebody2" ]
    ;; [test/test-comp style]
    [ui/mmap-view
     {:region {:latitude 37.78825
               :longitude -122.4324
               :latitudeDelta 0.0922
               :longitudeDelta 0.0421}}

     ]
    ]
   ]
  )
