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
     url: "https://github.com/aleksa-aetherius/KMMAnalytics/releases/download/1.0.2/KMMAnalytics.xcframework.zip",
checksum:"94b68f363f80d3188864f58ccec4f5437ea0cb50b1a4a780a13516b4be554be1"
)
  ]
)