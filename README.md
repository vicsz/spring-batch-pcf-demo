# Super Simple Spring Batch Demo Example

Reads from input.csv and outputs to output.txt.

No job status persistence. 

## To Run Locally 

```sh
mvn spring-boot:run
```


## PCF Steps 

### 1. Build the Spring Batch App

```sh
mvn package
```

### 2. Deploy to PCF

```sh
cf push batch-demo --no-route -i 0  -p target/batch-demo-1.0.jar 

```

### 3. Run a Spring Batch Task

```sh
cf run-task batch-demo ".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher"
```

### 4. Verify Spring Batch Task for executed successfully 

```sh
cf tasks batch-demo
```

Or 

```sh
cf logs --recent batch-demo
```