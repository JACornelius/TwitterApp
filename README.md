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
cd target/
java -jar TwitterAppTest-1.0-SNAPSHOT.jar
```
