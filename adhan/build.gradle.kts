@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.4.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "io.github.meypod.adhan_kotlin"
version = property("version") ?: ""

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }

    jvm()

//    js(IR) {
//        nodejs {
//            testTask {
//                useMocha {
//                    timeout = "30s"
//                }
//            }
//        }
//    }
//
//    wasmJs {
//        nodejs {
//            testTask {
//                useMocha {
//                    timeout = "30s"
//                }
//            }
//        }
//    }

    linuxX64()
    linuxArm64()
    mingwX64()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosArm64()
    macosX64()

    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                api("com.squareup.okio:okio:3.16.1")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation(kotlin("stdlib-jdk8"))
            }
        }

//        val jsTest by getting {
//            dependencies {
//                implementation(kotlin("test-js"))
//                implementation("com.squareup.okio:okio-nodefilesystem:3.16.1")
//                implementation(npm("@js-joda/timezone", "2.3.0"))
//            }
//        }
//
//        val wasmJsTest by getting {
//            dependencies {
//                implementation(npm("@js-joda/timezone", "2.3.0"))
//            }
//        }
    }

    // set an environment variable and read it in the test
    // https://publicobject.com/2023/04/16/read-a-project-file-in-a-kotlin-multiplatform-test/
    tasks.withType<KotlinJvmTest>().configureEach {
        environment("ADHAN_ROOT", rootDir)
    }

    tasks.withType<KotlinNativeTest>().configureEach {
        // required for the variable to propagate to the simulator
        environment("SIMCTL_CHILD_ADHAN_ROOT", rootDir)
        environment("ADHAN_ROOT", rootDir)
    }

    tasks.withType<KotlinJsTest>().configureEach {
        environment("ADHAN_ROOT", rootDir.toString())
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("io.github.meypod", "adhan_kotlin", version.toString())

    pom {
        name.set("Adhan Prayertimes Library")
        description.set("extended version of adhan-kotlin by batoulapps for use in al-azan")
        url.set("https://github.com/meypod/adhan-kotlin")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
            }
        }
        scm {
            url.set("https://github.com/meypod/adhan-kotlin")
        }

    }
}

// auto replace yarn.lock
rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    rootProject.the<YarnRootExtension>().yarnLockMismatchReport =
        YarnLockMismatchReport.WARNING
    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = true
}
