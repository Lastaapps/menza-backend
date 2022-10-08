package cz.lastaapps.extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependencyNotation: Any) =
    add(Constants.IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) =
    add(Constants.TEST_IMPLEMENTATION, dependencyNotation)

fun DependencyHandlerScope.api(dependencyNotation: Any) = add(Constants.API, dependencyNotation)
fun DependencyHandlerScope.coreLibraryDesugaring(dependencyNotation: Any) =
    add(Constants.DESUGARING, dependencyNotation)

fun DependencyHandlerScope.ksp(dependencyNotation: Any) =
    add(Constants.KSP, dependencyNotation)
