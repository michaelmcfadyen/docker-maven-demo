# docker-maven-demo

This project is used to demo how to migrate our functional tests from running locally to runnning in docker containers

There are two key maven plugins that are used to accomplish this. 

## Spotify Docker Maven Plugin
We use this plugin to build a new docker image using our newly built jar as part of the maven package phase.
https://github.com/spotify/docker-maven-plugin

## Fabric8 Docker Maven Plugin
This plugin is used to orchestrate the running of the docker containers needed to functionally test the application.
https://dmp.fabric8.io

## Project Technologies
### App
* Spring Boot
* Jersey
* Cassandra

### Functional Tests
* Cucumber JVM
* Guice
* Cassandra
* Apache Http Client

## How To Run
### Package app as docker image
```mvn clean package```

### Package app and run functional tests
```mvn clean verify ```

### Build image but do not build jar
```cd app; mvn docker:build```

### Run functional tests but do not start up containers
```cd ft; mvn clean verify -Ddocker.skip```

### Start up containers but do not run functional tests
```cd ft; mvn docker:start```

### Stop containers
``` cd ft; mvn docker:stop```

### Remove Containers
```cd ft; mvn docker:remove```

## TODO
- [ ] integrate docker maven plugins
