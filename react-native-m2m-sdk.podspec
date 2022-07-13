# react-native-m2m-sdk.podspec

require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-m2m-sdk"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                   react-native-m2m-sdk
                   DESC
  s.homepage     = "https://github.com/devapps-systems/react-native-m2m-sdk"
  s.license      = "MIT"
  
  s.authors      = { "Hemanshu Liya" => "hemanshuliya@email.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/devapps-systems/react-native-m2m-sdk.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,cc,cpp,m,mm,swift}"
  
  s.requires_arc = true

  s.vendored_frameworks = "ios/M2MSDK.xcframework"
  s.swift_version = "4.2"
  s.ios.deployment_target  = '9.0'
  s.user_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }
  s.pod_target_xcconfig = { 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'arm64' }

  s.frameworks  = 'AdSupport', 'CoreBluetooth', 'CoreLocation', 'SystemConfiguration', 'CoreTelephony'

  s.dependency "React"
  
end

