import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("co.touchlab.faktory.kmmbridge")

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
        name = "MeasureConverter"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "3.0.0"
        ios.deploymentTarget = "14.1"
        source =
            ":git => 'git@github.com:aalmeidaglobant/measure-converter.git', :tag => '$version', :branch => 'develop'"
//        publishDir = project.file("../Pods/$version/$name")
//        podfile = project.file("../iosSampleApp/Podfile")

        framework {
            baseName = "MeasureConverter"
            isStatic = true
//            outputDirectory = project.file("../../Pods")
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] =
            org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }

//    val xcFramework = XCFramework(name)
//    val iosTargets = listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    )
//    iosTargets.forEach {
//        it.binaries {
//            framework {
//                baseName = name
//                xcFramework.add(this)
//            }
//        }
//    }

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

kmmbridge {
    mavenPublishArtifacts()
    githubReleaseVersions()
    spm()
    cocoapods("git@github.com:aalmeidaglobant/measure-converter.git")
    versionPrefix.set("1.0")
    //etc
}