package app

import scala.collection.mutable.ListBuffer

class ScalaBuffer() {
	def read(file_name: String):ListBuffer[String] = {
		val bufferedSource = io.Source.fromFile(file_name)
		var lista_juegos=new ListBuffer[String]()
		for (line <- bufferedSource.getLines) {
			val cols = line.split(",").map(_.trim)
			try{
				if( (cols(2).forall(x => {x.isDigit || x=='.'})) && (cols(3).forall(x => {x.isDigit || x=='.'})) && cols(3)!="" && cols(2)!="" ){
					//titles 0, platforms  1,metascore  2 ,userscore    3,genre4,date   5
					lista_juegos+= (cols(0)+","+cols(1)+","+cols(2)+","+cols(3)+","+cols(4)).replaceAll("\'", "")
				}
			}catch{
				case e: Exception => None
			}
		}
		bufferedSource.close
		return lista_juegos
	}
}