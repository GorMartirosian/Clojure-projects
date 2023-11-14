(ns tic-tac-toe.io.input)

(defn user-input
  ([message]
   (println message)
   (read-line))
  ([]
   (read-line)))
