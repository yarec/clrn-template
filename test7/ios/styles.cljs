(ns test7.ios.styles)

(def styles
  {:app       {:position "absolute"
               :top      0
               :left     0
               :bottom   0
               :right    0}
   :statusbar {:background-color "#01579B"
               :height           26}
   :toolbar   {:position         "relative"
               :background-color "#01579B"}
   :scenes    {:main  {:view      {:align-items "stretch"}
                       :city-card {:card        {:height     105
                                                 :flex       1
                                                 :resizeMode "cover"}
                                   :view        {:padding          10
                                                 :background-color "transparent"}
                                   :title       {:font-size 22
                                                 :color     "white"}
                                   :temp        {:font-size   22
                                                 :font-weight "bold"
                                                 :color       "white"}
                                   :description {:font-weight   "bold"
                                                 :margin-bottom 5
                                                 :color         "white"}
                                   :key         {:color "white"}
                                   :value       {:font-weight "bold"
                                                 :color       "white"}}}
               :about {:view            {:padding 16
                                         :flex    1}
                       :title           {:font-weight   "bold"
                                         :margin-bottom 4}
                       :author          {:margin-top    4
                                         :margin-bottom 20}
                       :card            {:height     105
                                         :flex       1
                                         :resizeMode "cover"}}

               :test {:view            {:padding 0
                                        :padding-top 15
                                        :height 500
                                         :flex    1}
                       :title           {:font-weight   "bold"
                                         :margin-bottom 4}
                       :author          {:margin-top    4
                                         :margin-bottom 20}
                       :card            {:height     105
                                         :flex       1
                                         :resizeMode "cover"}

                      :feed {:action-text {:padding 8
                                           :color "#666666"
                                           :font-size 16
                                           :font-weight "bold"}
                             :link-text {:color "#4078c0"
                                         :font-size 15
                                         :font-weight "normal"}
                             :view-col {:flexDirection "column"
                                        :justifyContent "center"
                                        :padding 3
                                        :backgroundColor "#F6F6F6"}
                             :view-row {:flexDirection "row"
                                        :padding-left 5
                                        :justifyContent "flex-start"
                                        }
                             :create-at {:margin-left 10
                                         :margin-top 2
                                         :font-size 11
                                         :color "#BFBFBF"}
                             }}}})
