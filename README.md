# Benernet
Benternet is a simple home automation server for controlling various devices in
a home that is configured in the exact same way as mine.

TODO: Add wiring diagrams/setup instructions so that when it inevitably goes
haywire it can be rebuilt from scratch.

## Server
The server runs on a Raspberry Pi with GPIO pins connected to a board with:

  * An IR transmitter for communicating with IR devices
  * A 433Mhz transmitter for controlling some Etekcity outlets

## Clients
  * **Web**: The server has a web interface that can be accessed from the local
    network; the UI is possibly one of the finest ever made
  * **Android**: As if the web interface wasn't beautiful enough, how about an
    Android app that does pretty much the same thing but in a way that is
    somehow less attractive? Is this how I get rich?
