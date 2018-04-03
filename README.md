# HelloFib
A simple backend demonstrating basic REST features, fibonacci sequence analysis and java threadlock detection. 

Prerequisites:
maven
jdk 1.8

git clone https://github.com/JoshuaEDeFord/HelloFib.git
cd HelloFib
mvn install exec:java

curl -X GET "http://localhost:8181/api/helloWorld" -H "accept-language: fr-CH"
