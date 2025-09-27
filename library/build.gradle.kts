import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "io.github.kotlin"
version = "1.0.0"

kotlin {
    val xcframeworkName = "KMMAnalytics"
    val xcf = XCFramework(xcframeworkName)

    jvm()

    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = xcframeworkName
            binaryOption("bundleId", "org.aetherius.${xcframeworkName}")
            xcf.add(this)
            isStatic = true
        }
    }

    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "org.jetbrains.kotlinx.multiplatform.library.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "My library"
        description = "A library."
        inceptionYear = "2024"
        url = "https://github.com/kotlin/multiplatform-library-template/"
        licenses {
            license {
                name = "XXX"
                url = "YYY"
                distribution = "ZZZ"
            }
        }
        developers {
            developer {
                id = "XXX"
                name = "YYY"
                url = "ZZZ"
            }
        }
        scm {
            url = "XXX"
            connection = "YYY"
            developerConnection = "ZZZ"
        }
    }
}

// --- XCFramework + Swift Package export tasks ---

val xcframeworkName = "KMMAnalytics"

// Task to generate a Swift Package wrapping the XCFramework
tasks.register("swiftPackage") {
    group = "build"
    description = "Generate Swift Package manifest and bundle for $xcframeworkName"

    doLast {
        val packageDir = layout.buildDirectory.dir("swiftPackage/${xcframeworkName}Package").get().asFile
        packageDir.mkdirs()

        // Copy XCFramework output
        copy {
            from(layout.buildDirectory.dir("XCFrameworks/release"))
            include("${xcframeworkName}.xcframework/**")
            into(packageDir)
        }

        // Write Package.swift
        val packageSwift = packageDir.resolve("Package.swift")
        packageSwift.writeText(
            """
            // swift-tools-version:5.5
            import PackageDescription

            let package = Package(
                name: "${xcframeworkName}Package",
                platforms: [
                    .iOS(.v13)
                ],
                products: [
                    .library(
                        name: "${xcframeworkName}",
                        targets: ["${xcframeworkName}"]
                    ),
                ],
                targets: [
                    .binaryTarget(
                        name: "${xcframeworkName}",
                        path: "${xcframeworkName}.xcframework"
                    )
                ]
            )
            """.trimIndent()
        )
    }
}
