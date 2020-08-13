# Tagger

## Prerequisite
Tagger requires a running tomcat (tested using 8) and MySQL database
 - Tomcat
 - MySQL
 
## Configuration
Setup your database using the file `dl-mutation-tagging.sql`:

```
cd db

mysql -u<DB_USER_NAME> -p<DB_USER_PASSWORD> < dl-mutation-tagging.sql
```
> This version is different than the one before as the tag column could not hold "longer" tags

Fill up the file: `config.cfg` by providing a value to all the listed properties:

```
tomcat.url=http://localhost:8080/manager/text
tomcat.username=<TOMCAT_USER>
tomcat.password=<TOMCAT_USER>
tomcat.path=/tagger

dbAddress=jdbc:mysql://localhost:3306/dl-mutation-tagging
dbDriver=com.mysql.jdbc.Driver
dbUsername=<DB_USER_NAME>
dbPassword=<DB_USER_PASSWORD>
maxNumberOfEvaluators=1
maxNumberOfEvaluationsPerUser=200
```
> At the moment the empty configuration file is marked as been always unchanged so there should be no risk of committing passwords and other sensitive data to the repo. Check this link if you need to know more about this: 
[https://stackoverflow.com/questions/9794931/keep-file-in-a-git-repo-but-dont-track-changes](https://stackoverflow.com/questions/9794931/keep-file-in-a-git-repo-but-dont-track-changes)

## Build and Deploy

Deploy or re-deploy the application by issuing the following maven command (it might take a while the first time you do it):

`mvn clean compile package install war:war tomcat7:redeploy -DskipTests`

What does it happen during the execution of this command?

Maven clean and recompile the entire project making sure that the values specified in the `config.cfg` file are used to update all the resources/files inside the project (e.g., `context.xml`). Next, it package the entire application as `.war` making sure to include the files for the entities to tag (e.g., `src/main/resources/json` and `src/main/resources/png`) as well as all the other resources (e.g., javascript libraries). Finally, it automatically deploys and start the application inside tomcat (assuming that is running and the given tomcat user/password are correct).

## Inserting the entity data into the DB

Tagger assumes that the entities to tag are stored in the context of the web application under folders names after their type. For example, MNIST images, which are `png` are stored under `<tomcat_root>\png\` while BEAMNG roads, which are `json` are stored under `<tomcat_root>\json\`. The utility classes 
`ImportImagesInTheDb.java` and `ImportBeamNGDataInTheDb.java` can be used to store references to images from cvs files in batches. See the files under `cvs` for examples.
