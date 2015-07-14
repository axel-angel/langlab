(ns  langlab.core.multi-stemmers
  "Module contains stemming algorithms returning multiple results."
  (:require [ clojure.java.io :as io])
  (:import
      [java.lang CharSequence]
      [morfologik.stemming WordData]
      [morfologik.stemming PolishStemmer]
      [hunspell_stemmer Dictionary Stemmer]))

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

(defn ^:private ^Dictionary read-hunspell-dict
  [aff-fname dic-fname]
  (with-open
      [
         as (io/input-stream aff-fname)
         ds (io/input-stream dic-fname)
      ]
    (Dictionary. as ds)))

(defn make-multi-stem-hunspell [aff-fname dic-fname ]
  (let [
        as (io/input-stream aff-fname)
        ds (io/input-stream dic-fname)
        d ^Dictionary (Dictionary. as ds)
    ]
    (fn [^String word]
      (let [
            stemmer (Stemmer. d)
            stem-list (.stem stemmer word)
            ]
        (if (= 0 (.size stem-list))
          word
          (map #(.toString %) stem-list))))))
