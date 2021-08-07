# Tarea Taller de programación

## Dependencias

* Navegar a `TareaScala/src/main/resources` y renombrar el archivo `application.conf.example` por `application.conf`, luego configurar los datos de la cola ActiveMQ y la Base de Datos
* En la carpeta `TestScala/jars` están los dos jars que utilicé

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
