# Tarea Taller de programación

## Dependencias

* Crear Archivo `build.sbt` y agregar todas las dependencias del projecto (jars, scala-version, etc)
* En la carpeta `TestScala/jars` están los dos jars que utilicé
### Ejemplo de archivo `build.sbt`
```prolog
name := "TareaScala"
version := "0.1"
scalaVersion := "3.0.1"
idePackagePrefix := Some("app")

fork in Test:= true
envVars in Test:= Map("URL_MQ" -> "tcp://localhost:61616",
	"DRIVER" ->"com.mysql.jdbc.Driver",
	"URL_DB" -> "jdbc:mysql://localhost:3306/taller",
	"USERNAME" -> "root",
	"PASSWORD" -> "password"
)

libraryDependencies += "org.apache.activemq" % "activemq-all" % "5.16.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.45"
```
## Database

### Script para generar tabla y database
```sql
CREATE DATABASE IF NOT EXISTS `taller`;
USE `taller`;

CREATE TABLE `games` (
  `id` int(11) NOT NULL,
  `titles` varchar(255) NOT NULL,
  `platforms` varchar(255) NOT NULL,
  `metascore` float NOT NULL,
  `userscore` float NOT NULL,
  `genre` varchar(255) NOT NULL
);

ALTER TABLE `games` ADD PRIMARY KEY (`id`);
ALTER TABLE `games` MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;
```
