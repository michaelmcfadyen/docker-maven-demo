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

### Funtional Tests
* Cucumber JVM
* Guice
* Cassandra
* Apache Http Client
