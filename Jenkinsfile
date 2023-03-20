def skipRemainingStages = false

def tomcat_dev = 'http://192.168.1.145:8880/'
def tomcat_pre = 'http://192.168.1.145:8881/'
def tomcat_pro = 'http://192.168.1.145:8882/'
def current_profile = tomcat_dev

def isProProfile(){
    return "${env.JOB_NAME}".contains("_pro")
}
def isPreProfile(){
    return "${env.JOB_NAME}".contains("_pre")
}

pipeline {
    agent any

    triggers{ cron('H/15 * * * *') }
    
    stages {
        stage("Set Tomcat Profile"){
            steps{
                script {
                    println("Nombre del job: ${env.JOB_NAME}")
                    
                    if(isProProfile()){
                        current_profile = tomcat_pro
                        println("Pipeline del entorno de producción")
                        return
                    }
                    
                    if(isPreProfile()){
                        current_profile = tomcat_pre
                        println("Pipeline del entorno de preproducción")
                        return
                    }
                    
                    println("Pipeline del entorno de desarrollo")
                }
            }
        }
        stage("Code Checkout") {
            steps {
                script {
                    println("Comprobando si hay cambios en el repositorio...")
                    //checkout changelog: true, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: '0dc4562c-e6a8-41e7-a464-95a3401aab54', url: 'https://github.com/alvaro-gimenez-gorris/tomcat_jenkins']])
                    checkout scm
                    
                    changeCount = currentBuild.changeSets.size()
            		println("Número de cambios encontrados: " + changeCount)
            		if(changeCount > 0) {
            			println("Cambios encontrados en la rama. Seguimos con el pipeline")
            		}
            		else {
            			println("Finalizamos el despliegue porque no se han encontrado cambios")
            			skipRemainingStages = true
                        println "skipRemainingStages = ${skipRemainingStages}"
            		}
                }
            }
        }

        stage("Build") {
            when {
                expression {
                    !skipRemainingStages
                }
            }

            steps {
                script {
                    println "Instalando las dependencias con Maven..."
                    sh "mvn clean install -Dmaven.test.skip=true"
                }
            }
        }

        stage("Test Cases Execution") {
            when {
                expression {
                    !skipRemainingStages
                }
            }

            steps {
                script {
                    println "Validando los test definidos..."
                    sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Pcoverage-per-test"
                }
            }
        }
        
        stage("Sonar Analysis") {
            when {
                expression {
                    !skipRemainingStages
                }
            }

            steps {
                script {
                    println "Ejecutando la validación con Sonar..."
                    // sh "mvn sonar:sonar -Dsonar.host.url:http://ip:puerto -Dsonar.login=id"
                }
            }
        }
        
        stage("Code Deployment") {
            when {
                expression {
                    !skipRemainingStages
                }
            }

            steps {
                script {
                    println "Desplegando el war en el tomcat..."
                    deploy adapters: [tomcat9(credentialsId: 'TomcatCreds', path: '', url: current_profile)], contextPath: 'mi_curso_jenkins', onFailure: false, war: 'target/*.war'
                }
            }
        }
        
        stage("Notification") {
            when {
                expression {
                    !skipRemainingStages
                }
            }

            steps {
                script {
                    println "Notificando el deploy..."
                    /*emailext (
                      subject: "Job completed"
                      body: "Jenkins Pipeline Job for Maven Build got completed"
                      to: "email@email.com"
                    )*/
                }
            }
        }
    }
}
