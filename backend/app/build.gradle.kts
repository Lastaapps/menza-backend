plugins {
    id("ktor-server-convention")
}

application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("io.ktor.server.cio.EngineMain")
}

dependencies {
    implementation(projects.backend.base)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
