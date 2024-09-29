## Building the project

1. set JAVA_HOME variable to valid JDK path
2. run following command from inside parent directory (where `pom.xml` file is located)
```
mvnw clean install
```
3. once build is completed, check `target` folder for each module and find the build jars
```
-- booking-service
-- code-coverage
-- shared
-- taxi-agent
```
4. Jar available at `booking-service/target/booking-service-*.jar` is uber jar for server program
5. Jar available at `taxi-agent/target/taxi-agent-*.jar` is uber jar for client program
6. Ignore other jars created for `shared` and `code-coverage` module
7. Total code coverage report is available under `code-coverage/target/site/jacoco-aggregate/index.html`

## Running the server program


## Running the client program