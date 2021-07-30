package app

import javax.jms._
import org.apache.activemq.ActiveMQConnectionFactory

class ActiveMQConsumerHandler(activeMQURL: String, pqueue: String) {
	private var connection:Connection = _
	private val activeMqURL: String = activeMQURL
	private var session:Session = _
	private val queue: String = pqueue
	private var cola: Queue = _
	private var consumidor:MessageConsumer = _

	def connectQueue(): Unit = {
		val cFactory = new ActiveMQConnectionFactory(activeMqURL)
		connection = cFactory.createConnection()
		connection.start()
		this.sessionQueueu()
	}

	def sessionQueueu(): Unit ={
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
		cola = session.createQueue(queue)
		consumidor = session.createConsumer(cola)
	}

	def startQueue(listener: MessageListener): Unit ={
		consumidor.setMessageListener(listener)
	}

	def closeQueue(): Unit ={
		consumidor.close()
		session.close()
		connection.close()
	}
}
