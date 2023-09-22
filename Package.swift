// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "YourModuleName",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "YourModuleName",
            targets: ["YourModuleName"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "YourModuleName",
            path: "./YourModuleName.xcframework"
        ),
    ]
)
