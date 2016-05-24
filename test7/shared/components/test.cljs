(ns test7.shared.components.test
  (:require
    [reagent.core :as r]
    [test7.shared.ui :as ui]))


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
