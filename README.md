# ATMs

## Requirements

For building and running the application you need:

- Java 11
- Gradle 7.3.3

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.lbg.atms.run.AtmsApplication` class from your IDE.

Alternatively you can use gradle command

```shell
./gradlew clean bootRun
```
You can also create a jar and use the below commands to run the application

### Create jar
```shell
./gradlew clean build
```
### Run the application
```shell
$JAVA_HOME/bin/java -jar build/libs/atms-0.0.1.jar
```

## Open API Specification
```shell
http://localhost:8080/swagger-ui.html
```



