plugins {
    java
    id("com.gradleup.shadow")
    id("io.github.intisy.github-gradle") version "1.8.3"
}

github {
    accessToken = System.getenv("GITHUB_TOKEN") ?: ""
}

group = "me.profelements"
description = "DynaTech is a Slimefun addon that adds various machines, generators, tools and more."

fun latestGitTagVersion(): String? = try {
    val out = providers.exec {
        workingDir = rootDir
        commandLine("git", "describe", "--tags", "--abbrev=0")
        isIgnoreExitValue = true
    }
    if (out.result.get().exitValue == 0)
        out.standardOutput.asText.get().trim().removePrefix("gh-").removePrefix("v").takeIf { it.isNotBlank() }
    else null
} catch (e: Exception) { null }

version = (project.findProperty("artifact_version") as String?)?.removePrefix("v")?.takeIf { it.isNotBlank() }
    ?: latestGitTagVersion()
    ?: "1.0.0"
val versionSuffix: String = when {
    !(project.findProperty("artifact_version") as String?).isNullOrBlank() -> ""
    System.getenv("GITHUB_ACTIONS") == "true" -> "-EXPERIMENTAL"
    else -> "-UNOFFICIAL"
}
val displayVersion = "${project.version}$versionSuffix"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    githubImplementation("Slimefun5:SlimefunMetrics:v1.0.0")
    githubCompileOnly("Slimefun5:Slimefun5:gh-v5.2.3.2")
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
            expand("version" to displayVersion)
        }
    }
    jar {
        enabled = false
    }
    shadowJar {
        relocate("org.bstats", "dynatech.libs.bstats")
        archiveFileName.set("DynaTech-$displayVersion.jar")
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
