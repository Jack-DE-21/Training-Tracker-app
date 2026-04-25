plugins {
    kotlin("jvm") version "2.2.20"

    // Plugin for Dokka - KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.20"

    jacoco

    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"

    application
}

group = "ie.setu"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")

    // For generating a Dokka Site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
}

application {
    mainClass.set("MainKt")
}

tasks.test {
    useJUnitPlatform()

    // Report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    // For building a fat jar - include all dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}

kotlin {
    jvmToolchain(16)
}
// & "C:\Users\A R\.jdks\corretto-16.0.2\bin\java.exe" -jar "build\libs\Training-Tracker-app-1.0.jar"
