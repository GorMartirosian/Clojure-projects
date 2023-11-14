(ns tic-tac-toe.data.player)

(refer 'tic-tac-toe.io.input)

(defrecord Player [name mark])

(defn- choose-mark [player-name]
  (if   (= "X" (.toUpperCase (user-input (str player-name ", choose X or O: "))))
    "X"
    "O"))

(defn- init-player [message]
  (let [name (user-input message)
        mark (choose-mark name)]
    (->Player name mark)))

(def player1 (init-player "What is your name?: "))
(def player2 (init-player "What is the other players name?: "))