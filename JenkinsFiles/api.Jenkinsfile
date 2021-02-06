#!groovy

//maven

@Library('jenkinslib@master') _

def deploy_api = new org.devops.api()
def tools = new org.devops.tools()

String branchName = "$env.BRANCH"
String projectName = "$env.PROJECT_NAME"
String environment = "$env.ENV"

pipeline{
    
    agent { node { label "master" } }
    
    options {
        timestamps()
        skipDefaultCheckout()
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
    }
    
    stages {
        stage("Pull"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---获取代码---》》》》》》》》","green1")
                    deploy_api.pull(projectName, branchName)
                }
            }
        }
        stage("compile"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---打包---》》》》》》》》","green1")
                    deploy_api.compile(environment, projectName)
                }
            }
        }
        stage("Push"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---推送---》》》》》》》》","green1")
                    deploy_api.push(environment, projectName)
                }
            }
        }
        stage("Backup"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---备份---》》》》》》》》","green1")
                    deploy_api.backup(environment, projectName)
                }
            }
        }
        stage("Restart"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---重启---》》》》》》》》","green1")
                    deploy_api.restart(environment, projectName)
                }
            }
        }
    }
    
    post{
        always{
            script{
                println("always")
            }
        }
        success{
            script{
                currentBuild.description = "\n 构建成功!"
            }
        }
        failure{
            script{
                currentBuild.description = "\n 构建失败!"
            }
        }
        aborted{
            script{
                currentBuild.description = "\n 构建取消!" 
            }
        }
    }
}


