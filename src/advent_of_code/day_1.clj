(ns advent-of-code.day-1
  (:require [clojure.string :as str]))

(def string-modules
  (->
   "resources/inputs/day_1.txt"
   slurp
   (str/split #"\n")))

(def module-masses
  (map #(Integer/parseInt %) string-modules))


(defn fuel-required
  "The amount of fuel required to launch a module"
  [mass]
  (->
   mass
   (/ 3.0)
   (Math/floor)
   (int)
   (- 2)))

(def fuel-requirements
  (reduce + (map fuel-required module-masses)))
