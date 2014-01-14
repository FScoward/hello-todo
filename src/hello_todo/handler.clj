(ns hello-todo.handler
  (:use compojure.core
        hiccup.core
        hiccup.form
        hiccup.page)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

; TODO
(defrecord Todo [no todo])

; TODOリストを溜め込む
(def todolist (ref ()))
(defn add-todolist [item]
  (dosync (alter todolist conj item)))

; カウント用関数
(def counter (ref 0))
(defn next-counter [] (dosync (alter counter inc)))

; todolist表示
(defn display-todolist []
  (map #(str "No: " (:no %) "   " "Item: " (:todo %) "</br>")(deref todolist)))


(defroutes app-routes
  (GET "/" [& {item :todo}]
       (html5 [:body
               (form-to [:GET "/"]
                        (label "todo" "todo")
                        (text-field "todo")
                        [:br]
                        (submit-button "submit")
                        [:br]
                        (if-not (nil? item)
                          (do
                            (add-todolist (Todo. (next-counter) item))
                            (display-todolist))
                          "no item"
                          ))]))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
