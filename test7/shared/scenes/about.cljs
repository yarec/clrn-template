(ns test7.shared.scenes.about
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
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

(defn about-scene [{style            :style
                    github-button-fn :github-button-fn
                    changelog-button-fn :changelog-button-fn}]
  [ui/scroll {:style (get style :view)}
   [ui/view
    [ui/text {:style (get style :title)} "test7"]
    [ui/text "some text"]
    [ui/text {:style (get style :author)} "somebody" ]

    [test-comp style]

    (github-button-fn github-link)
    (changelog-button-fn changelog-link)]])
