# firealarm

A middleware to alert you if your Noir app stacktraces.

## Why???

Very much a personal itch, but others may find it useful too.

Clojure webapps don't seem to 500 very often, probably due to immutable data, managing state sanely, compiliation, and functional programming. Still, I sleep better knowing that I'd find out about any explosions early and in detail, via email and possibly SMS.

Email is good, but I don't like giving out my GMail credentials, and I have a web host which allows me to send some low-volume email, so I needed a way to POST the message as a web form, and have it relay that to me.

I'd like to add Jabber and SMS capability too in the future, and proper SMTP email as well.

In dev mode, I like to have a text file with stacktraces; I keep it  open in emacs and auto-revert-mode it. JVM stacktraces are so long, it's helpful to have just the LAST error message and not a huge log to scroll through to find the most recennt one.


## Usage

In Lein:
```clojure
["firealarm" "0.1.0-SNAPSHOT"]
```

Set up your email/settings in ENV variables (so that it'll work on Heroku, etc).


FIREALARM_SITENAME

If you're using the POST method:

FIREALARM_POST_URL
FIREALARM_POST_TOKEN

etc


In the server.clj of your noir project:

```clojure
(:require firealarm.core :as firealarm)

```


(soon, TODO) Compose together which alert mechanisms you want for which mode (:dev or :prod).

In your server.clj, add the middleware.

```clojure
(server/add-middleware (firealarm/exception-wrapper mode))
```

## Status

Work in progress, this is very rough early alpha. But, it works, and I'm using it already.

## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
