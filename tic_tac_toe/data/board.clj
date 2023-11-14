(ns tic-tac-toe.data.board)

(defmacro let-row-col [iter & body]
  "Generates a let block containing 'row' and 'col' variables for index. Should declare 'iter'!!!

  Usage: (let-row-col iter (body1) (body2))"
  `(let [~'row (quot ~iter 3)
         ~'col (rem ~iter 3)]
     ~@body))

(defn make-board
  ([]
   [['_ '_ '_ ]
    ['_ '_ '_]
    ['_ '_ '_]])
  ([cells]
   (loop [i 0
          current-row []
          board []]
     (cond  (= (count current-row) 3) (recur i [] (conj board current-row))
            (>= i (count cells)) board
            :else (recur (inc i) (conj current-row (cells i)) board)))))

(defn size [board]
  (* (count board) (count board)))

(defn- validate-index [num low up]
  (if (or (< num low) (> num up))
    (throw (IllegalArgumentException. "Inappropriate cell index!"))))

(defmacro defn-validated [func-name lower upper args & body]
  "creates a function definition, while also adding a check for index validation.
  CREATED FUNCTION SHOULD USE PARAMETER 'index'"
  `(defn ~func-name ~args
     ~(list 'validate-index 'index lower upper)
     ~@body))

(defn-validated get-cell 0 8 [board index]
                (let-row-col index
                             (nth (board row) col)))

(defn get-cells [board first second third]
  [(get-cell board first)
   (get-cell board second)
   (get-cell board third)])

(defn-validated get-row 0 2 [board index]
  (let [row-ind-start (* 3 index)]
    (get-cells board
               row-ind-start
               (inc row-ind-start)
               (+ 2 row-ind-start))))

(defn-validated get-column 0 2 [board index]
  (get-cells board
             index
             (+ 3 index)
             (+ 6 index)))

(defn-validated is-cell-empty? 0 8 [board index]
  (= (get-cell board index)
     '_))

(defn- set-cell [board index mark]
                (if (not (is-cell-empty? board index))
                  (throw (RuntimeException. "Cannot mark, the cell is already used!"))
                  (let-row-col index (assoc board row
                                            (assoc (board row) col mark)))))
(def num-to-index-map {"7" 0
                       "8" 1
                       "9" 2
                       "4" 3
                       "5" 4
                       "6" 5
                       "1" 6
                       "2" 7
                       "3" 8})

(defn set-cell-from-numpad [board num mark]
  (set-cell board (num-to-index-map num) mark))

(defn print-board [board]
  (doseq [row board]
    (doseq [cell row]
      (print (str cell " ")))
    (println)))