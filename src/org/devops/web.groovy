package org.devops

// 拉取代码
def pull(projectName, branchName){
    
    String home_path = "/home/project/${projectName}"
    
    println("Pull ${projectName} Branch ${branchName}")
    
    sh "rm -rf ${home_path}"
    println("Delecte ${projectName} Success!")
    
    sh "git clone -b ${branchName} git@xxx.xxx.x.xxx:ljx-web/${projectName}.git ${home_path}"
    println("Pull ${projectName} Success!")
}


// 打包
def bulid(env, projectName){
    
    String home_path = "/home/project/${projectName}/"
    
    if ( projectName == "site-design-pc-web" || projectName == "site-design-mobile-web" ){
        println("\033[47;34m ${projectName} This project does not require packaging! \033[0m") // blue
        sh """
           cd ${home_path}
           pwd
           zip -rq build-web.zip build-web/
           zip -rp static-page.zip static-page/
           zip -T build-web.zip
           zip -T static-page.zip
           ls
           """
        println("zip ${projectName} success")
        
    }else{
        println("\033[47;34m ${projectName} 正在打包.... \033[0m")
        sh """
           cd ${home_path}
           pwd
           npm install -g cnpm --registry=https://registry.npm.taobao.org
           cnpm i
           cnpm run build:${env}
           zip -rq ${projectName}.zip dist/
           zip -T ${projectName}.zip
           ls
           """
        println("zip ${projectName} success")
    }
}


// 推送
def push(env,projectName){
    
    String dist_path = "/home/project/${projectName}";
    String backup_path = "/home/deploy/backup";
    
    def ip_list = libraryResource "org/ip/${env}_ip.txt"
    _ip = ip_list.split("\n")
    for ( ip in _ip ){
        println("env ${ip}")
        if ( projectName == "site-design-pc-web" || projectName == "site-design-mobile-web" ){
            sh """
               scp ${dist_path}/build-web.zip root@${ip}:${backup_path}/build-web.zip
               scp ${dist_path}/static-page.zip root@${ip}:${backup_path}/static-page.zip
               """
        }else{
            sh "scp ${dist_path}/${projectName}.zip root@${ip}:${backup_path}/${projectName}.zip"
        }
        println("Push ${projectName} ${ip} success!")
    }
}


// 备份
def backup(env, projectName){
    
    String work_path = "/home/project/${projectName}";
    String backup_path = "/home/deploy/backup";
    def now = new Date().format("yyyy-MM-dd-HH-mm") // 获取当前时间年月日时分
    
    def ip_list = libraryResource "org/ip/${env}_ip.txt"
    
    _ip = ip_list.split("\n")
    for ( ip in _ip ){
        if ( projectName == "site-design-pc-web" || projectName == "site-design-mobile-web" ){
            sh """
               ssh root@${ip} "cp ${backup_path}/build-web.zip ${backup_path}/build-web-${now}.zip"
               ssh root@${ip} "cp ${backup_path}/static-page.zip ${backup_path}/static-page-${now}.zip"
               ssh root@${ip} "ls ${backup_path}/build-web-${now}.zip"
               ssh root@${ip} "ls ${backup_path}/static-page-${now}.zip"
               """
        }else{
            sh """
               ssh root@${ip} "cp ${backup_path}/${projectName}.zip ${backup_path}/${projectName}-${now}.zip"
               ssh root@${ip} "ls ${backup_path}/${projectName}-${now}.zip"
               """
        }
    }
    println("${env} bachup success!")
}


// 重启
def restart(env, projectName){
    String bachup_path = "/home/deploy/backup";
    String work_path = "/home/workspace/${projectName}";
    
    def ip_list = libraryResource "org/ip/${env}_ip.txt"
    _ip = ip_list.split("\n")
    for ( ip in _ip ){
        if ( projectName == "site-design-pc-web" || projectName == "site-design-mobile-web" ){
            sh """
               ssh root@${ip} "cp ${bachup_path}/build-web.zip ${work_path}/build-web.zip"
               ssh root@${ip} "unzip -od ${work_path}/ ${work_path}/build-web.zip"
               ssh root@${ip} "cp ${bachup_path}/static-page.zip ${work_path}/static-page.zip"
               ssh root@${ip} "unzip -od ${work_path}/ ${work_path}/static-page.zip"
               ssh root@${ip} "ls ${work_path}/"
               """
        }else{
            sh """
               ssh root@${ip} "cp ${bachup_path}/${projectName}.zip ${work_path}/${projectName}.zip"
               ssh root@${ip} "unzip -od ${work_path}/ ${work_path}/${projectName}.zip"
               ssh root@${ip} "ls ${work_path}/"
               """
        }
    }
    println("${env} ${projectName} restart success!")
}




