# rest-spray-akka
A backend REST server using [spray.io](http://spray.io) and [Akka](http://akka.io/)

## Building, testing and running

The application uses Scala's [SBT](http://www.scala-sbt.org/) tool. To build it, cd to the project root dir and type:

```shell
sbt package
```

To run the application and start the REST server:

```shell
sbt run
```

To run tests (implemented with [ScalaTest](http://scalatest.org/)):

```shell
sbt test
```

## Dependencies

- [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) 7
- [Scala](http://www.scala-lang.org/) 2.11.7
- [SBT](http://www.scala-sbt.org/) 0.13.8
- [Spray.io](http://spray.io/) 1.3.2
- [Akka](http://akka.io/) 2.3.12
- [ScalaTest](http://scalatest.org/) 2.2.5

## REST Services

Services are invoked through http requests. The data format is [JSON](http://json.org/). The following table describes the services:

| Service | Description                          | Path                   | Method |
|---------|--------------------------------------|------------------------|--------|
| List    | Gets all projects                    | /project/list          | GET    |
| Add     | Deletes and existing project         | /project/delete/${pid} | DELETE |
| Fraud   | Adds a new project                   | /project/new           | PUT    |

