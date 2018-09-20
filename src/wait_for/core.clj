(ns wait-for.core
  (:require clojure.pprint))

;; See https://gist.github.com/mrowe/4689005
(defn wait-for*
  [predicate {:keys [interval timeout timeout-fn]
              :or   {interval 3
                     timeout  120}}]

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

  Optionally takes a `:timeout-fn` key the value of which will be
  called when the timeout occurs. The default behaviour is to throw
  an `(ex-info ...)` with a useful message.

  Setting `timeout-fn` explicitly to `false` cause the results of the
  last call to `predicate` to be returned regardless. This allows the
  following form to work as expected in tests:

      (is (wait-for #(< (rand) 0.2) :interval 1 :timeout 10))"
  [predicate & {:as   opts
                :keys [interval timeout timeout-fn]
                :or   {interval 3
                       timeout  120}}]

  (cond (= timeout-fn false)
        `(let [timeout-fn# (fn [] (~predicate))]
           (wait-for* ~predicate (assoc ~opts :timeout-fn timeout-fn#)))

        (= timeout-fn nil)
        `(let [timeout-fn#
               (fn []
                 (throw
                   (ex-info (format "Timed out waiting %d seconds for %s to become true"
                                    ~timeout (-> (quote ~predicate)
                                                 clojure.pprint/pprint
                                                 with-out-str))
                            {:predicate (quote ~predicate)
                             :timeout   ~timeout})))]
           (wait-for* ~predicate
                      (assoc ~opts :timeout-fn timeout-fn#)))

        :else
        `(wait-for* ~predicate ~opts)))
