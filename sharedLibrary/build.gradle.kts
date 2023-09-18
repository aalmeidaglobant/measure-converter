plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
}
val libName = "MeasureConverter"
val libVersion = "1.1.3"
group = "com.example.measure_converter"
version = libVersion


publishing {
    publications.withType<MavenPublication> {
        artifactId = libName
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
        version = libVersion
        source = "{ :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '$libVersion' }"
        publishDir = rootProject.file("pods")
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


task("checkoutDev", type = Exec::class) {
    workingDir = File("$rootDir/pods")
    commandLine("git", "checkout", "develop").standardOutput
}

task("publishDevXCFramework") {
    description = "Publish iOs framweork to the Cocoa Repo"


    dependsOn("checkoutDev", "podPublishDebugXCFramework")

    doLast {
        val dir = File("$rootDir/pods/debug/${libName}Pod.podspec")
        val tempFile = File("$rootDir/pods/debug/${libName}Pod.podspec.new")

        val reader = dir.bufferedReader()
        val writer = tempFile.bufferedWriter()
        var currentLine: String?

        while (reader.readLine().also { currLine -> currentLine = currLine } != null) {
            if (currentLine?.startsWith("s.version") == true) {
                writer.write("s.version       = \"${libVersion}\"" + System.lineSeparator())
            } else {
                writer.write(currentLine + System.lineSeparator())
            }
        }
        writer.close()
        reader.close()
        val successful = tempFile.renameTo(dir)

        if (successful) {

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine(
                    "git",
                    "commit",
                    "-a",
                    "-m",
                    "\"New dev release: ${libVersion}-debug}\""
                ).standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "tag", "$libVersion-debug").standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "push", "origin", "develop", "--tags").standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("pod", "lib", "lint", "${libName}Pod.podspec").standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("pod", "repo", "push", "measure-converter-specs", "${libName}Pod.podspec").standardOutput
            }
        }
    }
}
