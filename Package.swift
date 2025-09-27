// swift-tools-version:5.9
import PackageDescription

let package = Package(
  name: "KMMAnalytics",
  platforms: [
   .iOS(.v14),
  ],
  products: [
   .library(name: "KMMAnalytics", targets: ["KMMAnalytics"])
  ],
  targets: [
   .binaryTarget(
     name: "KMMAnalytics",
     url: "https://github.com/aleksa-aetherius/KMMAnalytics/releases/download/1.0/KMMAnalytics.xcframework.zip",
checksum:"500814033d2d649a306eb9ec33c1652e53b090e18b87aa0038d4ee9cb7578c2c"
)
  ]
)