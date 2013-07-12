(ns  langlab-base.core.multi-stemmers
  "Module includes stemming algorithms returning multiple results."
  (:import 
    [ java.lang CharSequence ]
    [ morfologik.stemming WordData ]
    [ morfologik.stemming PolishStemmer ]))

(set! *warn-on-reflection* true)

(defn pl-multi-stem-morfologik
  "Returns a seq of stems for `word` generated by Polish Morfologik stemmer."
  [ ^String word ]
  (let [
        stemmer (new PolishStemmer)
        conv-to-string 
          (fn [ ^WordData w ]
            (. (. w getStem) toString))
        stems (map 
               conv-to-string
               (. stemmer lookup word))
       ]
    stems))
