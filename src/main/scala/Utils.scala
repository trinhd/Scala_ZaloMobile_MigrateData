package main.scala

import java.sql.Statement
import java.sql.Connection
import java.sql.ResultSet

import scala.util.control.Breaks._
import java.sql.PreparedStatement

object Utils {
  def sqliteSelect(dbpath: String, query: String, outputfilepath: String) = {
    val conn = SQLiteConnect.dbConnect(dbpath)
    var strInsert = "";
    try {
      val stmt: Statement = conn.createStatement()
      val rs: ResultSet = stmt.executeQuery(query)
      var iCount = 0

      while (rs.next()) {
        var data1 = rs.getBytes("data1")
        if (data1 != null) {
          println(scala.io.Source.fromBytes(data1).mkString)
          //data1 = "'" + data1.toString() + "'"
        } else println(data1)
        strInsert += util.Properties.lineSeparator + "INSERT INTO chat_content (currentUserUid, message, url, url_thumb, localpath, timestamp, senderUid, senderName, ownerId, type, state, receiverUid, minigame, header, footer, secret, metadata_1, metadata_2, state_sync, cliMsgIdReply, data1, data2, data3) VALUES ('" + rs.getString("currentUserUid") + "', '" + rs.getString("message") + "', '" + rs.getString("url") + "', '" + rs.getString("url_thumb") + "', '" + rs.getString("localpath") + "', " + rs.getString("timestamp") + ", '" + rs.getString("senderUid") + "', '" + rs.getString("senderName") + "', '" + rs.getString("ownerId") + "', " + rs.getString("type") + ", " + rs.getString("state") + ", '" + rs.getString("receiverUid") + "', '" + rs.getString("minigame") + "', '" + rs.getString("header") + "', '" + rs.getString("footer") + "', '" + rs.getString("secret") + "', '" + rs.getString("metadata_1") + "', '" + rs.getString("metadata_2") + "', " + rs.getString("state_sync") + ", " + rs.getString("cliMsgIdReply") + ", " + data1 + ", " + rs.getString("data2") + ", " + rs.getString("data3") + ")"
        iCount += 1
        println(iCount)
        if (iCount > 3) break
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
  
  def sqliteSelectInsert(dbSource: String, dbDest: String, query: String): Int = {
    var iCount = 0
    
    val conn1 = SQLiteConnect.dbConnect(dbSource)
    try {
      val stmt: Statement = conn1.createStatement()
      val rs: ResultSet = stmt.executeQuery(query)
      var iCount = 0

      while (rs.next()) {
        val conn2 = SQLiteConnect.dbConnect(dbDest)
        var prestmt: PreparedStatement = conn2.prepareStatement("INSERT INTO chat_content (currentUserUid, message, url, url_thumb, localpath, timestamp, senderUid, senderName, ownerId, type, state, receiverUid, minigame, header, footer, secret, metadata_1, metadata_2, state_sync, cliMsgIdReply, data1, data2, data3) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        
        var data1 = rs.getBinaryStream("data1")
        var buffer : Array[]
        
        prestmt.setString(1, rs.getString("currentUserUid"))
        prestmt.setString(2, rs.getString("message"))
        prestmt.setString(3, rs.getString("url"))
        prestmt.setString(4, rs.getString("url_thumb"))
        prestmt.setString(5, rs.getString("localpath"))
        prestmt.setTimestamp(6, rs.getTimestamp("timestamp"))
        prestmt.setString(7, rs.getString("senderUid"))
        prestmt.setString(8, rs.getString("senderName"))
        prestmt.setString(9, rs.getString("ownerId"))
        prestmt.setInt(10, rs.getInt("type"))
        prestmt.setString(11, rs.getString("state"))
        prestmt.setString(12, rs.getString("receiverUid"))
        prestmt.setString(13, rs.getString("minigame"))
        prestmt.setString(14, rs.getString("header"))
        prestmt.setString(15, rs.getString("footer"))
        prestmt.setString(16, rs.getString("secret"))
        prestmt.setString(17, rs.getString("metadata_1"))
        prestmt.setString(18, rs.getString("metadata_2"))
        prestmt.setString(19, rs.getString("state_sync"))
        prestmt.setInt(20, rs.getInt("cliMsgIdReply"))
        prestmt.setBytes(21, data1)
        prestmt.setInt(22, rs.getInt("data2"))
        prestmt.setString(23, rs.getString("data3"))
        
        
        /*var data1 = rs.getBytes("data1")
        if (data1 != null) {
          println(scala.io.Source.fromBytes(data1).mkString)
          //data1 = "'" + data1.toString() + "'"
        } else println(data1)
        strInsert += util.Properties.lineSeparator + "INSERT INTO chat_content (currentUserUid, message, url, url_thumb, localpath, timestamp, senderUid, senderName, ownerId, type, state, receiverUid, minigame, header, footer, secret, metadata_1, metadata_2, state_sync, cliMsgIdReply, data1, data2, data3) VALUES ('" + rs.getString("currentUserUid") + "', '" + rs.getString("message") + "', '" + rs.getString("url") + "', '" + rs.getString("url_thumb") + "', '" + rs.getString("localpath") + "', " + rs.getString("timestamp") + ", '" + rs.getString("senderUid") + "', '" + rs.getString("senderName") + "', '" + rs.getString("ownerId") + "', " + rs.getString("type") + ", " + rs.getString("state") + ", '" + rs.getString("receiverUid") + "', '" + rs.getString("minigame") + "', '" + rs.getString("header") + "', '" + rs.getString("footer") + "', '" + rs.getString("secret") + "', '" + rs.getString("metadata_1") + "', '" + rs.getString("metadata_2") + "', " + rs.getString("state_sync") + ", " + rs.getString("cliMsgIdReply") + ", " + data1 + ", " + rs.getString("data2") + ", " + rs.getString("data3") + ")"
        */
        iCount += prestmt.executeUpdate()
        if (iCount > 3) break
      }
      
      
    } catch {
      case t: Throwable => {
        println("LỖI KHÔNG TẠO ĐƯỢC CÂU INSERT DỮ LIỆU!!!")
        t.printStackTrace() // TODO: handle error
      }
    }
    SQLiteConnect.dbClose(conn1)
    
    iCount
  }
}