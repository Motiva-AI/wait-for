# wait-for

[![CircleCI](https://circleci.com/gh/Motiva-AI/wait-for.svg?style=svg)](https://circleci.com/gh/Motiva-AI/wait-for)

A simple but insanely useful little macro for testing concurrent systems.

## Usage

```
user> (require '[wait-for.core :refer [wait-for]])
=> nil
user> (doc wait-for)
-------------------------
wait-for.core/wait-for
([predicate & {:as opts, :keys [interval timeout timeout-fn], :or {interval 3, timeout 120}}])
Macro
  Invoke predicate every interval (default 3) seconds until it returns true,
  or timeout (default 120) seconds have elapsed. E.g.:

      (wait-for #(< (rand) 0.2) :interval 1 :timeout 10)

  Optionally takes a `:timeout-fn` key the value of which will be
  called when the timeout occurs. The default behaviour is to throw
  an `(ex-info ...)` with a useful message.

  Setting `timeout-fn` explicitly to `false` cause the results of the
  last call to `predicate` to be returned regardless. This allows the
  following form to work as expected in tests:

      (is (wait-for #(< (rand) 0.2) :interval 1 :timeout 10))
=> nil
user>
```

## Latest Version

The latest release version of wait-for is hosted on [Clojars](https://clojars.org):

[![Current Version](https://clojars.org/motiva/wait-for/latest-version.svg)](https://clojars.org/motiva/wait-for)

## License

The MIT License (MIT)

Copyright © 2018 Motiva Inc

