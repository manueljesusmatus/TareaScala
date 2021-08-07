val scala3Version = "3.0.1"
lazy val root = project
  .in(file("."))
  .settings(
    name := "TareaScala",
    version := "0.1.0",
    scalaVersion := scala3Version,
    idePackagePrefix := Some("app"),

    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.45",
    libraryDependencies += "org.apache.activemq" % "activemq-all" % "5.16.2",
    libraryDependencies += "com.typesafe" % "config" % "1.4.1"
  )