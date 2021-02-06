package org.devops

// 拉取代码
def pull(projectName, branchName){
    
    String home_path = "/home/project/${projectName}";
    println("Pull ${projectName} Branch ${branchName}")
    
    sh "rm -rf ${home_path}"
    println("Delecte ${projectName} Success!")
    
    sh "git clone -b ${branchName} git@xxx.xxx.x.xxx:ljx-api/${projectName}.git ${home_path}"
    println("Pull ${projectName} Success!")
}

// 打包
def compile(env, projectName){
    String home_path = "/home/project/${projectName}";
    sh """
       cd ${home_path}
       ls
       mvn -q clean package -Dmaven.test.skip=true -P${env}
       ls target
       """
    println("Compile ${projectName} ${env} Success!")
}

// 推送
def push(env, projectName){
    
    String dist_path = "/home/project/${projectName}/target";
    String backup_path = "/home/project/backup";
    
    def ip_list = libraryResource "org/ip/${env}_ip.txt" //加载资源文件
    _ip = ip_list.split("\n")
    for ( ip in _ip ){
        println("当前部署IP: ${ip}")
        sh "scp ${dist_path}/${projectName}.jar root@${ip}:${backup_path}/${projectName}.jar"
        println("Push ${projectName} ${ip} Success!")
    }
}

// 备份
def backup(env, projectName){
    
    String backup_path = "/home/project/backup";
    def now = new Date().format("yyyy-MM-dd-HH-mm")
    def ip_list = libraryResource "org/ip/${env}_ip.txt"
    _ip = ip_list.split("\n")
    
    for ( ip in _ip ){
        println("当前部署IP: ${ip}")
        sh """
           ssh root@${ip} "cp ${backup_path}/${projectName}.jar ${backup_path}/${projectName}-${now}.jar"
           ssh root@${ip} "ls ${backup_path}/${projectName}-${now}.jar"
           """
           
        println("${backup_path}/${now}.jar")
        println("Backup ${projectName} ${ip} Success!")
    }
}

// 重启
def restart(env, projectName){
    
    String backupPath = "/home/project/backup";
    String workPath = "/home/workspace/${projectName}";
    String log_path = "${workPath}/logs";
    
    def ip_list = libraryResource "org/ip/${env}_ip.txt"
    _ip = ip_list.split("\n")
    
    for ( ip in _ip ){
        println("当前部署IP: ${ip}")
        sh """
           ssh root@${ip} "sh /home/project/shell/stop.sh ${projectName}"
           ssh root@${ip} "sh /home/project/shell/start.sh ${projectName}"
           """
        
        println("Restart ${projectName} ${ip} Success!")
        
        /*String stop = String.format('''ssh root@%s "ps -ef | grep %s | grep '/bin/java -jar %s.jar' | grep -v grep | awk '{print $2}'"''',ip,projectName,projectName)
        println("停止项目的进程：${stop}")
        pid = sh(returnStdout: true, script: "${stop}")  
        println(pid)
        //sh "kill -9 ${pid}"
        
        //println("${projectName} ${ip} stop !")
        
        /*ssh '''
            root@${ip} "nohup /usr/java/jdk1.8.0_211/bin/java -jar ${projectName}.jar --spring.profiles.active=${env} >${log_path}/console.log 2>$1 &"
            root@${ip} "ps -ef|grep ${projectName}|grep -v grep|awk '{print$2}}'"
            '''*/
        /*String start = String.format(''' ssh root@%s "nohup /usr/java/jdk1.8.0_211/bin/java -jar %s.jar --spring.profiles.active=%s >%s/console.log 2>$1 &"''',ip,projectName,env,log_path)
        
        println("启动命令：${start}")
        
        sta = sh(returnStdout: true, script: "${start}")
        println(sta)*/
        
    }
}


