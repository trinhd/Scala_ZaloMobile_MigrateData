package main.scala

import java.sql.Statement
import java.sql.Connection
import java.sql.ResultSet

object Utils {
  def sqliteSelect(dbpath: String, query: String, outputfilepath: String) = {
    val conn = SQLiteConnect.dbConnect(dbpath)
    var strInsert = "";
    try {
      val stmt = conn.createStatement()
      val rs: ResultSet = stmt.executeQuery(query)

      while (rs.next()) {
        strInsert += util.Properties.lineSeparator + "INSERT INTO chat_content (recid, currentUserUid, message, url, url_thumb, localpath, timestamp, senderUid, senderName, ownerId, type, state, receiverUid, minigame, header, footer, secret, metadata_1, metadata_2, state_sync, cliMsgIdReply, data1, data2, data3) VALUES (" + rs.getString("recid") + ", " + rs.getString("currentUserUid") + ", " + rs.getString("message") + ", " + rs.getString("url") + ", " + rs.getString("url_thumb") + ", " + rs.getString("localpath") + ", " + rs.getString("timestamp") + ", " + rs.getString("senderUid") + ", " + rs.getString("senderName") + ", " + rs.getString("ownerId") + ", " + rs.getString("type") + ", " + rs.getString("state") + ", " + rs.getString("receiverUid") + ", " + rs.getString("minigame") + ", " + rs.getString("header") + ", " + rs.getString("footer") + ", " + rs.getString("secret") + ", " + rs.getString("metadata_1") + ", " + rs.getString("metadata_2") + ", " + rs.getString("state_sync") + ", " + rs.getString("cliMsgIdReply") + ", " + rs.getString("data1") + ", " + rs.getString("data2") + ", " + rs.getString("data3") + ")"
      }

      OutputFileWriter.writeFile(outputfilepath, false, strInsert)
    } catch {
      case t: Throwable => {
        println("LỖI KHÔNG TẠO ĐƯỢC CÂU INSERT DỮ LIỆU!!!")
        t.printStackTrace() // TODO: handle error
      }
    }
    SQLiteConnect.dbClose(conn)
  }

  def sqliteInsert(dbpath: String, inputfilepath: String): Int = {
    var intCount = 0;
    val conn = SQLiteConnect.dbConnect(dbpath)
    val arrInsert = FileReader.fileReader(inputfilepath)
    try {
      for (strInsert <- arrInsert) {
        println(strInsert)
        val prestmt = conn.prepareStatement(strInsert)
        intCount += prestmt.executeUpdate()
      }
    } catch {
      case t: Throwable => {
        println("KHÔNG THÊM ĐƯỢC DỮ LIỆU!!!")
        t.printStackTrace() // TODO: handle error
      }
    }

    intCount
  }
}