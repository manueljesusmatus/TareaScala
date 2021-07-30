package app

import javax.jms._
import app.{ScalaPersist, ActiveMQConsumerHandler, ActiveMQProducerHandler}
import com.typesafe.config.ConfigFactory

object AppPersist {
	def main(args: Array[String]): Unit = {
		var conf = ConfigFactory.load
		val urlMQ = conf.getString("URL_MQ")
		val driver = conf.getString("DRIVER")
		val urlDB = conf.getString("URL_DB")
		val username = conf.getString("USERNAME")
		val password = conf.getString("PASSWORD")
		val queueName = "/persist"
		val queueNamePub = "/processed"
		val listaTableColumns: List[String] = List("titles","platforms","metascore","userscore","genre")

		val db = new ScalaPersist(driver,urlDB,username,password)
		db.connectDB()
		val mq = new ActiveMQConsumerHandler(urlMQ, queueName)
		mq.connectQueue()
		val mqpub = new ActiveMQProducerHandler(urlMQ, queueNamePub)
		mqpub.connectQueue()

		val listener = new MessageListener {
			def onMessage(msg: Message): Unit ={
				msg match {
					case text: TextMessage =>
						val lista: List[String] = text.getText.split(",").map(_.trim).toList
						val id = db.create("games", listaTableColumns, lista)
						mqpub.sendMessageToQueue(id.toString)
						println(s"[DEBUG] ${id.toString}")
					case _ =>
						throw new Exception("Error")
				}
			}
		}
		mq.startQueue(listener)
	}
}
