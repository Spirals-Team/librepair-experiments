# playSpring [![Build Status](https://travis-ci.org/Prussia/playSpring.svg?branch=master)](https://travis-ci.org/Prussia/playSpring)
Personal Project to practice and record the examples on Springboot
###Feature
- *SpringMVC*
- *Spring Security*  
- *Validation Bean* 
- *Customized Spring properties*
- *Ehcache implementation* 
- *JDBCTempldate & SQL Manager* 
- *Mutiple Data Source JNDI Implementation for mssql/neo4j*
- *lombok*
- *i18n samples*
- *logback with Asyncrous Appender*
- *Spring Test - web integration test*
- *Swagger* 
	* annotation @apioperation
- *Performance Monitor* 
- *gzip compression in tomcat to improve the performance of front-end*
- *Spring Data JPA & Redis*
- *dozer to transfer from PO to VO* 
- Groovy Template Engine
- Validation Constraint

###Continuous Integration
- [travis-ci](https://travis-ci.org/)
	- [db setup](https://docs.travis-ci.com/user/database-setup/)
	- built on Postgres
- [Heroku Deployment by travis](https://docs.travis-ci.com/user/deployment/heroku/)
	- [playSpring on Heroku](https://playspring.herokuapp.com)
	- [Settings for playSpring](https://dashboard.heroku.com/apps/playspring)

###DevOps
- [docker-compose.yml](https://github.com/Prussia/playSpring/tree/master/container/playSpring)

###Dev Environment
- [Chocolatey - Software Management Automation](https://chocolatey.org/install)

##**TODO**
- Spring Cloud
- [psi-probe](https://github.com/psi-probe/psi-probe/releases) - Advanced manager and monitor for Apache Tomcat	
- Deployment
	* [Heroku](https://docs.travis-ci.com/user/deployment/heroku/)
		- [Heroku data](https://data.heroku.com/)
- [JWT](https://jwt.io/introduction/) - JSON Web Token - used for **Information Exchange** and Authentication
	- JSON Web Encryption [JWE]
	- JSON Web Signature [JWS]
	- [Oracle JSON Web Token](https://docs.oracle.com/cd/E23943_01/security.1111/e10037/jwt.htm#CIHGDBJC)
	- [Web Application Security](http://enterprisewebbook.com/ch9_security.html)
- **Single Sign On**
	- [Authentication and Authorization: OpenID vs OAuth2 vs SAML](https://spin.atomicobject.com/2016/05/30/openid-oauth-saml/)
	- [Spring Oauth2](https://projects.spring.io/spring-security-oauth/)
- Dockerfile
	* [Docker Swarm](https://www.docker.com/products/docker-swarm)
	* [Docker Universal Control Plane](https://docs.docker.com/datacenter/ucp/2.0/guides/)
	* [Portainer](http://portainer.io/) - UI for Docker and better management
- [Morphia](https://mongodb.github.io/morphia/) -- The Java Object Document Mapper for MongoDB 
- Websocket         
- Spring Integration
