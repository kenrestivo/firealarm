# firealarm

A middleware  to alert you if your Noir app stacktraces.

## Usage

Set up your email/settings in ENV variables (so that it'll work on Heroku, etc).

Compose together which alert mechanisms you want for which mode (:dev or :prod).

In your server.clj, add middleware.

```clojure
(server/add-middleware (exception-wrapper mode))
```

## Warnings

Work in progress, this is very rough.

## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
