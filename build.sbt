organization in ThisBuild := "com.airtonjal"

name := "rest-spray-akka"

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers ++= Seq(
  "Geotools" at "http://download.osgeo.org/webdav/geotools/",
  "spray repo" at "http://repo.spray.io",
  "JCenter" at "http://jcenter.bintray.com/",
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "sonatype" at "https://oss.sonatype.org/content/repositories/releases"
)

val sprayVersion = "1.3.3"
val slickVersion = "3.1.0"
val logbackVersion = "1.1.3"
val scalaLoggingVersion = "2.1.2"
val postgresqlVersion = "9.4-1206-jdbc41"
val postgisVersion = "1.3.3"
val gdalVersion = "2.0.0"
val akkaVersion = "2.3.12"
val scalatestVersion = "2.2.5"


libraryDependencies ++= Seq(
  "io.spray" %% "spray-can" % sprayVersion,
  "io.spray" %% "spray-http" % sprayVersion,
  "io.spray" %% "spray-httpx" % sprayVersion,
  "io.spray" %% "spray-routing" % sprayVersion,
  "com.gettyimages" %% "spray-swagger" % "0.5.1",
  "io.spray" %% "spray-testkit" % sprayVersion % "test",
  "org.scalatest" %% "scalatest" % scalatestVersion % "test",
//  "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
//  "org.specs2" %% "specs2" % "2.4.17" % "test",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-codegen" % slickVersion,
  "org.postgresql" % "postgresql" % postgresqlVersion,
  "com.zaxxer" % "HikariCP-java6" % "2.3.7",
//  "org.postgis" % "postgis-jdbc" % postgisVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "org.clapper" %% "grizzled-slf4j" % "1.0.2",
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.json4s" %% "json4s-native" % "3.2.11"
)

Revolver.settings.settings

mainClass in (Compile, run) := Some("com.airtonjal.rest.Main")
mainClass in Revolver.reStart := Some("com.airtonjal.rest.Main")
