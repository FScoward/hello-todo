(ns hello-todo.handler
  (:use compojure.core
        hiccup.core
        hiccup.form
        hiccup.page)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
