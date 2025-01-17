
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.60.3"
////                            # available:"0.60.4"
////                            # available:"0.60.5"
}

refreshVersions {
}

include("json-dsl")
include("search-dsls")
include("search-client")
include("docs")
rootProject.name = "kt-search"
