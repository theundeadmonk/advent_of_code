(ns advent-of-code.day-2
  (:require [advent-of-code.day-1 :as day-1]))

(defn actual-fuel-required
  "The amount of fuel required to launch a module. This includes the launch cost of fuel"
  [mass]
  (loop [current-mass mass
         total-fuel-required 0]
    (let [fuel-required (day-1/fuel-required current-mass)]
      (if (pos? fuel-required)
        (recur fuel-required (+ total-fuel-required fuel-required))
        total-fuel-required))))

(def fuel-requirements
  (reduce + (map actual-fuel-required day-1/module-masses)))
