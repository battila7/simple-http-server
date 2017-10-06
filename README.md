# Simple HTTP Server

An example of a simple **no-dependency** Java HTTP server written from scratch. It features a clean architecture and the basic feaures of an HTTP server/framework.

The server utilizes a thread pool, each connection is processed on a separate thread.

If you preferred the previous, even simpler version, head over here: https://github.com/battila7/simple-http-server/tree/0f4efb8af16fbb36b30f1d4549b08a003f814326

## What it is

  * An HTTP server/framework that has no dependencies.
  * A fun project.

## What it isn't

  * A production-ready HTTP server/framework.
  * HTTP compliant. 

## How to run it?

The repository has two modules, of which the `example` module contains a simple application that responds with `Hello, World!` to all `GET` requests.

~~~~bash
mvn exec:java -pl example -Dexec.mainClass="simple.cli.Main" [-Dport=9090]
~~~~

By default, the application will listen on port `9090`.

## Why did you write this?

I did not intend to, but I caught myself thinking about it, so I eventually gave in. Fortunately, it was fun.
