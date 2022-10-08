
plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    alias(libs.plugins.benNamesVersions)
    alias(libs.plugins.versionCatalogUpdate)
}


// https://github.com/littlerobots/version-catalog-update-plugin#configuration
versionCatalogUpdate {
    sortByKey.set(true)
    // pins version - wouldn't be changed
    pin {}
    // keeps entry - wouldn't be deleted when unused
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}

