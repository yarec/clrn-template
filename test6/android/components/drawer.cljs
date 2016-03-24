(ns test6.android.components.drawer
  (:require
    [reagent.core :as r]
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [test6.shared.ui :as ui]
    [test6.android.ui :as android-ui]))

(def logo-img
  (js/require "./images/cljs.png"))

(defn drawer-component [navigator drawer]
  (let [tab    (subscribe [:get-shared-tab])
        drawer (subscribe [:get-android-drawer])]
    [android-ui/drawer
     [android-ui/drawer-header {:image (r/as-element [ui/image {:source logo-img}])}]
     [android-ui/drawer-section {:items [{:icon    "cloud-queue"
                                          :active  (= @tab "main")
                                          :value   "Weather"
                                          :onPress (fn [_]
                                                     (dispatch [:set-shared-tab "main"])
                                                     (.closeDrawer @drawer))}
                                         {:icon    "face"
                                          :active  (= @tab "about")
                                          :value   "About"
                                          :onPress (fn [_]
                                                     (dispatch [:set-shared-tab "about"])
                                                     (.closeDrawer @drawer))}]}]]))
