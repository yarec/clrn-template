(ns test7.shared.ui
  (:require [reagent.core :as r]))

(set! js/React (js/require "react-native"))
(set! js/ScrollableTabView (js/require "react-native-scrollable-tab-view"))
(set! js/GiftedListView (js/require "react-native-gifted-listview"))

(set! js/MapView (js/require "react-native-gifted-listview"))

(set! cks-mgr (js/require "react-native-cookies"))
;;(set! js/Refresher (js/require "react-native-refresher"))

(def app-registry
  (.-AppRegistry js/React))

;; Basic views

(def view         (r/adapt-react-class (.-View js/React)))
(def map-view     (r/adapt-react-class (.-MapView js/React)))
(def scroll       (r/adapt-react-class (.-ScrollView js/React)))
(def image        (r/adapt-react-class (.-Image js/React)))
(def text         (r/adapt-react-class (.-Text js/React)))
(def input        (r/adapt-react-class (.-TextInput js/React)))

(def touchable    (r/adapt-react-class (.-TouchableWithoutFeedback js/React)))
(def progress-bar (r/adapt-react-class (.-ProgressBarAndroid js/React)))

(def linking (.-Linking js/React))

;; ---- chendesheng/ReagentNativeDemo ------------------------------------
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight js/React)))
(def list-view           (r/adapt-react-class (.-ListView js/React)))
(def switch              (r/adapt-react-class (.-Switch   js/React)))

(def scroll-tab-view   (r/adapt-react-class  js/ScrollableTabView))

(def gifted-list-view   (r/adapt-react-class  js/GiftedListView))

;;(def refresh-list-view (r/adapt-react-class (.-RefresherListView js/Refresher)))
;;(def loading-bar-indicator (r/adapt-react-class (.-LoadingBarIndicator js/Refresher)))


(def mmap-view (r/adapt-react-class js/MapView))

(defn hi-btn [name callback]
    [touchable-highlight {:style {:border-width 1 :border-color "#110"
                                    :margin 10
                                    :padding 10
                                    :background-color "#98f"}
                            :on-press callback}
    [text name]]
    )
