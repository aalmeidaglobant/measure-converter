Pod::Spec.new do |spec|
    spec.name                     = 'MeasureConverterPod'
    spec.version                  = '1.1.6'
    spec.homepage                 = 'https://github.com/aalmeidaglobant/measure-converter'
    spec.source                   = { :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '1.1.6' }
    spec.authors                  = ''
    spec.license                  = { :type => 'MIT', :text => 'License text'}
    spec.summary                  = 'Some description for the Shared Module'
    spec.vendored_frameworks      = 'MeasureConverter.xcframework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
                
                
                
                
end