(ns ms.core)


; Constants
(def ^{:private true :const true} S 1000)
(def ^{:private true :const true} M (* S 60))
(def ^{:private true :const true} H (* M 60))
(def ^{:private true :const true} D (* H 24))
(def ^{:private true :const true} Y (* D 365.25))
(def ^{:private true :const true} REGEX_STR
  (re-pattern "(?ix)^((?:\\d+)?\\.?\\d+)?[\\ \\t]*(
                 milliseconds?
                |msecs?
                |ms
                |seconds?
                |secs?
                |s
                |minutes?
                |mins?
                |m
                |hours?
                |hrs?
                |h
                |days?
                |d
                |years?
                |yrs?
                |y)?$"))


; Helpers
(defn- plural
  [val n name]
  (if (< val (* n 1.5))
      (-> val (/ n) Math/floor bigint (str " " name))
      (-> val (/ n) Math/ceil  bigint (str " " name "s"))))

(defn- short-format
  [val n name]
  (-> val (/ n) Math/floor bigint (str name)))

(defn- parse-str
  [val]
  (let [match (re-find REGEX_STR val)
        m1    (nth match 1)
        m2    (nth match 2)
        n     (if (nil? m1) 1 (Float/parseFloat m1))
        t     (if (nil? m2) "ms" (clojure.string/lower-case m2))]
       (condp contains? t
              #{"years"        "year"        "yrs"   "yr"   "y"}  (bigint (* n Y))
              #{"days"         "day"         "d"}                 (bigint (* n D))
              #{"hours"        "hour"        "hrs"   "hr"   "h"}  (bigint (* n H))
              #{"minutes"      "minute"      "mins"  "min"  "m"}  (bigint (* n M))
              #{"seconds"      "second"      "secs"  "sec"  "s"}  (bigint (* n S))
              #{"milliseconds" "millisecond" "msecs" "msec" "ms"} (bigint n)
              nil)))

(defn- fmt-long
  [val]
  (cond
    (>= val Y) (plural val Y "year")
    (>= val D) (plural val D "day")
    (>= val H) (plural val H "hour")
    (>= val M) (plural val M "minute")
    (>= val S) (plural val S "second")
    :else      (str val " ms")))

(defn- fmt-short
  [val]
  (cond
    (>= val Y) (short-format val Y "y")
    (>= val D) (short-format val D "d")
    (>= val H) (short-format val H "h")
    (>= val M) (short-format val M "m")
    (>= val S) (short-format val S "s")
    :else      (str val "ms")))


; Public
(defn ms
  "Parse or format the given value"
  [val & {:keys [long?] :or {long? false}}]
  (if (instance? String val)
    (parse-str val)
    (if long?
      (fmt-long  val)
      (fmt-short val))))
