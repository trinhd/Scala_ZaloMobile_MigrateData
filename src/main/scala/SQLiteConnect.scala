package main.scala

import java.sql.Connection
import java.sql.DriverManager

object SQLiteConnect {
  def dbConnect(dbpath: String): Connection = {
    var conn: Connection = null
    try {
      // db parameters
      val url = "jdbc:sqlite:" + dbpath;
      // create a connection to the database
      conn = DriverManager.getConnection(url)

      println("Connection to SQLite " + dbpath + " has been established.");

    } catch {
      case t: Throwable => {
        t.printStackTrace()
      }
    }
    conn
  }

  def dbClose(conn: Connection) = {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch {
      case t: Throwable => {
        t.printStackTrace()
      }
    }
  }
}