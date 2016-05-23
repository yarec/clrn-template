(ns test7.shared.scenes.test
  (:require
    [re-frame.core :refer [subscribe dispatch dispatch-sync]]
    [reagent.core :as r]
    [clojure.walk :refer [keywordize-keys]]
    [ajax.core :refer [GET POST ajax-request json-request-format json-response-format]]
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
(defn real-action [event]
  (let [type (:type event)
        action (:action (:payload event))
        title (:title (:issue (:payload event)))
        ]

    (case type
      "ForkEvent" "Forked"
      "WatchEvent" "Started"
      "PushEvent" "Pushed"
      "IssueCommentEvent" (str action " comment")
      "PullRequestEvent" (str action " pull request")
      "MemberEvent" action
      "IssuesEvent" (str action " issue [" title "] ")
      "PullRequestReviewCommentEvent" (str action " pull request comment on")
      "DeleteEvent" "Delete"
      "CreateEvent" "Create"
      )
    )

  )


(defn time-diff [curr, prev]
  (let [ms-per-min (* 60 1000)
        ms-per-hour (* ms-per-min 60)
        ms-per-day (* ms-per-hour 24)
        ms-per-month (* ms-per-day 30)
        ms-per-year (* ms-per-month 365)
        delta (- curr prev)
        delta (.max js/Math 0 delta)
        ]
    (cond
      (< delta ms-per-min) (str (.round js/Math (/ delta 1000)) " seconds ago")
      (< delta ms-per-hour) (str (.round js/Math (/ delta ms-per-min)) " mins ago")
      (< delta ms-per-day) (str (.round js/Math (/ delta ms-per-hour)) " hours ago")
      (< delta ms-per-month) (str "approximately " (.round js/Math (/ delta ms-per-day)) " days ago")
      (< delta ms-per-year) (str "approximately " (.round js/Math (/ delta ms-per-month)) " month ago")
      :else  (str "approximately " (.round js/Math (/ delta ms-per-year)) " years ago")
      )
    )
  )
(defn times-ago [event]
  (let [create-time (:created_at event)
        cur-date (js/Date.)
        gh-date (js/Date. create-time)]
    (time-diff (.getTime cur-date) (.getTime gh-date))
    )
  )

(def styl (r/atom {}))

(defn render-row [row]
  (let [row (js->clj row :keywordize-keys true)
        type (:type row)
        times-ago-str (times-ago row)

        actor (:actor row)
        avatar-url (:avatar_url actor)
        login (:login actor)

        action (real-action row)

        repo (:repo row)
        repo-name (:name repo)
        repo-url (:url repo)

        row-text (str action " " repo-name)
        style (get @styl :feed)]

    (r/as-element [ui/view {:style (get style :view-col)}
                   [ui/view
                    [ui/view {:style (get style :view-row)}
                     [ui/image {:source {:uri avatar-url}
                                :style {:width 60 :height 60}}]
                     [ui/view
                      [ui/text {:style {:padding-left 8
                                        :padding-top 8}} login]
                      [ui/text {:style (get style :create-at)} times-ago-str]
                      ]
                     ]
                    ]
                   [ui/text {:style (get style :action-text)}
                    (str action " ")
                    [ui/text {:style (get style :link-text)}
                     repo-name]]
                   ])))

(defn get-events-header [url]
  (ajax-request {:uri url
                 :method :get
                 :handler (fn [[ok resp]]
                            (print ok)
                            (print resp)
                            )
                 :format (json-request-format)
                 :response-format {:read (fn [r]
                                           (.log js/console r)
                                           )
                                   :description "raw"}
                 }
                )
  )

(defn get-events [page callback options]
  (let [url "https://api.github.com/users/yarec/received_events"
        ;; url "http://interface.ihuipao.cn/race/nav/raceid/16"
        ]
    (print url)
    (GET url {:handler (fn [resp]
                         (print resp)
                         (callback (clj->js resp))
                         )
              })
    )
  )

(defn render-row1 [row]
  (r/as-element [ui/view 
                 [ui/text "hello"]
                 ]
                ))
(defn test-login []
  (let [url "http://ihuipao.cn/user/login"
        params (js-obj "method" "POST"

                       "redirect"
                       "error"
                       ;;"follow"
                       ;;"manual"

                       "body" "account=rentie@ihuipao.cn&password=123456"
                       "headers" (js-obj "content-type" "application/x-www-form-urlencoded")
                )
                ]
    (.then (.fetch js/window url params)
           (fn [resp]
             (let [data (keywordize-keys resp)
                   headers (js->clj (.-headers resp) :keywordize-keys true)
                   cookies (aget (.-map headers) "set-cookie")
                   ]

               (.log js/console headers)
               (.log js/console cookies)
               ;;(print (js->clj cookies :keywordize-keys true))
               ;;(.log js/console resp)
               ;;(print (keywordize-keys (js->clj cookies :keywordize-keys true)))
               ;;(.log js/console (get dd "type"))
               ;;(.log js/console resp)
               ;;(print (.json resp))
               ;;(.log js/console (get data "headers") )
               )))
           )
    )

(defn test-cookies []
  (let [cks-mgr ui/cks-mgr]
    (.getAll cks-mgr (fn [err, res]
                       (print err)
                       (print res)
                       ))
    )
  )
(defn test-get []
  (let [url "https://api.github.com/users/yarec/received_events"
        url "http://interface.ihuipao.cn/race/nav/raceid/16"
        url "http://interface.ihuipao.cn/index/userinfo"
        ]
    (print url)
    (.then (.fetch js/window url) (fn [resp]
                                    (.log js/console resp )
                                    ;;(.log js/console (.json resp) )
                                    ))
    #_(ajax-request {:uri url
                   :method :get
                   :handler (fn [[ok resp]]
                              (print ok)
                              (print resp)
                              )
                   :format (json-request-format)
                   :response-format {:read (fn [r]
                                             (.log js/console r)
                                             )
                                     :description "raw"}
                   }
                  )
    #_(GET url {:handler (fn [resp]
                         (print 11)
                         (print resp)
                         )
              }
         )
    )
  )
(defn listview []
  [ui/view
   [ui/text {:on-press #(test-get)}
    "test get"]
   [ui/touchable-highlight {:style {:border-width 1 :border-color "#110"
                                    :margin 10
                                    :padding 10
                                    :background-color "#98f"}
                            :on-press #(test-cookies)}
    [ui/text "show Cookies"]]
   [ui/touchable-highlight {:style {:border-width 1 :border-color "#110"
                                    :margin 10
                                    :padding 10
                                    :background-color "#98f"}
                            :on-press #(test-login)}
    [ui/text "test login"]]

   [ui/text {:on-press #(print "hhhh")}
    "hello"]
   [ui/gifted-list-view
    {:row-view render-row
     :on-fetch get-events

     ;; :row-view render-row1
     ;; :on-fetch (fn [page, callback, options] (js/setTimeout #(callback (clj->js @rows)) 1000) )

     :first-load true
     :enableEmptySections true
     :with-section true}]
   ]
  )

(defn test-scene [{style            :style
                   github-button-fn :github-button-fn
                   changelog-button-fn :changelog-button-fn}]
  (reset! styl style)
  [ui/scroll-tab-view  {:style (get style :view) }

   [ui/view {:tab-label "Tab3"
             :style {:height 600}}
    [listview]
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
