# firealarm

A very simple middleware to alert you if your Ring-based (Compojure, etc) app stacktraces.

## Why???

Very much a personal itch, but others may find it useful too.

Clojure webapps don't seem to 500 very often, probably due to immutable data, managing state sanely, and functional programming. Still, it's nice to find out about any explosions early and in detail, via email and possibly SMS.

Since I have a web host which allows me to send some low-volume email, I could make do just POSTing the message as a web form to my web host, and have it relay that to me.

I could also use Simple Email Service or just SMTP too.

In dev mode, I like to have a text file with stacktraces; I keep it  open in emacs and auto-revert-mode it. JVM stacktraces are long, so it's helpful to have just the LAST error message and not a huge log to scroll through to find the most recennt one.

Rather than add email, SMS, or Jabber to this library, it's easy enough to do in a function that takes a body and sends it along. Send that function to firealarm/exception-wrapper and you are set.

## Usage

In Lein:
```clojure
[firealarm "0.1.2"]
```

In the server.clj of your web project:

```clojure
(require '[firealarm.core :as firealarm])

```

In server.clj, create the function to do the reporting, as such

```clojure
(def reporter (firealarm/post-reporter "http://somewhere.com/report" "name of site" "token"))
```

You can also compose them together, like so:


```clojure
(def reporter (juxt
     (firealarm/post-reporter "http://somewhere.com/report" "name of site" "token")
    (firealarm/file-reporter "/tmp/web-error.log")))
```

If you're using a Ring-based stack, you can generate the handler and apply it like so:

```clojure
(def wrap-exception (firealarm/exception-wrapper reporter))
(def app (wrap-exception handler))
```



## Status

Work in progress, this is very rough early alpha. But, it works, and I'm using it already. See TO-DO.org file for more.


## License

Copyright © 2012 ken restivo

Distributed under the Eclipse Public License, the same as Clojure.
