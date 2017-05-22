package main.scala

object MigrateData {
  def main(args: Array[String]) = {
    println("CHƯƠNG TRÌNH SAO CHÉP DỮ LIỆU CỦA ZALO MOBILE APP")
    try{
      if (args(0) == "--help" || args(0) == "-h") {
        printHelp()
      } else if (args(0) == "--readDB" || args(0) == "-rdb") {
        Utils.sqliteSelect(args(1), args(2), args(3))
      } else if (args(0) == "--insertDB" || args(0) == "-idb") {
        val iCount = Utils.sqliteInsert(args(1), args(2))
        println("Số dòng dữ liệu được thêm vào cơ sở dữ liệu là: " + iCount)
      } else {
        printHelp()
      }
    } catch {
      case t: Throwable => {
        t.printStackTrace() // TODO: handle error
        printHelp()
      }
    }
  }
  
  def printHelp() = {
    println("Usage: ProgramJarFile [Option] [Arguments]")
    println("       Option:")
    println("              --readDB -rdb : Đọc cơ sở dữ liệu của Zalo và ghi các câu insert ra tập tin. Arguments: ZaloSQLiteDBPath Query OutputFilePath")
    println("              --insertDB -idb : Chèn dữ liệu từ tập tin đã đọc ra vào cơ sở dữ liệu Zalo. Arguments: ZaloSQLiteDBPath InputFilePath")
    println("              --help -h : Print this help")
  }
}