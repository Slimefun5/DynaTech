plugins {
    java
    id("com.gradleup.shadow")
}

group = "me.profelements"
version = "1.0.0-UNOFFICIAL"
description = "DynaTech is a Slimefun addon that adds various machines, generators, tools and more."

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly(files("../../core/Slimefun5/core/build/libs/Slimefun v5.0.0-UNOFFICIAL-MC26.1.2.jar"))
    compileOnly(files("../ExoticGarden/build/libs/ExoticGarden vv1.0.0-UNOFFICIAL-MC26.1.2.jar"))
    compileOnly(files("../InfinityExpansion/build/libs/InfinityExpansion v1.0.0.jar"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
    jar {
        enabled = false
    }
    shadowJar {
        archiveFileName.set("DynaTech v${project.version}-MC26.1.2.jar")
        relocate("dev.j3fftw.extrautils", "me.profelements.dynatech.extrautils")
        exclude("META-INF/**")
    }
    build {
        dependsOn(shadowJar)
    }
    compileTestJava {
        enabled = false
    }
    test {
        enabled = false
    }
}
