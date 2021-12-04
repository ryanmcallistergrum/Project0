import com.mongodb.{ServerApi, ServerApiVersion}
import org.mongodb.scala.{ConnectionString, MongoClient, MongoClientSettings, MongoDatabase}

object DBConnection {
  private final val CONNECTION_STRING:String = "mongodb://localhost:27017";
  private final val CLIENT_SETTINGS:MongoClientSettings = MongoClientSettings.builder().applyConnectionString(ConnectionString(CONNECTION_STRING)).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
  protected final val DB_NAME:String = "farmSim";
  protected final val CLIENT:MongoClient = MongoClient(CLIENT_SETTINGS);

  protected def connect() : MongoDatabase = {
    CLIENT.getDatabase(DB_NAME);
  }
  protected def disconnect() : Unit = {
    CLIENT.close();
  }
}