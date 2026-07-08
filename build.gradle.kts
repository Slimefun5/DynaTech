plugins {
    java
    id("com.gradleup.shadow")
    id("io.github.intisy.github-gradle") version "1.8.3"
}

group = "me.profelements"
description = "DynaTech is a Slimefun addon that adds various machines, generators, tools and more."

apply(from = "https://raw.githubusercontent.com/Slimefun5/gradle/stable/slimefun-addon.gradle")

dependencies {
    githubImplementation("Slimefun5:SlimefunMetrics:v1.0.0")
    githubCompileOnly("Slimefun5:ExoticGarden:v1.7.2")
    githubCompileOnly("Slimefun5:InfinityExpansion:v1.1.2")
}

tasks {
    shadowJar {
        relocate("org.bstats", "dynatech.libs.bstats")
        relocate("dev.j3fftw.extrautils", "me.profelements.dynatech.extrautils")
    }
}
