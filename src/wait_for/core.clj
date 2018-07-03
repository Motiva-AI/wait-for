(ns wait-for.core)

(defn wait-for
  "Invoke predicate every interval (default 3) seconds until it returns true,
  or timeout (default 120) seconds have elapsed. E.g.:

      (wait-for #(< (rand) 0.2) :interval 1 :timeout 10)

  Returns nil if the timeout elapses before the predicate becomes true,
  otherwise the value of the predicate on its last evaluation."
  [predicate & {:keys [interval timeout timeout-fn]
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
