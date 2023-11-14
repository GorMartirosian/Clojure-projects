(ns tic-tac-toe.core
  (:gen-class))

(refer 'clojure.repl)
(require 'tic-tac-toe.data.board)
(require 'tic-tac-toe.io.input)
(require 'tic-tac-toe.data.player)
(require 'tic-tac-toe.win-check)

(refer 'tic-tac-toe.data.board)
(refer 'tic-tac-toe.io.input)
(refer 'tic-tac-toe.data.player)
(refer 'tic-tac-toe.win-check)



(defn- announce-vic&score [winner score]
  (println (str (:name winner) " wins!!!"))
  (println "Score: ")
  (println (str (:name player1) " - " (first score)))
  (println (str (:name player2) " - " (second score) "\n")))

(defn- play-again? []
  (let [answer (user-input "Do you want to play again? (y/n) ")]
    (or (= answer "y") (= answer "Y"))))

(defn- update-score [score winner]
  (assoc score winner (inc (score winner))))


(defmacro gen-win-clause [player]
  (let [which-player (if (= player 'player1) 0 1)]
    `(let [score-aft-win# (update-score ~'score ~which-player)]
       (print-board ~'board)
       (println)
       (announce-vic&score ~player score-aft-win#)
       (if (play-again?) (recur (make-board)
                                  ~player
                                  score-aft-win#)
                         (println "\nThank you, bye.")))))

(defn- return-other-player [player]
  (if (= player player1)
    player2
    player1))

(defn game-loop []
  (loop [board (make-board)
         order-to-play player1
         score [0 0]]
    (cond (= (check-win board) :p2-win) (gen-win-clause player2)
          (= (check-win board) :p1-win) (gen-win-clause player1)
          :else (do (print-board board)
                    (let [cell-to-mark (user-input (str "Which cell do you want to mark, " (:name order-to-play) "?(use numpad): "))]
                      (recur (set-cell-from-numpad board cell-to-mark (:mark order-to-play))
                             (return-other-player order-to-play)
                             score))))))

(game-loop)