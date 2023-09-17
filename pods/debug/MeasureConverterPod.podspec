Pod::Spec.new do |spec|
    spec.name                     = 'MeasureConverterPod'
    spec.version                  = '1.1.1'
    spec.homepage                 = 'https://github.com/aalmeidaglobant/measure-converter'
    spec.source                   = { :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '1.1.0' }
    spec.authors                  = ''
    spec.license                  = { :type => 'MIT', :text => 'License text'}
    spec.summary                  = 'Some description for the Shared Module'
                
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
                
                
                
    spec.vendored_frameworks = pods/debug/MeasureConverter.xcframework
end