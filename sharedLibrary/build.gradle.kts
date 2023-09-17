plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
}

group = "com.example.measure_converter"
version = "1.0.2"


publishing {
    publications.withType<MavenPublication> {
        artifactId = "MeasureConverter"
    }

    repositories {
        maven {
            url = uri((System.getenv("MAVEN_WRITE_URL")))

            credentials {
                password = System.getenv("MAVEN_PWD")
                username = System.getenv("MAVEN_USERNAME")
            }
        }

    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {

        publishLibraryVariants("release", "debug")

        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()


    cocoapods {
        name = "MeasureConverterPod"
        summary = "Some description for the Shared Module"
        homepage = "https://github.com/aalmeidaglobant/measure-converter"
        ios.deploymentTarget = "14.1"

        source =
            "{ :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '$version' }"
//        publishDir = project.file("pods")
//        podfile = project.file("../iosSampleApp/Podfile")
        license = "{ :type => 'MIT', :text => 'License text'}"
        framework {
            baseName = "MeasureConverter"
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.example.measureconverter"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}
