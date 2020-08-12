# tagger

 Prerequisite:
 Tomcat / JSP & Servlet / MySQL
 
 Settings in Utilities.java:
 
```	
	dbAddress
	dbUsername
	dbPassword
	maxNumberOfEvaluators
	maxNumberOfEvaluationsPerUser
```

Fillup the file: `config.cfg` with the required properties and then call the following command to automatically rebuild and redeploy the application in your (running) tomcat:

`mvn clean compile package install war:war tomcat7:redeploy -DskipTests`

