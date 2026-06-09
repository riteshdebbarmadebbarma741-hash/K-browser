pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "K-Browser"
include(":app")
include(":core:network")
include(":core:database")
include(":core:browser-engine")
include(":core:designsystem")
include(":feature:adblocker")
include(":feature:tabs-manager")
include(":feature:downloads")
include(":feature:bookmarks-history")
