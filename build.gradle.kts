plugins {
    java
    id("com.gradleup.shadow")
    id("io.github.intisy.github-gradle") version "1.8.3"
}

github {
    accessToken = System.getenv("GITHUB_TOKEN") ?: ""
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
    githubCompileOnly("Slimefun5:Slimefun5:gh-v5.2.3.1")
    githubCompileOnly("Slimefun5:ExoticGarden:v1.7.2")
    githubCompileOnly("Slimefun5:InfinityExpansion:v1.1.2")
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
        archiveFileName.set("DynaTech-1.0.0-UNOFFICIAL.jar")
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
