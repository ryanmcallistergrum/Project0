import java.sql.ResultSet
import scala.collection.immutable
import scala.collection.mutable


class DBManager extends DBConnection {
  def clearDB() : Unit = {
    connect();
    executeDML("delete from salerates;");
    executeDML("delete from plots;");
    executeDML("delete from growthrates;");
    executeDML("delete from inventory;");
    executeDML("delete from bankaccount;");
    executeDML("delete from store;");
    executeDML("delete from playerinfo;");
    executeDML("delete from seasons;");
    disconnect();
  }

  protected def createPlayer(name : String, gameVersion : String) : Int = {
    val id:Int = getNextPlayerId();
    connect();

    executeDML(s"insert into playerinfo (player_id, gameVersion, name, day, season) values ( $id, \"$gameVersion\", \"$name\", 1, \"spring\");");

    disconnect();

    return id;
  }
  def getPlayersInfo() : immutable.List[(Int, String, String, Int, String)] = {
    var lb:mutable.ListBuffer[(Int, String, String, Int, String)] = mutable.ListBuffer();
    connect();

    val rs:ResultSet = executeQuery("select player_id, gameVersion, name, day, season from playerinfo;");
    if (rs != null) {
      while (rs.next()) {
        lb.addOne(
          (
            rs.getInt("player_id"),
            rs.getString("gameVersion"),
            rs.getString("name"),
            rs.getInt("day"),
            rs.getString("season")
          )
        );
      }
      rs.close();
    }

    disconnect();

    return lb.toList;
  }
  private def getNextPlayerId() : Int = {
    connect();
    var result = 0;

    val rs:ResultSet = executeQuery("select max(player_id) \"player_id\" from playerinfo;");

    if (rs == null)
      result += 1;
    else if (rs.next()) {
      result = rs.getInt("player_id") + 1;
      rs.close();
    } else {
      result += 1;
      rs.close();
    }

    disconnect();

    return result;
  }
  def nextDay(player_id : Int) : Unit = {}
  def deletePlayer(player_id : Int) : Unit = {
    connect();
    executeDML(s"delete from salerates where player_id = $player_id;");
    executeDML(s"delete from plots where player_id = $player_id;");
    executeDML(s"delete from growthrates where player_id = $player_id;");
    executeDML(s"delete from inventory where player_id = $player_id;");
    executeDML(s"delete from bankaccount where player_id = $player_id;");
    executeDML(s"delete from store where player_id = $player_id;");
    executeDML(s"delete from playerinfo where player_id = $player_id;");
    disconnect();
  }

  protected def addSeason(season : String, length : Int) : Unit = {
    connect();
    executeDML(s"insert into seasons (season, length) values (\"$season\", $length);");
    disconnect();
  }
  def getSeasonsInfo() : immutable.Map[String, Int] = {
    var result:mutable.Map[String, Int] = mutable.Map();
    connect();

    val rs:ResultSet = executeQuery("select * from seasons where season != \"\";");

    if (rs != null) {
        while (rs.next())
          result.addOne(
            (
              rs.getString("season"), rs.getInt("length")
            )
          )
        rs.close();
      }

    disconnect();

    return result.toMap;
  }

  protected def createBankAccount(player_id : Int, newBalance : Int, newDebt : Int) : Unit = {
    connect();
    executeDML(s"insert into bankaccount(player_id, balance, debt) values($player_id, $newBalance, $newDebt);");
    disconnect();
  }
  def getBankAccount(player_id : Int) : immutable.Map[String, Int] = {
    var result:mutable.Map[String, Int] = mutable.Map[String, Int]();

    connect();

    val rs:ResultSet = executeQuery(s"select balance, debt from bankaccount where player_id = $player_id;");

    if (rs != null) {
      while (rs.next()) {
        result.addOne(("balance", rs.getInt("balance")));
        result.addOne(("debt", rs.getInt("debt")));
      }
      rs.close();
    }

    disconnect();

    return result.toMap;
  }
  def adjustBankBalance(player_id : Int, amt : Int) : Int = {
    val currentBalance:Int = getBankAccount(1)("balance");
    connect();

    executeDML(s"update bankaccount set balance = ${currentBalance + amt} where player_id = $player_id;");

    disconnect();

    return getBankAccount(1)("balance");
  }
  def payDebt(player_id : Int, amt : Int) : immutable.Map[String, Int] = {
    adjustBankBalance(player_id, -amt);
    val newDebt:Int = getBankAccount(player_id)("debt") - amt;

    connect();

    executeDML(s"update bankaccount set debt = $newDebt where player_id = $player_id;");

    disconnect();

    return getBankAccount(player_id);
  }

  def addItemToInventory(player_id : Int, item_name : String, quantity :Int) : List[(String, Int)] = {
    connect();
    executeDML(s"insert into inventory(player_id, item_name, quantity) values ($player_id, \"$item_name\", $quantity);");
    disconnect();
    return getInventory(player_id);
  }
  def getInventory(player_id : Int) : List[(String, Int)] = {
    var result:mutable.ListBuffer[(String, Int)] = mutable.ListBuffer[(String, Int)]();

    connect();

    val rs:ResultSet = executeQuery(s"select item_name, quantity from inventory where player_id = $player_id;");

    if (rs != null) {
      while (rs.next())
        result.addOne(
          (
            rs.getString("item_name"), rs.getInt("quantity")
          )
        );
      rs.close();
    }

    return result.toList;
  }
  def updateItemInventoryQuantity(player_id : Int, item_name : String, newQuantity : Int) : List[(String, Int)] = {
    connect();
    executeDML(s"update inventory set quantity = $newQuantity where player_id = $player_id and item_name = \"$item_name\";");
    disconnect();
    return getInventory(player_id);
  }
  def removeItemFromInventory(player_id : Int, item_name : String) : List[(String, Int)] = {
    connect();
    executeDML("delete from inventory where player_id = $player_id and item_name = \"$item_name\";");
    disconnect();
    return getInventory(player_id);
  }

  protected def addItemToStore(player_id : Int, item_name : String, quantity : Int, cost : Int, season : String) : List[(String, Int, Int)] = {
    connect();
    executeDML(s"insert into store (player_id, item_name, quantity_available, cost, season) values ($player_id, \"$item_name\", $quantity, $cost, \"$season\");");
    disconnect();
    return getStoreForSeason(player_id, season);
  }
  def getStoreForSeason(player_id : Int, season : String) : List[(String, Int, Int)] = {
    var result:mutable.ListBuffer[(String, Int, Int)] = mutable.ListBuffer();
    connect();

    val rs:ResultSet = executeQuery(s"select item_name, quantity_available, cost from store where player_id = $player_id and season = \"$season\";");
    if (rs != null) {
      while (rs.next()) {
        result.addOne(
          (
            rs.getString("item_name"), rs.getInt("quantity_available"), rs.getInt("cost")
          )
        );
      }
      rs.close();
    }


    disconnect();

    return result.toList;
  }
  def buyItem(player_id : Int, item_name : String, quantity : Int, season : String) : Unit = {
    adjustBankBalance(player_id, -getStoreForSeason(player_id, season).filter(i => i._1 == item_name).head._3 * quantity);

    if (!getInventory(player_id).contains(item_name))
      addItemToInventory(player_id, item_name, quantity);
    else
      updateItemInventoryQuantity(player_id, item_name, getInventory(player_id).filter(i => i._1 == item_name).head._2 + quantity);


    val currentStoreQuantity:Int = getStoreForSeason(player_id, season).filter(i => i._1 == item_name).head._2;

    connect();
    executeDML(s"update store set quantity_available = ${currentStoreQuantity - quantity} where player_id = $player_id and item_name = \"$item_name\" and season = \"$season\";");
    disconnect();
  }

  protected def addGrowthRate(player_id : Int, item_name : String, sequence : Int, stage_description : String, length : Int, season : String) : Unit = {
    connect();
    executeDML(s"insert into growthrates(player_id, item_name, sequence, stage_description, length, season) values ($player_id, \"$item_name\", $sequence, \"$stage_description\", $length, \"$season\");");
    disconnect();
  }
  def getGrowthRates(player_id : Int, season : String) : List[(String, Int, String, Int, String)] = {
    var result:mutable.ListBuffer[(String, Int, String, Int, String)] = mutable.ListBuffer();
    val query:String = if (season.isEmpty)
      s"select item_name, sequence, stage_description, length, season from growthrates where player_id = $player_id and season != \"\" order by field(season, \"spring\", \"summer\", \"fall\", \"winter\"), item_name, sequence;"
    else
      s"select item_name, sequence, stage_description, length, season from growthrates where player_id = $player_id and season = \"$season\" order by item_name, sequence;";

    connect();

    val rs:ResultSet = executeQuery(query);

    if (rs != null) {
      while (rs.next())
        result.addOne(
          (
            rs.getString("item_name"), rs.getInt("sequence"), rs.getString("stage_description"), rs.getInt("length"), rs.getString("season")
          )
        );
      rs.close();
    }

    disconnect();

    return result.toList;
  }

  protected def addSaleRate(player_id : Int, item_name : String, stage_description : String, profit : Int) : Unit = {
    connect();
    executeDML(s"insert into salerates(player_id, item_name, stage_description, profit) values ($player_id, \"$item_name\", \"$stage_description\", $profit);");
    disconnect();
  }
  def getSaleRates(player_id : Int) : immutable.Map[String, Int] = {
    var result:mutable.Map[String, Int] = mutable.Map();

    connect();

    val rs:ResultSet = executeQuery(s"select item_name, profit from salerates where player_id = $player_id order by item_name;");

    if (rs != null) {
      while (rs.next())
        result.addOne(
          (
            rs.getString("item_name"), rs.getInt("profit")
          )
        );
      rs.close();
    }

    disconnect();

    return result.toMap;
  }

  protected def addPlot(player_id : Int, plot_id : Int, tilled : Boolean, watered : Boolean, hasBigRock : Boolean, hasStump : Boolean, item_name : String, stage_description : String, daysGrown : Int) : Unit = {
    connect();
    executeDML(s"insert into plots(player_id, plot_id, tilled, watered, hasBigRock, hasStump, item_name, stage_description, daysGrown) values ($player_id, $plot_id, $tilled, $watered, $hasBigRock, $hasStump, \"$item_name\", \"$stage_description\", $daysGrown);");
    disconnect();
  }
  def getPlots(player_id : Int) : List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = {
    var result:mutable.ListBuffer[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = mutable.ListBuffer();

    connect();

    val rs:ResultSet = executeQuery(s"select plot_id, tilled, watered, hasBigRock, hasStump, item_name, stage_description, daysGrown from plots where player_id = $player_id order by plot_id;");

    if (rs != null) {
      while (rs.next())
        result.addOne(
          (
            rs.getInt("plot_id"), rs.getBoolean("tilled"), rs.getBoolean("watered"), rs.getBoolean("hasBigRock"), rs.getBoolean("hasStump"), rs.getString("item_name"), rs.getString("stage_description"), rs.getInt("daysGrown")
          )
        );
      rs.close();
    }

    disconnect();

    return result.toList;
  }
  def tillPlot(player_id : Int, plot_id : Int) : Unit = {
    connect();
    executeDML(s"update plots set tilled = true where player_id = $player_id and plot_id = $plot_id;");
    disconnect();
  }
  def waterPlot(player_id : Int, plot_id : Int) : Unit = {
    connect();
    executeDML(s"update plots set watered = true where player_id = $player_id and plot_id = $plot_id;");
    disconnect();
  }
  def breakBigRock(player_id : Int, plot_id : Int) : Unit = {
    connect();
    executeDML(s"update plots set hasBigRock = false where player_id = $player_id and plot_id = $plot_id;");
    disconnect();
  }
  def chopStump(player_id : Int, plot_id : Int) : Unit = {
    connect();
    executeDML(s"update plots set hasStump = false where player_id = $player_id and plot_id = $plot_id;");
    disconnect();
  }
  def addPlant(player_id : Int, plot_id : Int, item_name : String) : Unit = {
    val plantDetails:(String, Int, String, Int, String) = getGrowthRates(player_id, "").filter(p => p._1 == item_name && p._2 == 0).head;

    connect();

    executeDML(s"update plots set item_name = \"$item_name\", stage_description = \"${plantDetails._3}\" where player_id = $player_id and plot_id = $plot_id;");

    disconnect();
  }
  def growPlants(player_id : Int) : Unit = {
    val eligiblePlants:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = getPlots(player_id).filter(p => p._3); // watered == true

    for (plant <- eligiblePlants) {
      var newDaysGrown:Int = plant._8 + 1;
      val itemName:String = plant._6;
      var stageDescription:String = plant._7;

      val plantGrowthRates:List[(String, Int, String, Int, String)] = getGrowthRates(player_id, "").filter(p => p._1 == itemName);

       if (plantGrowthRates.filter(p => p._3 == stageDescription).head._4 < newDaysGrown) {
         // Advance plant to next stage.
         val nextSequence:Int = plantGrowthRates.filter(p => p._3 == stageDescription).head._2 + 1;
         connect();
         executeDML(s"update plots set stage_description = \"${plantGrowthRates.filter(p => p._2 == nextSequence).head._3}\", daysGrown = 0, watered = false where player_id = $player_id and plot_id = ${plant._1};");
         disconnect();
       } else {
         connect();
         executeDML(s"update plots set daysGrown = $newDaysGrown, watered = false where player_id = $player_id and plot_id = ${plant._1};");
         disconnect();
       }
    }
  }
  def removePlant(player_id : Int, plot_id : Int) : Unit = {
    connect();
    executeDML(s"update plots set item_name = \"\", stage_description = \"\", daysGrown = 0, watered = false where player_id = $player_id and plot_id = $plot_id;");
    disconnect();
  }
  def sellPlant(player_id : Int, item_name : String, plot_id : Int) : Int = {
    var profit:Int = getSaleRates(player_id).filter(p => p._1 == item_name).head._2;
    adjustBankBalance(player_id, profit);
    removePlant(player_id, plot_id);
    return profit;
  }
}