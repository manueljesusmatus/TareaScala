package app

import javax.jms._
import app.{ScalaPersist, ActiveMQConsumerHandler, ActiveMQProducerHandler}
import com.typesafe.config.ConfigFactory

object AppConsumer {
	def main(args: Array[String]): Unit = {
		var conf = ConfigFactory.load
		val urlMQ = conf.getString("URL_MQ")
		val driver = conf.getString("DRIVER")
		val urlDB = conf.getString("URL_DB")
		val username = conf.getString("USERNAME")
		val password = conf.getString("PASSWORD")
		val queueName = "/extracted"
		val queueNamePub = "/processed"

		val db = new ScalaPersist(driver,urlDB,username,password)
		db.connectDB()
		val mqpub = new ActiveMQConsumerHandler(urlMQ, queueNamePub)
		mqpub.connectQueue()
		val mq = new ActiveMQProducerHandler(urlMQ, queueName)
		mq.connectQueue()

		val listener = new MessageListener {
			def onMessage(msg: Message): Unit ={
				msg match {
					case text: TextMessage =>
						println(s"[DEBUG] ${text.getText()}")
						val row = db.getTextById(text.getText().toInt, "games")
						mq.sendMessageToQueue(row)
					case _ =>
						throw new Exception("Error")
				}
			}
		}
		mqpub.startQueue(listener)
	}
}
