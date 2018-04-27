# bilan-energie

## Présentation de "Bilan Energie"

L'application "Bilan Energie" est une application de gestion et d’analyse des consommations des différents biens (bâtiments et éclairage public), sur la base des informations reçue des concessionnaires et autres organismes facturant la Ville de Nouméa.

## Objectif

Ce projet a pour but de permette de fournir les métriques liées à l'efficacité énergétique des Infrastructures de la Ville de Nouméa, et ce en faisant le lien entre les consommations (factures eau et électricité).
Ce projet est né de l'obligation pour les collectivités de fournir à la DIMENC les métriques liées à leur efficacité énergétique (isolation thermique, conso électricté, eau, fuites, ...)

## Construction du livrable

Placez vous dans le projet ou ce trouve le fichier <pom.xml>. Puis exécuter la commande

    mvn install

Cette commande créera le war déployable.

## Déploiement

 Il existe 2 éléments à déployer.
  * La base de données à travers liquibase (projet privé bilan-energie-liquibase)
  * L'application à travers tomcat

### La base de données

Le répertoire <src/main/db/liquibase> contient les scripts de création base de données, du modèle et d'intégration des données. Vous trouverez dans ce répertoire :

* le répertoire dba qui contient les scripts de création du schméa de bases de données ainsi que la mise en place des utilisateurs et droits
	* create-database.xml
	* db-changelog.xml
	* liquibase.properties

* le répertoire bilan qui contient les scripts de création du modèle de l'application "Bilan Energie" ainsi que les données liés à l'initialisation de l'application et la reprise d'historique
	* create-database.xml
	* db-changelog.xml
	* liquibase.properties

* le répertoire data qui contient les données chargées dans le modèle

    Pour déployer la base de données manuellement, effectuer les actions suivantes :
	* Pré-requis :

   	* Liquibase est installé sur le poste de déploiement : {{http://www.liquibase.org/}}
	* la base de données "bilan" est préalablement créée
	* Dans le répertoire dba, lancer la commande : liquibase update
	* Dans le répertoire bilan, lancer la commande : liquibase update
	* Dans le répertoire qrtz, lancer la commande : liquibase update

### L'application Java

Pour déployer l'application Java, vous devez :
* Définir la ressource JNDI <jdbc/bilan> (Données de l'application Bilan Energie) sur le serveur d'application - fichier context.xml

  Exemple :

        <Resource name="jdbc/bilan" auth="Container" type="javax.sql.DataSource"
		username="bilan_adm" password="changeme" driverClassName="org.postgresql.Driver"
		url="jdbc:postgresql://127.0.0.1:5432/bilan" maxActive="20"
		maxIdle="10" validationQuery="select 1" />

* Définir la ressource JNDI <jdbc/bilan_qrtz> (Données des jobs Quartz) sur le serveur d'application - fichier context.xml

  Exemple :

		<Resource name="jdbc/bilan_qrtz" auth="Container" type="javax.sql.DataSource"
		username="bilan_qrtz_adm" password="changeme" driverClassName="org.postgresql.Driver"
		url="jdbc:postgresql://127.0.0.1:5432/bilan" maxActive="20"
		maxIdle="10" validationQuery="select 1" />

* Définir la ressource JNDI <jdbc/adresse> (Données référentielles des adresses) sur le serveur d'application - fichier context.xml

    Exemple :

		<Resource name="jdbc/adresse" auth="Container" type="javax.sql.DataSource"
		username="adresse_consult" password="changeme" driverClassName="org.postgresql.Driver"
		url="jdbc:postgresql://127.0.0.1:5432/adresse" maxActive="20"
		maxIdle="10" validationQuery="select 1" />

* Définir les paramètres de sécurité sur le serveur d'application - fichier context.xml

	Exemple :

		<!-- Déclaration d’un classpath virtuel afin de pouvoir centraliser les fichiers de configuration de sécurité spring -->
		<Loader className="org.apache.catalina.loader.VirtualWebappLoader" virtualClasspath="<TOMCAT_HOME>\conf"/>

		<!-- Déclaration de la variable "spring.security.bilan" précisant le chemin (dans le classpath) et le nom du fichier de configuration de la sécurité  -->
		<Environment name="spring.security.bilan" value="classpath:bilan-spring-security.xml" type="java.lang.String"/>

* Définir le mécanisme de sécurité sur le serveur d'application : Créer un fichier conf\bilan-spring-security.xml

    Exemple :

        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:p="http://www.springframework.org/schema/p"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:context="http://www.springframework.org/schema/context"
                xmlns:tx="http://www.springframework.org/schema/tx"
                xsi:schemaLocation="
                       http://www.springframework.org/schema/beans
                       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                       http://www.springframework.org/schema/context
                       http://www.springframework.org/schema/context/spring-context-3.0.xsd
                       http://www.springframework.org/schema/tx
                       http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">
            <bean id="delegateAuthenticationProvider"
                class="org.springframework.security.ldap.authentication.LdapAuthenticationProvider">
             <constructor-arg>
               <bean class="org.springframework.security.ldap.authentication.BindAuthenticator">
                 <constructor-arg ref="contextSource"/>
                 <property name="userSearch" ref="userSearch" />
               </bean>
             </constructor-arg>
            </bean>

            <bean id="userSearch"
                class="org.springframework.security.ldap.search.FilterBasedLdapUserSearch">
              <constructor-arg index="0" value="xxxxx"/>
              <constructor-arg index="1" value="(sAMAccountName={0})"/>
              <constructor-arg index="2" ref="contextSource" />
              <property name="searchSubtree" value="true" />
            </bean>

            <bean id="contextSource"
                    class="org.springframework.security.ldap.DefaultSpringSecurityContextSource">
              <constructor-arg value="xxxxx"/>
              <!-- Completer avec un user dedie, a discretion de l'infra, c'est le user qui a le droit de lire l'AD -->
              <property name="userDn" value="xxxxx"/>
              <property name="password" value="xxxxx"/>
            </bean>
        </beans>

* Déployer le war à l'aide de la console d'administration de votre serveur d'application. Pour plus d'information, reportez vous à la doc de votre serveur d'application.

### Paramétrage de l'application

* Configuration des batchs Quartz

	L'application lance 2 jobs Quartz : la synchronisation des adresses et l'intégration des factures.

	Le lancement de 2 jobs est paramétrable dans la table qrtz_cron_triggers du schéma bilan_qrtz_adm

	Exemple de programmation :

	* Synchronisation des adresses du lundi au vendredi à 12h00 : cron_expression = 0 0 12 ? * MON-FRI

	* Intégration des factures du lundi au vendredi toutes les heures de 6h00 à 19h00 : cron_expression = 0 0 6-19 ? * MON-FRI

* Configuration des logs de l'application

	Les logs de l'application sont paramétrables dans le fichier ~Tomcat\webapps\bilan\WEB-INF\classes\logback.xml

	Ce paramétrage permet de définir la stratégie des logs pour :

	* l'ensemble de l'application

	* le job de synchronisation des adresses

	* le job d'intégration des factures

	Exemple de configuration :

        <property name="LOGS_FOLDER" value="${catalina.base}/logs" />

        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n</pattern>
            </encoder>
            <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
                <level>WARN</level>
            </filter>
        </appender>

        <appender name="SYNCHRO-ADRESSE" class="ch.qos.logback.core.FileAppender">
            <file>${LOGS_FOLDER}/synchro_adresse.log</file>
            <append>true</append>
            <encoder>
                <pattern>%date{ISO8601} %-5level %logger{35} - %msg %n</pattern>
            </encoder>
        </appender>

        <appender name="INTEGRATION-FACTURE" class="ch.qos.logback.core.FileAppender">
            <file>${LOGS_FOLDER}/integration_facture.log</file>
            <append>true</append>
            <encoder>
                <pattern>%date{ISO8601} %-5level %logger{35} - %msg %n</pattern>
            </encoder>
        </appender>

        <logger name="integrationFacture" level="INFO" additivity="false">
            <appender-ref ref="INTEGRATION-FACTURE" />
        </logger>

        <logger name="synchroAdresse" level="INFO" additivity="false">
            <appender-ref ref="SYNCHRO-ADRESSE" />
        </logger>

        <root>
            <appender-ref level="ERROR" ref="STDOUT" />
        </root>