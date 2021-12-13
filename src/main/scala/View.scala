import Console._;

object View {
  def prettyPrint(input : String, length : Int, resetPrior : Boolean, resetAfter : Boolean, selections : List[String]) : String = {
    var result:String = input.repeat(length);

    for (s:String <- selections)
      s.toLowerCase() match {
      case "black" => result = s"${BLACK}" + result;
      case "red" => result = s"${RED}" + result;
      case "green" => result = s"${GREEN}" + result;
      case "yellow" => result = s"${YELLOW}" + result;
      case "blue" => result = s"${BLUE}" + result;
      case "magenta" => result = s"${MAGENTA}" + result;
      case "cyan" => result = s"${CYAN}" + result;
      case "white" => result = s"${WHITE}" + result;
      case "black_b" => result = s"${BLACK_B}" + result;
      case "red_b" => result = s"${RED_B}" + result;
      case "green_b" => result = s"${GREEN_B}" + result;
      case "yellow_b" => result = s"${YELLOW_B}" + result;
      case "blue_b" => result = s"${BLUE_B}" + result;
      case "magenta_b" => result = s"${MAGENTA_B}" + result;
      case "cyan_b" => result = s"${CYAN_B}" + result;
      case "white_b" => result = s"${WHITE_B}" + result;
      case "bold" => result = s"${BOLD}" + result;
      case "underlined" => result = s"${UNDERLINED}" + result;
      case "blink" => result = s"${BLINK}" + result;
      case "reversed" => result = s"${REVERSED}" + result;
      case "invisible" => result = s"${INVISIBLE}" + result;
    }

    if (resetPrior)
      result = s"${RESET}" + result;
    if (resetAfter)
      result += s"${RESET}";

    return result;
  }

  def cropPlural(crop : String) : String = {
    crop match {
      case "Avocado" => "Avocados";
      case "Blueberry" => "Blueberries";
      case "Broccoli" => "Broccoli";
      case "Cabbage" => "Cabbage";
      case "Carrot" => "Carrots";
      case "Cauliflower" => "Cauliflowers";
      case "Celery" => "Celery";
      case "Corn" => "Corn";
      case "Eggplant" => "Eggplants";
      case "Potato" => "Potatoes";
      case "Pumpkin" => "Pumpkins";
      case "Turnip" => "Turnips";
    }
  }

  def title() : Unit = {
    //"------------------------------------------------------------------"
    println(prettyPrint("-", 62, true, true, List("bold", "blue", "black_b")));

    //"|                                                                |"
    print(prettyPrint("|", 1, true, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 60, true, false, List("bold", "black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    //"|  _______  _____  ______  ___    ___ _______ __ ___    ___  |"
    print(prettyPrint("|", 1, false, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 7, true, false, List("green_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 5, true, false, List("yellow_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 6, true, false, List("red_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 3, true, false, List("cyan_b")));
    print(prettyPrint(" ", 4, true, false, List("black_b")));
    print(prettyPrint(" ", 3, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 7, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 3, true, false, List("white_b")));
    print(prettyPrint(" ", 4, true, false, List("black_b")));
    print(prettyPrint(" ", 3, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    // "|  __      __   __ __   __ ____  ____ __      __ ____  ____  |"
    print(prettyPrint("|", 1, false, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("green_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("cyan_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    //    // "|  _____   _______ ______  __ ____ __ _______ __ __ ____ __  |"
    print(prettyPrint("|", 1, false, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 5, true, false, List("green_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 7, true, false, List("yellow_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 6, true, false, List("red_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 7, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 4, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    // "|  __      __   __ __   __ __  __  __      __ __ __  __  __  |"
    print(prettyPrint("|", 1, false, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("green_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    // "|   __      __   __ __   __ __      __ _______ __ __      __   |"
    print(prettyPrint("|", 1, false, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("green_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("yellow_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 3, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("red_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("cyan_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 7, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 1, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 6, true, false, List("black_b")));
    print(prettyPrint(" ", 2, true, false, List("white_b")));
    print(prettyPrint(" ", 2, true, false, List("black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    //"|                                                                |"
    print(prettyPrint("|", 1, true, false, List("bold", "blue", "black_b")));
    print(prettyPrint(" ", 60, true, false, List("bold", "black_b")));
    println(prettyPrint("|", 1, true, true, List("bold", "blue", "black_b")));

    //"------------------------------------------------------------------"
    println(prettyPrint("-", 62, true, true, List("bold", "blue", "black_b")));

    println("Welcome to FarmSim!");
    print("Press enter to continue...");
  }

  def mainMenu() : Unit = {
    print(prettyPrint("[N]", 1, false, true, List("bold", "blue")));
    println("ew Game");
    print(prettyPrint("[L]", 1, false, true, List("bold", "green")));
    println("oad Game");
    print(prettyPrint("[C]", 1, false, true, List("bold", "yellow")));
    println("lear All Data");
    print(prettyPrint("[E]", 1, false, true, List("bold", "red")));
    println("xit");
  }

  def loadGame() : Int = {
    val files:List[(Int, String, String, Int, String)] = DBManager.getPlayersInfo();

    if (files.isEmpty) {
      println("No save files found...")
      println("Returning to main menu...")
      return 0;
    } else {
      for (file:(Int, String, String, Int, String) <- files)
        println(s"ID: ${file._1}, Version: ${file._2}, Name: ${file._3}, Day: ${file._4}, Season: ${file._5}"
        );
      return files.length;
    }
  }

  def describeDay(player_id : Int) : Unit = {
    val playerInfo:(Int, String, String, Int, String) = DBManager.getPlayersInfo().filter(p => p._1 == player_id).head;

    print("It is day " + playerInfo._4 + " of ");
    playerInfo._5 match {
      case "Spring" => print(prettyPrint("Spring", 1, true, true, List("green")));
      case "Summer" => print(prettyPrint("Summer", 1, true, true, List("yellow")));
      case "Fall" => print(prettyPrint("Fall", 1, true, true, List("red")));
      case "Winter" => print(prettyPrint("Winter", 1, true, true, List("white")));
    }
    println(".");
    playerInfo._5 match {
      case "Spring" => println("You hear the birds singing about the beautiful day.");
      case "Summer" => println("The sun beats down across the land without a cloud in the sky.");
      case "Fall" => println("Leaves are changing, and the air is refreshingly cool.");
      case "Winter" => println("Patches of frost spot your fields. Winter is here.");
    }

    val plotsInfo:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = DBManager.getPlots(player_id);
    println("You have " + plotsInfo.count(p => p._6.nonEmpty) + " crops.");
    if (plotsInfo.count(p => p._7 == "Fully Grown") > 0)
      println(plotsInfo.count(p => p._7 == "Fully Grown") + " are ready to be sold.");
  }

  def farmActions() : Unit = {
    print("Farm ");
    print(prettyPrint("[P]", 1, true, true, List("green")));
    println("lots");

    print(prettyPrint("[I]", 1, true, true, List("blue")));
    println("nventory");

    print(prettyPrint("[S]", 1, true, true, List("yellow")));
    println("tore");

    print(prettyPrint("[B]", 1, true, true, List("cyan")));
    println("ank");

    print(prettyPrint("[N]", 1, true, true, List("red")));
    println("ext Day");
  }

  def farmPlotDetails(player_id : Int) : Unit = {
    val plotsInfo:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = DBManager.getPlots(player_id);

    if (plotsInfo.count(p => p._6.nonEmpty) == 1)
      println("You have " + plotsInfo.count(p => p._6.nonEmpty) + " crop.");
    else
      println("You have " + plotsInfo.count(p => p._6.nonEmpty) + " crops.");

    for(crop:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plotsInfo.distinctBy(p => p._6).filterNot(p => p._6.isEmpty))
      if (plotsInfo.count(p => p._6 == crop._6) == 1)
        println(plotsInfo.count(p => p._6 == crop._6 && p._7 == crop._7) + " " + crop._6.split(" ").head + " (" + crop._7 + ")");
      else
        println(plotsInfo.count(p => p._6 == crop._6) + " " + cropPlural(crop._6.split(" ").head) + " (" + crop._7 + ")");

    if (plotsInfo.count(p => p._7 == "Fully Grown") > 0) {
      println(plotsInfo.count(p => p._7 == "Fully Grown") + " are ready to be sold.")
      for(crop:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plotsInfo.filter(p => p._7 == "Fully Grown").distinctBy(p => p._6))
        if (plotsInfo.filter(p => p._6 == crop._6).count(p => p._7 == "Fully Grown") == 1)
          println(plotsInfo.filter(p => p._6 == crop._6).count(p => p._7 == "Fully Grown") + " " + crop._6.split(" ").head);
        else
          println(plotsInfo.filter(p => p._6 == crop._6).count(p => p._7 == "Fully Grown") + " " + cropPlural(crop._6.split(" ").head));

      // TODO: Add more information to View.farmPlotDetails().
    }
  }

  def farmPlotActions(player_id : Int) : Unit = {
    val inventory:Map[String, Int] = DBManager.getInventory(player_id);

    if (inventory.contains("Watering Can")) {
      print(prettyPrint("[W]", 1, true, true, List("blue")));
      println("ater Crops")
    }
    if (inventory.contains("Hoe")) {
        print(prettyPrint("[T]", 1, true, true, List("green")));
        println("ill Plots");
    }
    if (inventory.contains("Axe")) {
      print(prettyPrint("[C]", 1, true, true, List("yellow")));
      println("hop Stump");
    }
    if (inventory.contains("Hammer")) {
      print(prettyPrint("[S]", 1, true, true, List("white")));
      println("mash Big Rocks");
    }
    if (inventory.exists(i => i._1.contains("Seed"))) {
      print(prettyPrint("[P]", 1, true, true, List("green")));
      println("lant Crops");
    }

    print(prettyPrint("[E]", 1, true, true, List("red")));
    println("xit");

  }

  def inventory(player_id : Int) : Unit = {
    val inventory:Map[String, Int] = DBManager.getInventory(player_id);

    for (key <- inventory.keys)
      if (inventory(key) > 1)
        println(inventory(key) + " " + key + "s");
      else
        println(inventory(key) + " " + key);
  }

  def bankAccount(player_id : Int) : Unit = {
    val accountDetails:Map[String, Int] = DBManager.getBankAccount(player_id);

    for (key <- accountDetails.keys)
      if (key.equals("balance")) {
        print(key + ": ")
        println(prettyPrint(accountDetails(key).toString, 1, true, true, List("yellow")));
      } else {
        print(key + ": ")
        println(prettyPrint(accountDetails(key).toString, 1, true, true, List("red")));
      }

    println();

    print(prettyPrint("[P]", 1, true, true, List("yellow")));
    println("ay Debt")

    print(prettyPrint("[E]", 1, true, true, List("red")));
    println("xit");
  }

  def store(player_id : Int) : Unit = {
    print("Gold available: ");
    println(prettyPrint(DBManager.getBankAccount(player_id)("balance").toString, 1, true, true, List("yellow")));
    println();

    val storeContents:List[(String, Int, Int, String)] = DBManager.getStoreForSeason(player_id, DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._5);

    for (item:(String, Int, Int, String) <- storeContents.filter(p => p._4 == "" || p._4 == DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._5).sortBy(p => p._4)) {
      if (item._1.nonEmpty && item._2 != 0)
        println(
          "Item: " + item._1 + " ".repeat(storeContents.maxBy(i => i._1.length)._1.length + 1 - item._1.length) +
          "Quantity Available: " + item._2 + " ".repeat(storeContents.maxBy(i => i._2)._2.toString.length + 1 - item._2.toString.length) +
          "Cost: " + item._3
        );
    };
  }

  def storeActions(player_id : Int) : Unit = {
    print(prettyPrint("[B]", 1, true, true, List("green")));
    println("uy Item");

    if (DBManager.getPlots(player_id).count(p => p._7 == "Fully Grown") > 0) {
      print(prettyPrint("[S]", 1, true, true, List("yellow")));
      println("ell Crops");
    }

    print(prettyPrint("[E]", 1, true, true, List("red")));
    println("xit");
  }

  def finalResults(player_id : Int) : Unit = {
    val accountDetails:Map[String, Int] = DBManager.getBankAccount(player_id);

    for (key <- accountDetails.keys)
      if (key.equals("debt"))
        if (accountDetails(key) == 0) {
          println(prettyPrint("Congratulations!", 1, true, true, List("cyan")));
          println("You have successfully paid off your debt and won the game!");
        } else {
          println(prettyPrint("Game Over!", 1, true, true, List("red")));
          println("Your inability to pay off your debt by the end of the year has resulted in your farm being seized.");
        }

  }
}