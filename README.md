# TwitterApp

## Required Programs/Installations
Java JDK, Twitter Account, Twitter4j (http://twitter4j.org/en/index.html)

## Getting Started
After creating a twitter account, twitter.properties would require some twitter authentication values. twitter.properties file is in the src/main/java directory and the TwitterApp directory. The file should look like this
```
debug = true
oauth.accessToken =
oauth.consumerSecret = 	
oauth.consumerKey =
oauth.accessTokenSecret = 
```
These can be found when making a new Twitter application (https://apps.twitter.com). Here create a new application and under the "Keys and Access Tokens" would be all 4 authentication values.

## Compiling: 
In java Directory:
```
javac -sourcepath twitterapp -cp ../../../lib/twitter4j-core-4.0.4.jar twitterapp/src/*.java 
```

## Running:
```
java -cp .:../../../lib/twitter4j-core-4.0.4.jar twitterapp.src.MainFile
```

## Creating a JAR File:
In the java Directory:
```
jar cfm TwitterApp.jar twitterapp/src/META-INF/MANIFEST.MF twitterapp/src/*.class ../../../twitter4j.properties
java -jar TwitterApp.jar
```

## Creating a JAR File using Maven
Install maven and run in TwitterApp directory
```
mvn clean install
```
Add the completed twitter.properties file into this newly created target directory
```
cd target/
java -jar TwitterAppTest-1.0-SNAPSHOT.jar
```

## Running using Dropwizard
Install the latest Dropwizard. 
```
mvn clean package && java -jar target/TwitterApp-1.0-SNAPSHOT.jar server
```
To post a new tweet, in a different terminal while the server is running
```
curl -d'{"name":"mojo", "message":"insert new tweet here"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/1.0/twitter/tweet

```
To post a new tweet with additional information
Timeline can be viewed at http://localhost:8080/api/1.0/twitter/timeline

## Unit Testing with mockito
To test all unit tests:
```
mvn test
```
To test a particular test:
```
mvn -Dtest=ResourceTest#<particular test> test
```

## Using Jacoco for Code Coverage
In terminal run:
```
mvn install jacoco:report
```
To review code report coverage open index.html file inside TwitterApp/target/site/jacoco/twitterapp.src/index.html.

## Incorporating config.yml file
Create a new file named config.yml inside the TwitterApp folder and include all the tokens and secrets, as so:
```
twitter:
    accessToken: <Access Token>
    accessTokenSecret: <Access Token Secret>
    consumerSecret: <Consumer Secret>
    consumerKey: <Consumer Key>
logging:
  level: INFO
  loggers:
    "myLogger":
      level: INFO
      additive: false
      appenders:
        - type: console
          logFormat: "%d{HH:mm:ss.SSS} [%thread]: %message%n %ex{full}"
```
To run and compile:
```
mvn clean install
java -jar target/TwitterApp-1.0-SNAPSHOT.jar server config.yml
```