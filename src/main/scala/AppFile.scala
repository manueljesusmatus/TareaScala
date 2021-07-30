package app

import java.io._
import app.{ScalaBuffer, ActiveMQProducerHandler}

object AppFile {
	def main(args: Array[String]): Unit = {
		val urlMQ = System.getenv("URL_MQ")
		val fileName = "test.txt"
		val queueName = "/persist"

		val aux = new ScalaBuffer()
		val mq = new ActiveMQProducerHandler(urlMQ,queueName)
		mq.connectQueue()
		for(linea <- aux.read(fileName)) mq.sendMessageToQueue(linea)
		println(s"[INFO] Lectura terminada")
		mq.closeQueue()
	}
}
