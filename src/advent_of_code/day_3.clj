(ns advent-of-code.day-3
  (:require [clojure.string :as str]))

(def rows 21)
(def columns 21)

(defrecord Grid [board position])

(def board
  (vec (repeat rows (vec (repeat columns :.)))))

(defn set-point
  [board row column]
  (let [val (get-in board [row column])]
    (if (= val :.)
      (assoc-in board [row column] :-)
      (assoc-in board [row column] :X))))

(def zero-grid
  (let [new-board (set-point board 10 10)]
    (->Grid new-board {:row 10 :column 10})))

(defn move-up
  [{:keys [board position]} distance]
  (let [row (:row position)
        column (:column position)
        top-row (+ row distance)
        new-board (loop [current-board board
                         current-row row]
                    (if (> current-row top-row)
                      current-board
                      (recur (set-point current-board current-row column)
                             (inc current-row))))]
    (->Grid new-board {:row top-row :column column})))

(defn move-down
  [{:keys [board position]} distance]
  (let [row (:row position)
        column (:column position)
        bottom-row (- row distance)
        new-board (loop [current-board board
                         current-row row]
                    (if (< current-row bottom-row)
                      current-board
                      (recur (set-point current-board current-row column)
                             (dec current-row))))]
    (->Grid new-board {:row bottom-row :column column})))

(defn move-right
  [{:keys [board position]} distance]
  (let [row (:row position)
        column (:column position)
        rightmost-column (+ column distance)
        new-board (loop [current-board board
                         current-column column]
                    (if (> current-column rightmost-column)
                      current-board
                      (recur (set-point current-board row current-column)
                             (inc current-column))))]
    (->Grid new-board {:row row :column rightmost-column})))

(defn move-left
  [{:keys [board position]} distance]
  (let [row (:row position)
        column (:column position)
        leftmost-column (- column distance)
        new-board (loop [current-board board
                         current-column column]
                    (if (< current-column leftmost-column)
                      current-board
                      (recur (set-point current-board row current-column)
                             (dec current-column))))]
    (->Grid new-board {:row row :column leftmost-column})))

(defn get-direction [char]
  (case char
    \R move-right
    \D move-down
    \L move-left
    \U move-up
    move-up))

(defn ipt->path
  [str-ipt]
  (let [dirs (str/split str-ipt #",")]
    (map #(vector (-> % first get-direction) (-> % (subs 1) Integer/parseInt)) dirs)))

(def paths
  (let [str-paths (->
                   "resources/inputs/day_3.txt"
                   slurp
                   (str/split #"\n"))]
    (map ipt->path str-paths)))

;; (def path
;;   (let [paths (->
;;                "resources/inputs/day_3.txt"
;;                slurp
;;                (str/split #"\n")
;;                (str/split #","))]
;;     (map #(vector (-> % first get-direction) (-> % (subs 1) Integer/parseInt)) paths)))

(def final-board
  (let [int-board (reduce (fn [grid [move distance]]
                            (move grid distance))
                          zero-grid
                          (first paths))
        new-board (->Grid (:board int-board) {:row 0 :column 0})]
    (reduce (fn [grid [move distance]]
                            (move grid distance))
                          zero-grid
                          (nth paths 1))))
