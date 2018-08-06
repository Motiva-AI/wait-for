# wait-for

[![CircleCI](https://circleci.com/gh/Motiva-AI/wait-for.svg?style=svg)](https://circleci.com/gh/Motiva-AI/wait-for)

A simple but insanely useful little function for testing concurrent systems.

## Usage

```
user> (require '[wait-for.core :refer [wait-for]])
=> nil
user> (doc wait-for)
-------------------------
wait-for.core/wait-for
([predicate & {:keys [interval timeout timeout-fn], :or {interval 3, timeout 120, timeout-fn (fn* [] (throw (ex-info "Timed out waiting for condition" {:predicate predicate, :timeout timeout})))}}])
  Invoke predicate every interval (default 3) seconds until it returns true,
  or timeout (default 120) seconds have elapsed. E.g.:

      (wait-for #(< (rand) 0.2) :interval 1 :timeout 10)

  Returns nil if the timeout elapses before the predicate becomes true,
  otherwise the value of the predicate on its last evaluation.
=> nil
user>
```

## Latest Version

The latest release version of sqs-utils is hosted on [Clojars](https://clojars.org):

[![Current Version](https://clojars.org/motiva/wait-for/latest-version.svg)](https://clojars.org/motiva/wait-for)

## License

The MIT License (MIT)

Copyright © 2018 Motiva Inc

