plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
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
        homepage = "Link to the Shared Module homepage"
        version = "2.0.0"
        ios.deploymentTarget = "14.1"
//        source = ":git => 'git@github.com:aalmeidaglobant/measure-converter.git', :tag => '$version'"
//        publishDir = project.file("pods")
//        podfile = project.file("../iosSampleApp/Podfile")

        framework {
            baseName = "MeasureConverter"
            isStatic = true
//            outputDirectory =  project.file("pods")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
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