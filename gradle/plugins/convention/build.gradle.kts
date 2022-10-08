plugins {
    `kotlin-dsl`
//    id("java-gradle-plugin")
}

group = "cz.lastaapps.convention-plugins"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(libs.gradlePlugins.kotlin)
    runtimeOnly(libs.gradlePlugins.shadow)
}

gradlePlugin {
    plugins {
        register("ktorServer") {
            id = "ktor-server-convention"
            implementationClass = "cz.lastaapps.KtorServerConvention"
        }
        register("base-kmp") {
            id = "base-kmp-convention"
            implementationClass = "cz.lastaapps.BaseKMPConvention"
        }
    }
}
