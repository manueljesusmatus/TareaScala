package app

import java.sql.DriverManager
import java.sql.Connection
import scala.collection.mutable.ListBuffer

class ScalaPersist(pdriver: String, purl: String, pusername: String, ppassword: String ) {
	private var connection:Connection = _
	private val driver = pdriver
	private val url = purl
	private val username = pusername
	private val password = ppassword

	def connectDB(): Unit ={
		try {
			Class.forName(driver)
			connection = DriverManager.getConnection(url,username,password)
		}catch{
			case e: Any => e.printStackTrace()
		}
	}

	def getAll(table:String, colname:String): List[String] ={
		val content = new ListBuffer[String]()
		try{
			val statement = connection.createStatement()
			val query = s"SELECT $colname FROM $table"
			println(s"[INFO] ejecutando : $query")
			val results = statement.executeQuery(query)
			while(results.next()){
				content += results.getString(colname)
			}
		}catch{
			case e: Any => e.printStackTrace()
		}
		content.toList
	}

	private def getById(): Int ={
		try{
			val statement = connection.createStatement()
			val query = s"SELECT LAST_INSERT_ID()"
			val results = statement.executeQuery(query)
			while(results.next()){
				return results.getString("LAST_INSERT_ID()").toInt
			}
		}catch{
			case e: Any =>
				e.printStackTrace()
		}
		return -1
	}

	def create(table:String, tableColumns: List[String], lista: List[String]): Int ={
		try{
			val statement = connection.createStatement()
			val query = s"INSERT into $table( ${tableColumns(0)},${tableColumns(1)},${tableColumns(2)},${tableColumns(3)},${tableColumns(4)} ) values ('${lista(0)}','${lista(1)}','${lista(2).toFloat}','${lista(3).toFloat}','${lista(4)}')"
			println(s"[INFO] ejecutando : $query")
			statement.executeUpdate(query)
			statement.close()
			return getById()
		}catch{
			case e: Any =>
				e.printStackTrace()
				return -1
		}
	}

	def closeDB(): Unit ={
		connection.close()
	}
}