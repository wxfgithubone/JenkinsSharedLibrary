package org.devops

def Build(buildType, buildShell){

    def buildTools = ["mvn":"maven","npm":"NPM"]
    
    println("当前选择的构建类型为：${buildType}")
    
    buildHome= tool buildTools[buildType]
    
    if ( "${buildType}" == "npm" ){
        
        println("当前构建的是前端项目")
        
    }else{
        
        println("当前构建的是后端项目")
        println("buildShell的值为：${buildShell}")
        println("打包目录：${buildHome}bin/${buildType} --version")
        
        sh "${buildHome}/bin/${buildType} --version"
    }
    
}