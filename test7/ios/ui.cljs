(ns test7.ios.ui
  (:require [reagent.core :as r]))

(set! js/React (js/require "react-native"))
(set! js/IOSButton (js/require "react-native-button"))

(def navigator    (r/adapt-react-class (.-NavigatorIOS js/React)))
(def tab-bar      (r/adapt-react-class (.-TabBarIOS js/React)))
(def tab-bar-item (r/adapt-react-class (.-TabBarIOS.Item js/React)))
(def button       (r/adapt-react-class js/IOSButton))

;; ---- chendesheng/ReagentNativeDemo ------------------------------------
(def switch              (r/adapt-react-class (.-SwitchIOS js/React)))
(def slider              (r/adapt-react-class (.-SliderIOS js/React)))


(defn show-dialog [{text     :text
                    callback :callback}]
  (.prompt (.-AlertIOS js/React) text nil callback))

(defn show-action-sheet [options callback]
  (.showActionSheetWithOptions (.-ActionSheetIOS js/React)
                               (clj->js options)
                               callback))
