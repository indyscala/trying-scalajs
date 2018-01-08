# Trying Scala.js Demo

## Prerequisites

* Install [sbt](http://www.scala-sbt.org/)
* Install [node.js](https://nodejs.org/)
* Install `source-map-support` via npm for source maps: `npm install source-map-support`

## Running the "Have I Been Pwned?" example

    git clone https://github.com/indyscala/trying-scalajs
    cd trying-scalajs
    sbt ~pwnedJS/fastOptJS

Then open [pwned-dev.html](pwned-dev.html) in a browser as well as the browser developer tools.  For example, with:

    open ./pwned-dev.html

in a separate terminal from sbt.
