# TwitterApp

## Required Programs/Installations
Java JDK, Twitter Account, Twitter4j (http://twitter4j.org/en/index.html)

## Getting Started
After creating a twitter account, twitter.properties would require some twitter authentication values. These can be found when making a new Twitter application (https://apps.twitter.com). Here create a new application and under the "Keys and Access Tokens" would be all 4 authentication values.

## Compiling: 
In java Directory:
```
javac -sourcepath twitterapp -cp ../../../lib/twitter4j-core-4.0.4.jar twitterapp/src/*.java 
```
## Running:
```
java -cp .:../../../lib/twitter4j-core-4.0.4.jar twitterapp.src.Main
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
cd target/
java -jar TwitterAppTest-1.0.jar
```
## Running using Dropwizard
Install the latest Dropwizard. 
```
mvn clean package && java -jar target/TwitterApp-1.0-SNAPSHOT.jar server
```
To post a new tweet, in a different terminal while the server is running
```
curl -X POST -d 'insert new tweet here' localhost:8080/api/1.0/twitter/tweet
```
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
