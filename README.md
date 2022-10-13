# Menza backend

This backend is responsible for handling dishes rating in 
the [Menza CTU](https://github.com/Lastaapps/menza) Android app.

## About
The BE supports dish rating and dish sold out.
It resets every midnight European time.
It is written in the way, that any menza can be supported,
not only these from CTU.
Server does not track users or collect data in any way
(except maybe some IP logs maybe, I don't even know).

## For developers
### Technologies used
- Raspberry Pi 4B 8GB
- Kotlin, Ktor Server, Serialization
- Clean architecture
- Gradle

### Connect your own app
I host on my own server. If you want to use my backend
and integrate it in your app, please file an issue or 
get in contact in another way. I'll generate you 
a new api key, and you are good to go!

### Doc
Documentation can be found in the [DOC.md](DOC.md) file.

## License

Menza backend is licensed under the `GNU GPL v3.0` license.
