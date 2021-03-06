package app

import java.io._
import app.{ScalaBuffer, ActiveMQProducerHandler}
import com.typesafe.config.ConfigFactory

object AppFile {
	def main(args: Array[String]): Unit = {
		var conf = ConfigFactory.load
		val urlMQ = conf.getString("URL_MQ")
		val fileName = "archivo.csv"
		val queueName = "/persist"

		val aux = new ScalaBuffer()
		val mq = new ActiveMQProducerHandler(urlMQ,queueName)
		mq.connectQueue()
		for(linea <- aux.read(fileName)) mq.sendMessageToQueue(linea)
		println(s"[INFO] Lectura terminada")
		mq.closeQueue()
	}
}
