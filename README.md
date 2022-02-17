<div style="display: inline_block">
    <img align="center" alt="Mateus-Spring" height="30" width="40" src="https://design.jboss.org/quarkus/logo/final/PNG/quarkus_icon_rgb_64px_default.png"/>
    <img align="center" alt="Mateus-Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg"/>
    <img align="center" alt="Mateus-Postgres" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg"/>
</div>
<br>

# Fit-system App

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Development

To run the app in dev environment, first start the PG container with the command:

```shell script
docker-compose up -d
```  

Then run the following quarkus cli:

```shell script
quarkus dev
```
> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.  

## Tests

Tests on this application are been developed with JUnit, Rest Assured and Mockito.  
To run the tests we just use this command:

```shell script
mvn test
```

To run a single Test Suite just use the following command replacing the placeholder  
TEST_CLASS for   the Test Suite you want to run:
```shell script
 mvn -Dtest=TEST_CLASS test
```

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/fit-system-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
