# Tarea Taller de programación

## Dependencias

* Crear Archivo `build.sbt` y agregar todas las dependencias del projecto (jars, scala-version, etc)
* Crear carpeta `TareaScala/src/main/resources` y Archivo `application.conf`
* En la carpeta `TestScala/jars` están los dos jars que utilicé
### Ejemplo de archivo `build.sbt`
```prolog
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
```
### Ejemplo de archivo `TareaScala/src/main/resources/application.conf`
```javascript
{
    URL_MQ="tcp://localhost:61616",
    DRIVER="com.mysql.jdbc.Driver",
    URL_DB="jdbc:mysql://localhost:3306/taller",
    USERNAME="root",
    PASSWORD="password"
 }
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

CREATE TABLE `switch_games` (
  `id` int(11) NOT NULL,
  `metascore` float NOT NULL,
  `userscore` float NOT NULL,
  `genre` varchar(255) NOT NULL,
  `titles` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `switch_games`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `switch_games`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

```
