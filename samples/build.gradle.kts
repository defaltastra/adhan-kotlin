import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  application
}

repositories {
  mavenCentral() 
}

dependencies {
  implementation(project(":adhan"))
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

application {
 mainClass.set("io.github.meypod.adhan_kotlin.Example")
}
