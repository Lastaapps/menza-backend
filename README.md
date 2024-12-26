# Menza backend

This backend is responsible for handling dishes rating in
the [Menza CTU](https://github.com/Lastaapps/menza) Android app.

This BE is meant primarily for CTU menzas, but if you want to use it
for another purposes, just host it yourself, start an issue here or contact me.

## About

The BE supports dish rating in multiple categories.
It resets every midnight European time.
It is written in a way, that any menza can be supported,
not only those from CTU.
Server does not track users or collect data in any way
(reverse proxy may collect IP addresses in logs).

## For developers

### Doc
API documentation can be found in the [DOC.md](DOC.md) file.

### Technologies used
- Kotlin, Ktor Server, Serialization
- Clean architecture (I hope)
- Gradle

### Hosting
- Raspberry Pi 4B 8GB
- Alma linux
- Docker

### Connect your own app
If you want to use backend hosted by me
and integrate it in your app, please file an issue or 
get in contact with me in another way. I'll generate you 
a new api key, and you are good to go!
Don't steal my keys, please.

## License

Menza backend is licensed under the `GNU GPL v3.0` license.
