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
			tableColumns.mkString(",")
			val query = s"INSERT into ${table} ( ${tableColumns.mkString(",")} ) values ('${lista.mkString("','")}') "

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

	def getTextById( qryId:Int, tableName:String ): String = {
		var row: String = ""
		try {
			val statement = connection.createStatement()
			val qry = s"SELECT * FROM $tableName WHERE id=$qryId"
			println(s"[INFO] ejecutando : $qry")
			val result = statement.executeQuery(qry)
			while(result.next()){
				row = s"${result.getString("titles")},${result.getString("platforms")},${result.getString("metascore")},${result.getString("userscore")},${result.getString("genre")}"
			}
			statement.close()
			println(s"[INFO] row : $row")
			return row
		} catch{
			case e => e.printStackTrace()
				return row
		}
	}

	def closeDB(): Unit ={
		connection.close()
	}
}