package app

import javax.jms._
import org.apache.activemq.ActiveMQConnectionFactory

class ActiveMQProducerHandler(activeMQURL: String, pqueue: String) {
	private var connection:Connection = _
	private val activeMqURL: String = activeMQURL
	private var session:Session = _
	private val queue: String = pqueue
	private var cola: Queue = _
	private var prod:MessageProducer = _

	def connectQueue(): Unit = {
		val cFactory = new ActiveMQConnectionFactory(activeMqURL)
		connection = cFactory.createConnection()
		connection.start()
		this.sessionQueueu()
	}

	def sessionQueueu(): Unit ={
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
		cola = session.createQueue(queue)
		prod = session.createProducer(cola)
	}

	def sendMessageToQueue( message: String): Unit ={
		val txtMessage = session.createTextMessage(message)
		prod.send(txtMessage)
		println(s"[SEND] mensaje : ${txtMessage.getText}")
	}

	def closeQueue(): Unit ={
		prod.close()
		session.close()
		connection.close()
	}
}
