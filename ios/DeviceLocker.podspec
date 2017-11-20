
Pod::Spec.new do |s|
  s.name         = "DeviceLocker"
  s.version      = "1.0.0"
  s.summary      = "DeviceLocker"
  s.description  = <<-DESC
                  DeviceLocker
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/DeviceLocker.git", :tag => "master" }
  s.source_files  = "DeviceLocker/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  