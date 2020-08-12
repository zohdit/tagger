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

> At the moment the empty configuration file is marked as been always unchanged so there should be no risk of committing passwords and other sensitive data to the repo. Check this link for more 
[https://stackoverflow.com/questions/9794931/keep-file-in-a-git-repo-but-dont-track-changes](https://stackoverflow.com/questions/9794931/keep-file-in-a-git-repo-but-dont-track-changes)
