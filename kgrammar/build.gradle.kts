plugins {
    java
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(files("lib.jar"))
}