# TwitterApp

## Required Programs/Installations
Java JDK, Twitter Account, Twitter4j (http://twitter4j.org/en/index.html)
##Getting Started
After creating a twitter account, twitter.properties would require some twitter authentication values. These can be found when making a new Twitter application (https://apps.twitter.com). Here create a new application and under the "Keys and Access Tokens" would be all 4 authentication values.
## Compiling: 
```
javac -sourcepath twitterapp -cp lib/twitter4j-core-4.0.4.jar twitterapp/src/Project1.java twitterapp/src/Project2.java twitterapp/src/main.java
```
## Running:
```
java -cp .:lib/twitter4j-core-4.0.4.jar twitterapp.src.main
```
## Creating a JAR File:
In the java Directory:
```
jar cfm TwitterApp.jar twitterapp/src/META-INF/MANIFEST.MF twitterapp/src/*.class ../../../twitter4j.properties
java -jar TwitterApp.jar

```
