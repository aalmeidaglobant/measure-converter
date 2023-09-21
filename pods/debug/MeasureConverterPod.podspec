Pod::Spec.new do |spec|
Pod::Spec.new do |spec|
    spec.name                     = 'MeasureConverterPod'
    spec.name                     = 'MeasureConverterPod'
    spec.version                  = '1.1.3'
    spec.version                  = '1.1.3'
    spec.homepage                 = 'https://github.com/aalmeidaglobant/measure-converter'
    spec.homepage                 = 'https://github.com/aalmeidaglobant/measure-converter'
    spec.source                   = { :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '1.1.3' }
    spec.source                   = { :git => 'https://github.com/aalmeidaglobant/measure-converter.git', :tag => '1.1.3' }
    spec.authors                  = ''
    spec.authors                  = ''
    spec.license                  = { :type => 'MIT', :text => 'License text'}
    spec.license                  = { :type => 'MIT', :text => 'License text'}
    spec.summary                  = 'Some description for the Shared Module'
    spec.summary                  = 'Some description for the Shared Module'
    spec.vendored_frameworks      = 'MeasureConverter.xcframework'
    spec.vendored_frameworks      = 'MeasureConverter.xcframework'
    spec.libraries                = 'c++'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
    spec.ios.deployment_target = '14.1'
                
                
                
                
                
                
                
                
end
end
