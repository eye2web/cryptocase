# Crypto currency casus

### Project
- OpenJDK Java 11
- Spring boot 2.3.1
- Gradle
- Eye2web Modelmapper for DTO / domain decoupling
- Flyway for data migration
- OpenApi Spec generation

### Start application
```bash
./gradlew bootRun
```
Application will be available on http://localhost:8080

For testing purposes a Postman collection can be found in the root of this project. (./Crypto_currency.postman_collection.json)

Generated OpenApi Spec can be found here: http://localhost:8080/swagger-ui.html