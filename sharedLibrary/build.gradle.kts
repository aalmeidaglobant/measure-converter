plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("maven-publish")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}
val libName = "MeasureConverter"
val libVersion = "1.1.5"
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
    multiplatformSwiftPackage {
        packageName("YourModuleName")
        swiftToolsVersion("5.3")
        targetPlatforms {
            iOS { v("13") }
        }
        outputDirectory(File(rootDir, "/"))
    }
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

task("checkoutMain", type = Exec::class) {
    workingDir = File("$rootDir/pods")
    commandLine("git", "checkout", "main").standardOutput
}

task("publishDevXCFramework") {
    description = "Publish iOs framework to the Cocoa Repo"


    dependsOn( "podPublishDebugXCFramework")

    doLast {
        val dir = File("$rootDir/pods/debug/${libName}Pod.podspec")
        val tempFile = File("$rootDir/pods/debug/${libName}Pod.podspec.new")

        val reader = dir.bufferedReader()
        val writer = tempFile.bufferedWriter()
        var currentLine: String?

        while (reader.readLine().also { currLine -> currentLine = currLine } != null) {
            if (currentLine?.startsWith("    spec.version") == true) {
                writer.write("spec.version       = \"${libVersion}\"" + System.lineSeparator())
            } else {
                writer.write(currentLine + System.lineSeparator())
            }
            if (currentLine?.startsWith("    spec.vendored_frameworks") == true) {
                writer.write("spec.vendored_frameworks       = \"$rootDir/pods/debug/MeasureConverter.xcframework\"" + System.lineSeparator())
            } else {
                writer.write(currentLine + System.lineSeparator())
            }
        }
        writer.close()
        reader.close()
        val successful = tempFile.renameTo(dir)

        copy {
            from(dir)
            into(rootDir)
        }

        if (successful) {
            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine(
                    "git",
                    "add",
                    "."
                ).standardOutput
            }
            project.exec {
                workingDir = File("$rootDir/pods")

                commandLine(
                    "git",
                    "commit",
                    "-a",
                    "-m",
                    "\"New dev release: ${libVersion}}\""
                ).standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "tag", libVersion).standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "push", "origin", "develop", "--tags").standardOutput
            }
        }
    }
}

task("publishReleaseXCFramework") {
    description = "Publish iOs framework to the Cocoa Repo"


    dependsOn("checkoutMain", "podPublishReleaseXCFramework")

    doLast {
        val dir = File("$rootDir/pods/release/${libName}Pod.podspec")
        val tempFile = File("$rootDir/pods/release/${libName}Pod.podspec.new")

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
                    "\"New release: ${libVersion}}\""
                ).standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "tag", libVersion).standardOutput
            }

            project.exec {
                workingDir = File("$rootDir/pods")
                commandLine("git", "push", "origin", "main", "--tags").standardOutput
            }
        }
    }
}