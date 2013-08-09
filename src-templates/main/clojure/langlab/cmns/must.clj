(ns langlab.cmns.must
  "Module contains assertions-like system. 
   For production builds it can be disabled. It is generated by lein resource
   plugin.")

(def must-enabled {{must-enabled}})

(defmacro must 
  "C-assert-like macro.
   If `must-enabled` is set to true, it raises exceptions when `x` evaluates to
   false. If `must-enabled` is set to false it expands to empty macro."
  [ x ]
  (when must-enabled
    `(when-not ~x
       (throw (new AssertionError (str "Assert failed: " (pr-str '~x)))))))
  
(defmacro must-not
  "C-assert-like macro. Opposite of `must`."
  [ x ]
    `(not (should ~x)))