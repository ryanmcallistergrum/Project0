
object DBLoader extends DBManager {
  def loadJSONConfigIntoDatabase(new_player_name : String) : Int = {
    val json = ujson.read(os.read(os.pwd / "gameConfig.json"));

    if (getSeasonsInfo().isEmpty)
      for(season <- json("seasons").obj.keys)
        addSeason(season, json("seasons")(season).num.toInt);

    val player_id:Int = createPlayer(new_player_name, json("version").str);

    for (season <- json("store").obj.keys)
      for (item <- json("store")(season).obj.keys)
        addItemToStore(
          player_id,
          item,
          json("store")(season)(item)("quantity_available").num.toInt,
          json("store")(season)(item)("cost").num.toInt,
          season
        );

    for (item <- json("growthRates").obj.keys)
      if (item.isEmpty) // No array
        addGrowthRate(
          player_id,
          item,
          json("growthRates")(item)("sequence").num.toInt,
          json("growthRates")(item)("stage_description").str,
          json("growthRates")(item)("length").num.toInt,
          json("growthRates")(item)("season").str,
        );
      else
        for (i:Int <- json("growthRates")(item).arr.indices)
          addGrowthRate(
            player_id,
            item,
            json("growthRates")(item)(i)("sequence").num.toInt,
            json("growthRates")(item)(i)("stage_description").str,
            json("growthRates")(item)(i)("length").num.toInt,
            json("growthRates")(item)(i)("season").str
          );

    for (item <- json("saleRates").obj.keys)
      addSaleRate(
        player_id,
        item,
        json("saleRates")(item)("stage_description").str,
        json("saleRates")(item)("profit").num.toInt
      )

    for (plot:Int <- json("plots").arr.indices)
      addPlot(
        player_id,
        json("plots")(plot)("plot_id").num.toInt,
        json("plots")(plot)("tilled").bool,
        json("plots")(plot)("watered").bool,
        json("plots")(plot)("hasBigRock").bool,
        json("plots")(plot)("hasStump").bool,
        json("plots")(plot)("item_name").str,
        json("plots")(plot)("stage_description").str,
        json("plots")(plot)("days_grown").num.toInt
      );

    createBankAccount(
      player_id,
      json("bankAccount")("balance").num.toInt,
      json("bankAccount")("debt").num.toInt
    );

    for (i <- json("inventory").arr.indices)
      addItemToInventory(
        player_id,
        json("inventory")(i)("item_name").str,
        json("inventory")(i)("quantity").num.toInt
      );

    return player_id;
  }
}