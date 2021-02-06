#!groovy

@Library('jenkinslib@master') _

def tools = new org.devops.tools()

String branch = "$env.BRANCH"
String project_name = "$env.PROJECT_NAME"
String git_url = "$env.GIT_GROUP"


pipeline{
    agent { node { label "master" } }
    options{
        timestamps()
        skipDefaultCheckout()
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
    }
    stages{
        stage("Get Code"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---获取代码---》》》》》》》》","green1")
                    sh "rm -rf /home/project/${project_name}"
                    sh "git clone -b ${branch} ${git_url}/${project_name}.git /home/project/${project_name}"
                    println("Get ${project_name} Code Success")
                }
            }
        }
        stage("Compile"){
            steps{
                script{
                    tools.PrintMes("《《《《《《《《---编译---》》》》》》》》","green1")
                    sh """
                       cd /home/project/${project_name}/
                       mvn -q clean deploy -U -Dmaven.test.skip=true
                       """
                    sh "ls /home/project/${project_name}/target"
                    println("Deploy ${project_name} success")
                }
            }
        }
    }
    post {
        always {
            script{
                println("always")
            }
        }

        success {
            script{
                currentBuild.description = "\n 构建成功!" 
            }
        }

        failure {
            script{
                currentBuild.description = "\n 构建失败!" 
            }
        }

        aborted {
            script{
                currentBuild.description = "\n 构建取消!" 
            }
        }
    }
}