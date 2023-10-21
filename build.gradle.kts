import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose") version "1.4.3"
}

repositories {
    google()
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
    maven("https://repo1.maven.org/maven2/")
}

group = rootProject.group
version = rootProject.version
description = "$group:${project.name}"
java.sourceCompatibility = rootProject.java.sourceCompatibility

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("net.java.dev.jna:jna:5.10.0")
    implementation("net.java.dev.jna:jna-platform:5.10.0")
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.2")
    api("org.apache.commons:commons-lang3:3.12.0")
    api("org.apache.commons:commons-text:1.9")
    api("org.reflections:reflections:0.10.2")
    api(compose.materialIconsExtended)
    implementation(kotlin("stdlib-jdk8"))
}

sourceSets.main {
    java.srcDir("src/main/kotlin")
    resources.srcDir("src/main/resources")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=org.mylibrary.OptInAnnotation"
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "fr.lewon.ffxiv.bot.VLFfxivBotAppKt"
    }
    from(sourceSets["main"].output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

compose.desktop {
    application {
        mainClass = "fr.lewon.dofus.bot.VLFfxivBotAppKt"
        nativeDistributions {
            includeAllModules = true
            windows {
                iconFile.set(File("src/main/resources/icon/global_logo.png"))
            }
            packageName = "VLFfxivBotApp"
            packageVersion = "$version"
        }
    }
}