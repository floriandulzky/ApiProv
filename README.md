# ApiProv

ApiProv is lightweight Api Gateway or something you can provide APIs with. 

* Authenticate / Authorize your APIs
* Set rate limiting to your APIs
* Provide APIs from different protocols
* Use as an api gateway for your microservices
* Use as loadBalancer for API-Request or/and for your microservices

## Run ApiProv
### Docker
...
### From Source
* install mongo DB ( `docker run --name apiprov-mongo -27017:27017 mongo` )
* Fork / download Repo
* Change direcotry to "ApiProv-Source/main" ( `cd ApiProv-Source/main` )
* run `./mvnw quarkus:dev` to run the quarkus aplication in development mode.

## Main Modules
* Pre Filter
  * Api Token Authentication
* Router
  * REST Router
  * Fix Value Router
  * Remote-Shell script Router
* Post Filter

## Architecture
![Image of Yaktocat](https://github.com/floriandulzky/ApiProv/blob/master/Documentation/ApiProvArchitecture.png?raw=true)

## How to implent a Plugin
...

## Contribute
...
