[![Build Status](https://travis-ci.org/lperrod/web.svg?branch=master)](https://travis-ci.org/lperrod/web) [![Coverage Status](https://coveralls.io/repos/github/lperrod/web/badge.svg?branch=master)](https://coveralls.io/github/lperrod/web?branch=master) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A light application that allows you to create a simple website.  

Technologies :  
Java 8, Spring Boot 2 M7, Thymeleaf 3.0.9, Bootstrap 4, Jquery 3.2.0 , CodeMirror 5.34.0 and FontAwesome

# Configuration
The main configuration file is application.yml  
There you will be able to declare the application name, some log level or format, where is the database and how to connect to it

Please do note that there are several *.properties and you need to change the paths in compliance with your own server

# Launch
Simply run WebLauncher as a java program  
NewsFactory will provide some default blog entries for the website, provided you run WebLauncher with -Dspring.profiles.active=dev as vm arguments 
PageFactory will provide some default values for the website, provided you run WebLauncher with -Dspring.profiles.active=dev as vm arguments  
As for the db, feel free to modify application.yml
By default, it will use an inline h2 db

# Language
Currently only french and english are supported  
The default locale is Locale.FRANCE  
You can change it in LocaleConfiguration  
Feel free to add any Locale you need  
In order to do so, create the corresponding files in the i18n folder and add your locale in BackDisplayFactoryImpl.computeLocales()

# Access
The backoffice url is your_localhost:your_port/manager/*  
The front office url is your_localhost:your_port/pages/your_page_name  
The media url is your_localhost:your_port/public/media/

In order to access to the backoffice you need to create a user in the db and a role with all the privileges and associate the user with the role

#Administration
## Users  
You can create and manage users for the manager here.  
You cannot edit the password of the user.  
But you can add roles
## Roles
You can create and manage roles for the users here.  
### Rights  
This tab's purpose is to define which rights are given to the role

# Website management
## Menu
You can create a menu and any sub menu there and link it to a page
## Media
You can upload and download any kind of file there
## Carousel
You can create a carousel there and link it to a page
## Style
You can configure the stylesheet of the website there
## Blog
You can create blog entries there
## Facebook
You can connect to your Facebook account and import the latest posts and convert them to Blog entries
## Widgets
Widgets are the core of the page content management  
They are the html components that will construct your pages  
You can personalize the widgets by selecting a datasource and modifying its html content in the desired language  
There are many kinds of widgets from plain HTML, to IMAGE (linked to a media), VIDEO (linked to a media), MENU (linked to the menu)  
BLOG (all the blog entries), BLOG_ENTRY (a specific blog entry) and CAROUSEL (linked to a carousel)
There are some macros that you can drag and drop. They are bootstrap 4 components
## Pages
There is a menu where you can create a page with its body, its header and footer, its meta   
You can link widgets to the page or remove said link  
You can decide for which language the body,header,footer,meta will be created with the select
In the editor (from codemirror) you can drag and drop any associated widget  
Please note that the technology used for the templates is Thymeleaf (http://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html)


# Posts from facebook
The application gives the possibility to import the latest 25 posts from a Facebook account  
In order to use this feature, you have to delare your app in Facebook  
This will give you an appId and an appSecret  
You have to store this on your server in a "facebook.json" file and give its path to the application in the facebook.properties file  
Then the user simply has to go to the menu and login into his Facebook account and authorize the app  

# Backup
The application automatically creates a backup once a day of all the files  
The application imports all the data in a backup file on start  
You have the possibility to send a backup of all the files to your googleDrive  
For this, you have to declare the application in a similar fashion to Facebook  
You have to ask for a client secret file  
You have to store this json file on your server and give its path to your application via the google.properties file  
On the first launch, the console will prompt you to go to a certain url. You have to follow the link and accept the app  
This will store some credentials according to the path described in google.properties  

# Deployment
Remember to change the *.properties files and the pom.xml and follow the guidelines described here  
If you wish to deploy as an executable jar, you have to change the pom.xml file by removing the tomcat declaration and by changing war to jar  
Also you will need to change the main class so that it works as a simple springboot application  
If you wish to deploy as a war, follow this :  
https://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html

# SEO
You can congifure the url for the robots.txt by changing the file core.properties "websiteUrl"  
As for the sitemap, it will create as many entries as there are pages and wil use the "websiteUrl" as root url  
For any page, the meta tages can be configured  
The pages are automatically compatible with OpenGraph as the necessary prefix is added to the pages

# Error page
The backoffice error pages are located in back/error/*.properties  
Currently the errors 403,404 and 500 are overwritten  
If you want to customize more codes, simply add a page in the folder back/error/ in the resources  
As for the front error page, simply create a page with the name equivalent to the error code (or you can just manually add a html file in your pages folder)