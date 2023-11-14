(ns tic-tac-toe.win-check)

(refer 'tic-tac-toe.io.input)
(refer 'tic-tac-toe.data.player)
(refer 'tic-tac-toe.data.board)

(defn- check-cells-for-win [cell1 cell2 cell3]
  (if (= cell1 cell2 cell3)
    (cond (= cell1 (:mark player1)) :p1-win
          (= cell1 (:mark player2)) :p2-win
          :else false)
    false))


(defn- check-seq [s]
  (check-cells-for-win (first s)
                       (second s)
                       (nth s 2)))

(defn- check-win-horizontal [board]
      (or (check-seq (get-row board 0))
          (check-seq (get-row board 1))
          (check-seq (get-row board 2))))


(defn- check-win-vertical [board]
    (or (check-seq (get-column board 0))
        (check-seq (get-column board 1))
        (check-seq (get-column board 2))))

(defn- check-win-diagonal [board]
  (or (check-seq (get-cells board 0 4 8))
      (check-seq (get-cells board 2 4 6))))

(defn check-win [board]
  "Returns :p1-win in the case of first players victory, :p2-win in case of second and false if neither"
  (or (check-win-horizontal board) (check-win-vertical board) (check-win-diagonal board)))