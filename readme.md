# Tarea Taller de programación
## Grupo 1
* Natalia Herrera
* Gonzalo Fernández
* Matías Guzmán
* Manuel Matus
* Ándres Shehadeh

## Dependencias
[![activemq](https://img.shields.io/badge/activemq--all-5.16.2-blue)](https://activemq.apache.org/components/classic/download/) [![mysql-connector](https://img.shields.io/badge/mysql--connector-5.1.45-blue)](https://downloads.mysql.com/archives/installer/) [![config](https://img.shields.io/badge/com.typesafe.config-1.4.1-blue)](https://github.com/lightbend/config)
* Navegar a `TareaScala/src/main/resources` y renombrar el archivo `application.conf.example` por `application.conf`, luego configurar los datos de la cola ActiveMQ y la Base de Datos
* En la carpeta `TestScala/jars` están los dos jars utilizados

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

### Script para generar tablas y database
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

## Ejecución
Los programas desarrollados buscan persistir y filtrar datos contenidos en un `archivo.csv`, el cuál contiene las calificaciones a diversos juegos en las distintas plataformas que están disponibles (PC, PS4, XBOXone, Switch, etc)

1. El programa `AppFile` realiza la lectura de archivos basadas en buffer y encola la data.
2. El programa `AppPersist` consume la data de `AppFile`, persiste los datos en DB y publica que existe nueva data en la DB.
3. El programa `AppConsumer` lee los datos en DB y los encola para ser consumidos.
4. El programa `AppHost` es la aplicación distribuible la cual filtra la data publicada por `AppConsumer` y solo persiste aquellos que pertenecen a la categoría `Switch`
