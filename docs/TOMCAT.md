# Deploy .WAR in Tomcat
## Generate .WAR file

To generate the `.war` file neccesary for the deployement of the app in Tomcat, issue the next command from the 
terminal:
```
mvn clean package
```

This will generate the file in `FP-PSP-SERVER/target`

## Deployement of the WAR file
The easiest way to deploy our WAR file is to open the Tomcat Web Application Manager in our browser, by default it should be in 
`localhost:8080/manager/html`. It will prompt you the enter the your user and password. In case you haven't set an user and password
yet do as follow:

Open `tomcat-users.xml` located in `<TOMCAT_HOME>/conf/`.

Add the following lines:
```
<role rolename="manager-gui"/>
<user username="username" password="password" roles="manager-gui"/>
```

In the Tomcat Web Application Manager, just look for "WAR file to deploy", select the WAR file that you created and click deploy.
This will deploy the app in `localhost:8080/manager/<your_war_file>`
