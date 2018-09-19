(ns wait-for.core
  (:require clojure.pprint))

;; See https://gist.github.com/mrowe/4689005
(defn wait-for*
  [predicate {:keys [interval timeout timeout-fn]
              :or   {interval 3
                     timeout  120
                     timeout-fn
                     #(throw (ex-info "Timed out waiting for condition"
                                      {:predicate predicate
                                       :timeout   timeout}))}}]
  (let [end-time (+ (System/currentTimeMillis) (* timeout 1000))]
    (loop []
      (if-let [result (predicate)]
        result
        (do (Thread/sleep (* interval 1000))
            (if (< (System/currentTimeMillis) end-time)
              (recur)
              (timeout-fn)))))))

(defmacro wait-for
  "Invoke predicate every interval (default 3) seconds until it returns true,
  or timeout (default 120) seconds have elapsed. E.g.:

      (wait-for #(< (rand) 0.2) :interval 1 :timeout 10)

  Returns nil if the timeout elapses before the predicate becomes true,
  otherwise the value of the predicate on its last evaluation.

  Optionally takes a `:timeout-fn` key the value of which will be
  called when the timeout occurs. The default behaviour is to throw
  an `(ex-info ...)` with a useful message."
  [predicate & {:as   opts
                :keys [interval timeout timeout-fn]
                :or   {interval 3
                       timeout  120}}]

  `(wait-for*
     ~predicate
     ~(if-not timeout-fn
        `(assoc ~opts
                :timeout-fn
                #(throw
                   (ex-info (format "Timed out waiting %d seconds for %s to become true"
                                    ~timeout (-> ~predicate
                                                 quote
                                                 clojure.pprint/pprint
                                                 with-out-str))
                            {:predicate (quote ~predicate)
                             :timeout   ~timeout})))
        ~opts)))
