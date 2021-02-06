package org.devops

def getCode(branch, projectName){
    sh "rm -rf ${codePath}/${project}"
    println("git clone -b ${branch} git@xxx.xxx.x.xxx:ljx-module/${projectName}.git /home/project/${project}")
    sh "git clone -b ${branch} git@xxx.xxx.x.xxx:ljx-module/${projectName}.git /home/project/${project}"
    println("Get ${project} Code Success")
}


def compileCode(projectName){
    sh """
       cd /home/project/${project}/
       mvn -q clean deploy -U -Dmaven.test.skip=true
       """
    sh "ls /home/project/${project}/target"
    println("Deploy ${project} success")
}

