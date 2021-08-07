package app

import javax.jms._
import app.{ScalaPersist, ActiveMQConsumerHandler, ActiveMQProducerHandler}
import com.typesafe.config.ConfigFactory

object Host {
  def main(args: Array[String]): Unit = {
    var conf = ConfigFactory.load
    val urlMQ = conf.getString("URL_MQ")
    val driver = conf.getString("DRIVER")
    val urlDB = conf.getString("URL_DB")
    val username = conf.getString("USERNAME")
    val password = conf.getString("PASSWORD")
    val queueName = "/extracted"
    val listaTableColumns: List[String] = List("id", "metascore", "userscore", "genre", "titles") 
    val db = new ScalaPersist(driver,urlDB,username,password)
    db.connectDB()
    val mq = new ActiveMQConsumerHandler(urlMQ, queueName)
    mq.connectQueue()

    val listener = new MessageListener {
      def onMessage(msg: Message): Unit ={
        msg match {
          case text: TextMessage =>
            val lista: List[String] = text.getText.split(",").map(_.trim).toList
            println(lista)
            //val id = db.create("switch_games", listaTableColumns, lista)
            //println(s"[DEBUG] ${id.toString}")
          case _ =>
            throw new Exception("Error")
        }
      }
    }
    mq.startQueue(listener)
  }
}
