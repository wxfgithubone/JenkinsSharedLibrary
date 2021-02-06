#!groovy

//npm

@Library('jenkinslib@master') _

def deploy_web = new org.devops.web()
def tools = new org.devops.tools() // 格式化打印

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
                    deploy_web.pull(projectName, branchName)
                }
            }
        }
        stage("Bulid"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---打包---》》》》》》》》","green1")
                    deploy_web.bulid(environment, projectName)
                }
            }
        }
        stage("Push"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---推送---》》》》》》》》","green1")
                    deploy_web.push(environment, projectName)
                }
            }
        }
        stage("Backup"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---备份---》》》》》》》》","green1")
                    deploy_web.backup(environment, projectName)
                }
            }
        }
        stage("Restart"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---重启---》》》》》》》》","green1")
                    deploy_web.restart(environment, projectName)
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
