# firealarm

A very simple middleware to alert you if your Noir app stacktraces.

## Why???

Very much a personal itch, but others may find it useful too.

Clojure webapps don't seem to 500 very often, probably due to immutable data, managing state sanely, and functional programming. Still, it's nice to find out about any explosions early and in detail, via email and possibly SMS.

Since I have a web host which allows me to send some low-volume email, I could make do just POSTing the message as a web form to my web host, and have it relay that to me.

In dev mode, I like to have a text file with stacktraces; I keep it  open in emacs and auto-revert-mode it. JVM stacktraces are long, so it's helpful to have just the LAST error message and not a huge log to scroll through to find the most recennt one.

Rather than add email, SMS, or Jabber to this library, it's easy enough to do in a function that takes a body and sends it along.

## Usage

In Lein:
```clojure
["firealarm" "0.1.1"]
```

Set up your email/settings in ENV variables (so that it'll work on Heroku, etc).


FIREALARM_SITENAME=yoursite.com

If you're using the POST method:

FIREALARM_POST_URL
FIREALARM_POST_TOKEN

etc


In the server.clj of your noir project:

```clojure
(:require firealarm.core :as firealarm)

```


(soon, TODO) Compose together which alert mechanisms you want for which mode (:dev or :prod).

In your server.clj, inside your -main function, add the middleware immediately before the (server/start ...) line:

```clojure
(server/add-middleware (firealarm/exception-wrapper mode))
```

## Status

Work in progress, this is very rough early alpha. But, it works, and I'm using it already. See TO-DO.org file for more.


## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
