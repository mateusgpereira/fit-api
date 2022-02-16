<a href="https://quarkus.io/ ">
  <img src="https://quarkus.io/assets/images/quarkus_logo_horizontal_rgb_600px_reverse.png" alt="Logo">
</a>  
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
