// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "MeasureConverter",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: "MeasureConverter",
            targets: ["MeasureConverter"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "MeasureConverter",
            path: "./native/debug/MeasureConverter.xcframework"
        ),
    ]
)
