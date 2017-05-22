package main.scala

import scala.io.Source

object FileReader {
  def fileReader(inputPath: String): Array[String] = {
    try {
      val arrResult = Source.fromFile(inputPath)("UTF-8").getLines().toArray
      arrResult
    } catch {
      case t: Throwable => {
        println("Lỗi không đọc được tập tin ở đường dẫn: " + new java.io.File(inputPath).getCanonicalPath)
        t.printStackTrace() // TODO: handle error
        null
      }
    }
  }
}