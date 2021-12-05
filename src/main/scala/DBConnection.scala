import java.io.IOException;
import java.sql.{Connection, DriverManager, ResultSet, SQLException, Statement};

class DBConnection {
  private final val CONNECTION_STRING: String = "jdbc:mysql://localhost/farmSim";
  private final val DRIVER: String = "com.mysql.cj.jdbc.Driver";
  private var conn: Connection = _;
  private var stmt: Statement = _;
  private var rs: ResultSet = _;


  protected def connect(): Unit = {
    if (conn == null)
      try {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(CONNECTION_STRING, "farmSim", "farmSim");
      } catch {
        case e: SQLException => e.printStackTrace();
      }
    else if (conn.isClosed)
      try {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(CONNECTION_STRING, "farmSim", "farmSim");
      } catch {
        case e: SQLException => e.printStackTrace();
      }
  }

  protected def disconnect(): Unit = {
    if (!conn.isClosed)
      conn.close();
    if (rs != null)
      if (!rs.isClosed)
        rs.close();
    if (stmt != null)
      if (!stmt.isClosed)
        stmt.close();
  }

  protected def executeDML(query : String) : Option[Boolean] = {
    try {
      stmt = conn.createStatement();
      Some(stmt.execute(query));
    } catch {
      case e: SQLException => {
        e.printStackTrace();
        None;
      }
      case i: IOException => {
        i.printStackTrace();
        None;
      }
      case n: NullPointerException => {
        n.printStackTrace();
        None;
      }
    }
  }

  protected def executeQuery(query: String): Option[ResultSet] = {
    try {
      stmt = conn.createStatement();
      Some(stmt.executeQuery(query));
    } catch {
      case e: SQLException => {
        e.printStackTrace();
        None;
      }
      case i: IOException => {
        i.printStackTrace();
        None;
      }
      case n: NullPointerException => {
        n.printStackTrace();
        None;
      }
    }
  }
}

object DBConnection {
  private final val CONNECTION_STRING: String = "jdbc:mysql://localhost/farmSim";
  private final val DRIVER: String = "com.mysql.jdbc.Driver";
  private var conn: Connection = _;
  private var stmt: Statement = _;
  private var rs: ResultSet = _;


  protected def connect(): Unit = {
    if (conn == null)
      try {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(CONNECTION_STRING, "admin", "admin");
      } catch {
        case e: SQLException => e.printStackTrace();
      }
    else if (conn.isClosed)
      try {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(CONNECTION_STRING, "admin", "admin");
      } catch {
        case e: SQLException => e.printStackTrace();
      }
  }

  protected def disconnect(): Unit = {
    if (!conn.isClosed)
      conn.close();
    if (rs != null)
      if (!rs.isClosed)
        rs.close();
    if (stmt != null)
      if (!stmt.isClosed)
        stmt.close();
  }

  protected def executeDML(query : String) : Option[Boolean] = {
    try {
      stmt = conn.createStatement();
      val result:Boolean = stmt.execute(query);
      Some(result);
    } catch {
      case e: SQLException => {
        e.printStackTrace();
        None;
      }
      case i: IOException => {
        i.printStackTrace();
        None;
      }
      case n: NullPointerException => {
        n.printStackTrace();
        None;
      }
    }
  }

  protected def executeQuery(query: String): Option[ResultSet] = {
    try {
      rs = stmt.executeQuery(query);
      Some(rs);
    } catch {
      case e: SQLException => {
        e.printStackTrace();
        None;
      }
      case i: IOException => {
        i.printStackTrace();
        None;
      }
    }
  }
}
