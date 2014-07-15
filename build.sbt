organization := "com.evantapp"

name := "rest"

version := "0.1.-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions := Seq("-unchecked","-Xlint","-deprecation","-encoding","utf8","-Ywarn-dead-code","-language:_","-feature")

resolvers ++= Seq(
  "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe release"   at "http://repo.typesafe.com/typesafe/releases/",
  "typesafe repo"      at "http://repo.typesafe.com/typesafe/repo/",
  "typesafe snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "maven central"      at "http://repo1.maven.org/maven2/",
  "akka repo"          at "http://repo.akka.io/",
  "akka snapshots"     at "http://repo.akka.io/snapshots",
  "spray repo"         at "http://repo.spray.io/"
)

val scalaTesting = Seq(
  "org.specs2"        %% "specs2"        % "2.3.11" % "test" withSources(),
  "org.scalatest"     %% "scalatest"     % "2.2.0"  % "test" withSources(),
  "com.typesafe.akka" %% "akka-testkit"  % "2.3.4"  % "test" withSources(),
  "io.spray"          %% "spray-testkit" % "1.3.1"  % "test" withSources()
)

libraryDependencies ++= {
  val sprayV = "1.3.1"
  val akkaV = "2.3.4"
  Seq(
    "org.slf4j"         %  "slf4j-simple"  % "1.7.2",
    "io.spray"          %% "spray-can"     % sprayV  withSources(),
    "io.spray"          %% "spray-routing" % sprayV  withSources(),
    "org.json4s"        %% "json4s-native" % "3.2.9",
    "com.typesafe.akka" %% "akka-actor"    % akkaV withSources(),
    "com.typesafe.akka" %% "akka-slf4j"    % akkaV withSources()
  ) ++ scalaTesting
}

packageArchetype.java_application

Seq(Revolver.settings: _*)

