import scala.collection.mutable
import scala.io.StdIn.readLine

object Controller {
  private var player_id:Int = 0;
  private var state:String = "";

  def titleScreen() : Unit = {
    state = "title";

    while (state.equals("title")) {
      View.title();
      val toss:String = readLine();
      mainMenu();
    }
  }

  private def mainMenu() : Unit = {
    state = "main menu";
    var input:String = "";
    val inputOptions:List[String] = List("n", "l", "c", "e");

    while (state.equals("main menu")) {
      View.mainMenu();

      do {
        print("Please select a menu option: ");
        input = readLine();
        if (!inputOptions.contains(input.toLowerCase))
          println("Invalid menu option, please try again!");
      } while (!inputOptions.contains(input.toLowerCase));

      input.toLowerCase match {
        case "n" => newGame();
        case "l" => loadGame();
        case "c" => clearAllData();
        case "e" => state = "exit";
      };
    }
  }

  private def newGame() : Unit = {
    state = "new game";
    var input:String = "";

    while(state.equals("new game")) {
      do {
        print("Please enter in your player name: ");
        input = readLine();
        if (input.isEmpty)
          println("Invalid name, please try again!");
      } while (input.isEmpty);

      player_id = DBLoader.loadJSONConfigIntoDatabase(input);
      farm();
    }
  }

  private def loadGame() : Unit = {
    state = "load game";
    var input:String = "";

    while(state.equals("load game")) {
      if (View.loadGame() == 0)
        state = "main menu";
      else {
        val playerIDList:mutable.ArrayBuffer[Int] = mutable.ArrayBuffer();
        var gameId:Int = 0;
        DBManager.getPlayersInfo().foreach(p => playerIDList.addOne(p._1));

        do {
          print("Please select a player ID to load (or 'e' to return to the main menu): ");
          input = readLine();

          if (input.isEmpty)
            println("Invalid player ID, please try again!");
          else if (!input.matches("[1-9][0-9]?+"))
            println("Invalid player ID, please try again!");
          else if (!playerIDList.contains(input.toInt))
            println("Invalid player ID, please try again!");
          else {
            gameId = input.toInt;
          }
        } while (!input.equals("e") && !playerIDList.contains(gameId));

        if (!input.equals("e")) {
          player_id = input.toInt;
          farm();
        } else
          state = "main menu";
      }
    }
  }

  private def clearAllData() : Unit = {
    var input:String = "";

    do {
      println("Are you sure you want to clear all data?");
      println("Enter 'yes' to confirm or 'e' to return to the main menu.");
      input = readLine().toLowerCase;
      if (!input.equals("e") && !input.equals("yes"))
        println("Invalid menu option, please try again!");
    } while(!input.equals("e") && !input.equals("yes"));

    if (!input.equals("e")) {
      DBManager.clearDB()
      println("Database cleared!");
      print("Press enter to return to the main menu...");
      val toss:String = readLine();
    }
  }

  private def farm() : Unit = {
    state = "farm";
    var input:String = "";
    val inputOptions:List[String] = List("p", "i", "s", "b", "n");

    if (
      DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._4 == 0 ||
      DBManager.getBankAccount(player_id)("debt") == 0
    )
      resultScreen();

    while(state.equals("farm")) {
      View.describeDay(player_id);
      View.farmActions();

      do {
        print("Please select an action: ");
        input = readLine();
        if (!inputOptions.contains(input.toLowerCase))
          println("Invalid option, please try again!");
      } while (!inputOptions.contains(input.toLowerCase));

      input.toLowerCase match {
        case "p" => farmPlot();
        case "i" => inventory();
        case "s" => store();
        case "b" => bankAccount();
        case "n" => DBManager.nextDay(player_id);
      };

      if (
        DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._4 == 0 ||
        DBManager.getBankAccount(player_id)("debt") == 0
      )
        resultScreen();
    }
  }

  private def stumpBigRockComment() : Unit = {
    val plots:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = DBManager.getPlots(player_id);

    val bigRockCount:Int = plots.count(p => p._4);
    val stumpCount:Int = plots.count(p => p._5);
    if (bigRockCount > 0 && stumpCount == 0) {
      if (bigRockCount == 1)
        println(s"There is $bigRockCount Big Rock that could be crushed with a Hammer.");
      else
        println(s"There are $bigRockCount Big Rocks that could be crushed with a Hammer.");
    } else if (bigRockCount == 0 && stumpCount > 0) {
      if (stumpCount == 1)
        println(s"There is $stumpCount Stump that could be chopped to bits with an Axe.");
      else
        println(s"There are $stumpCount Stumps that could be chopped to bits with an Axe.");
    } else if (bigRockCount == 1 && stumpCount == 1)
      println(s"There is $bigRockCount Big Rock that could be crushed with a Hammer, and $stumpCount Stump that could be chopped to bits with an Axe.");
    else if (bigRockCount == 1 && stumpCount > 1)
      println(s"There is $bigRockCount Big Rock that could be crushed with a Hammer, and $stumpCount Stumps that could be chopped to bits with an Axe.");
    else if (bigRockCount > 1 && stumpCount == 1)
      println(s"There are $bigRockCount Big Rocks that could be crushed with a Hammer, and $stumpCount Stump that could be chopped to bits with an Axe.");
    else if (bigRockCount > 1 && stumpCount > 1)
      println(s"There are $bigRockCount Big Rocks that could be crushed with a Hammer, and $stumpCount Stumps that could be chopped to bits with an Axe.");
  }

  private def untilledPlotsComment() : Unit = {
    val plots:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = DBManager.getPlots(player_id);
    val validUntilledPlotsCount:Int = plots.count(p => !p._2 && !p._4 && !p._5 && p._6.isEmpty);

    if (validUntilledPlotsCount == 1)
      println(s"Looking over your fields, you see $validUntilledPlotsCount place you could fit a plant, if only it was tilled.");
    else if (validUntilledPlotsCount > 1)
      println(s"Looking over your fields, you see $validUntilledPlotsCount places you could fit a plant, if only they were tilled.");
  }

  private def farmPlot() : Unit = {
    state = "farm plot";
    var input:String = "";
    val inputOptions:mutable.Set[String] = mutable.Set("e");

    for(item:String <- DBManager.getInventory(player_id).keys)
      item match {
        case "Watering Can" => inputOptions.addOne("w");
        case "Hoe" => inputOptions.addOne("t")
        case "Axe" => inputOptions.addOne("c");
        case "Hammer" => inputOptions.addOne("s");
        case s"$item Seed" => inputOptions.addOne("p");
      }

    while (state.equals("farm plot")) {
      View.farmPlotDetails(player_id);
      View.farmPlotActions(player_id);

      do {
        print("Please select an action: ");
        input = readLine();
        if (!inputOptions.contains(input.toLowerCase))
          println("Invalid option, please try again!");
      } while (!inputOptions.contains(input.toLowerCase));

      val plots:List[(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int)] = DBManager.getPlots(player_id);

      input.toLowerCase match {
        case "w" => {
          if (plots.forall(p => p._6.isEmpty))
            println("You have no crops to water!");
          else if (plots.filter(p => p._6.nonEmpty).filter(p => !p._7.equals("Fully Grown")).count(p => !p._3) == 0)
            println("None of your crops need watering!");
          else if (plots.filter(p => p._6.nonEmpty).filter(p => !p._7.equals("Fully Grown")).count(p => !p._3) == 1) {
            println("Inspecting each of your plants, you find one that could use some water.");

            for (plot: (Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._6.nonEmpty).filter(p => !p._7.equals("Fully Grown")).filter(p => !p._3))
              DBManager.waterPlot(player_id, plot._1)

            println("Watered 1 plot.");
          } else {
            println("Inspecting each of your plants, you find multiple that could use some water.")

            for (plot: (Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._6.nonEmpty).filter(p => !p._7.equals("Fully Grown")).filter(p => !p._3))
              DBManager.waterPlot(player_id, plot._1)

            println(s"Watered ${plots.filter(p => p._6.nonEmpty).filter(p => !p._7.equals("Fully Grown")).count(p => !p._3)} plots.");
          }
          println("Press enter to continue...");
          val toss:String = readLine();
        }
        case "t" => {
          if (plots.forall(p => p._2))
            println("All of your plots are tilled!");
          else if (plots.filter(p => !p._4 && !p._5).count(p => !p._2) == 0) {
            println("All of your plots are tilled!");
            stumpBigRockComment();
          } else if (plots.filter(p => !p._4 && !p._5).count(p => !p._2) == 1) {
            println("Walking through your fields, you find one spot that could fit another crop.");

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => !p._4 && !p._5).filter(p => !p._2))
              DBManager.tillPlot(player_id, plot._1);

            println("Tilled 1 plot.");
          } else {
            println("Walking through your fields, you find some spots that could fit another crop.");
            val preTillCount:Int = plots.filter(p => !p._4 && !p._5).count(p => !p._2);

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => !p._4 && !p._5).filter(p => !p._2))
              DBManager.tillPlot(player_id, plot._1);

            println(s"Tilled ${plots.filter(p => !p._4 && !p._5).count(p => !p._2) - preTillCount} plots.");
          }
          println("Press enter to continue...");
          val toss:String = readLine();
        }
        case "c" => {
          if (plots.forall(p => !p._5))
            println("There are no stumps for you to chop up!");
          else if (plots.count(p => p._5) == 1) {
            println("Scanning your fields, you notice a stump of a once-large tree taking up farm space.");

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._5))
              DBManager.chopStump(player_id, plot._1);

            println("You chopped 1 Stump.");
          } else {
            println("Scanning your fields, you notice stumps of once-large trees taking up farm space. Was this once a forest?");

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._5))
              DBManager.chopStump(player_id, plot._1);

            println(s"You chopped up ${plots.count(p => p._5)} Stumps.");
          }
          println("Press enter to continue...");
          val toss:String = readLine();
        }
        case "s" => {
          if (plots.forall(p => !p._4))
            println("There are no big rocks for you to break!");
          else if (plots.count(p => p._4) == 1) {
            println("Scanning your fields, you notice a sizable boulder taking up farm space.");

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._4))
              DBManager.breakBigRock(player_id, plot._1);

            println("You smashed 1 Big Rock.");
          } else {
            println("Scanning your fields, you notice sizable boulders taking up farm space.");

            for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._4))
              DBManager.breakBigRock(player_id, plot._1);

            println(s"You smashed ${plots.count(p => p._4)} Big Rocks.");
          }
          println("Press enter to continue...");
          val toss:String = readLine();
        }
        case "p" => {
          if (plots.forall(p => p._6.nonEmpty))
            println("All of your tilled plots are full!");
          else if (plots.filter(p => p._2).forall(p => p._6.nonEmpty)) {
            println("All of your tilled plots are full!");
            stumpBigRockComment();
            untilledPlotsComment();
          } else if (plots.filter(p => p._2).count(p => p._6.isEmpty) == 1) {
            println("Walking through your fields, you find one tilled spot that could fit another crop.");

            var seed:String = "";
            val seeds:Map[String, Int] = DBManager.getInventory(player_id).filter(i => i._1.contains("Seed"));
            seeds.foreach(i => println(i._1.split(" ")(0) + " ".repeat(i._1.split(" ")(0).length - seeds.keys.max.split(" ")(0).length + 1) + "Qty: " + i._2));


            do {
              print("Please enter in the name of the seed to plant, or 'e' to return to the farm plots: ");
              seed = readLine();
              if (!seed.equals("e") && !seeds.exists(s => s._1.startsWith(seed)))
                println("Invalid option, please try again!")
            } while (!seed.equals("e") && !seeds.exists(s => s._1.startsWith(seed)));

            if (!seed.equals("e")) {
              DBManager.addPlant(player_id, plots.filter(p => p._6.isEmpty).head._1, seeds.filter(s => s._1.startsWith(seed)).head._1)
              println(s"1 $seed planted.");
              println("Press enter to continue...");
              val toss:String = readLine();
            }
          } else if (plots.filter(p => p._2).count(p => p._6.isEmpty) > 1) {
            println("Walking through your fields, you find some tilled spots that could fit another crop.");

            var seed:String = "";
            val seeds:Map[String, Int] = DBManager.getInventory(player_id).filter(i => i._1.contains("Seed"));
            seeds.foreach(i => println(i._1.split(" ")(0) + " ".repeat(i._1.split(" ")(0).length - seeds.keys.max.split(" ")(0).length + 1) + "Qty: " + i._2));

            do {
              print("Please enter in the name of the seed to plant, or 'e' to return to the farm plots: ");
              seed = readLine();
              if (!seed.equals("e") && !seeds.exists(s => s._1.startsWith(seed)))
                println("Invalid option, please try again!")
            } while (!seed.equals("e") && !seeds.exists(s => s._1.startsWith(seed)));

            if (!seed.equals("e")) {
              val maxPlantable:Int = Math.min(seeds.filter(s => s._1.startsWith(seed)).head._2, plots.count(p => p._2 && p._6.isEmpty));

              var userPlantNum:Int = 0;
              do {
                print(s"Please enter in the number of seeds you wish to plant (max: $maxPlantable) or 'e' to return to the farm plots menu: ");
                try {
                  input = readLine();
                  if (!input.equals("e"))
                    if (input.toInt < 1)
                      println("That is not a valid number! Please try again!");
                    else
                      userPlantNum = input.toInt;
                } catch {
                  case a : NumberFormatException => {
                    println("That is not a valid number! Please try again!");
                  }
                }
              } while (!input.equals("e") && (userPlantNum < 1 || userPlantNum <= maxPlantable));

              if (!input.equals("e")) {
                for (plot:(Int, Boolean, Boolean, Boolean, Boolean, String, String, Int) <- plots.filter(p => p._2 && p._6.isEmpty).dropRight(plots.count(p => p._2 && p._6.isEmpty) - userPlantNum))
                  DBManager.addPlant(player_id, plot._1, seeds.filter(s => s._1.startsWith(seed)).head._1);

                if (userPlantNum == seeds.filter(s=> s._1.startsWith(seed)).head._2)
                  DBManager.removeItemFromInventory(player_id, seeds.filter(s => s._1.startsWith(seed)).head._1);
                else
                  DBManager.updateItemInventoryQuantity(player_id, seeds.filter(s => s._1.startsWith(seed)).head._1, seeds.filter(s => s._1.startsWith(seed)).head._2 - userPlantNum);

                if (userPlantNum == 1)
                  println(s"Planted $userPlantNum $seed.");
                else
                  println(s"Planted $userPlantNum ${seed}s.");

                println("Press enter to continue...");
                val toss:String = readLine();
              } else
                state = "farm";
            }
          }
        }
        case "e" => state = "farm";
      }
    }
  }

  private def inventory() : Unit = {
    View.inventory(player_id);
    print("Press enter to continue...");
    val toss:String = readLine();
  }

  private def store() : Unit = {
    state = "store";
    var input:String = "";
    val inputOptions:Set[String] = Set("b", "e");

    while (state.equals("store")) {
      View.store(player_id);
      View.storeActions();

      do {
        print("Please select an action: ");
        input = readLine();
        if (!inputOptions.contains(input.toLowerCase))
          println("Invalid option, please try again!");
      } while (!inputOptions.contains(input.toLowerCase));

      input.toLowerCase match {
        case "b" => {
          val shopList:List[(String, Int, Int, String)] = DBManager.getStoreForSeason(player_id, DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._5).filter(i => i._2 > 0);
          var item:String = "";

          do {
            print("Please enter in the name of the item you wish to buy, or 'e' to return to the shop menu: ");
            input = readLine();
            if (!input.equals("e") && !shopList.exists(i => i._1.equals(input)))
              println("Invalid option, please try again!");
            else if (DBManager.getBankAccount(player_id)("balance") < shopList.filter(i => i._1.equals(input)).head._3)
              println(s"Cannot afford $input, please choose another item!");
            else
              item = input;
          } while (!input.equals("e") && !shopList.exists(i => i._1.equals(input)));

          if (!input.equals("e")) {
            val balance:Int = DBManager.getBankAccount(player_id)("balance");
            val maxPurchasable:Int = Math.min(balance / shopList.filter(i => i._1.equals(input)).head._3, shopList.filter(i => i._1.equals(input)).head._2);

            var purchaseAmount:Int = 0;
            do {
              println(s"Please enter in the quantity of $item you wish to buy (max: $maxPurchasable), or 'e' to return to the shop menu: ");
              try {
                input = readLine();
                if (!input.equals("e")) {
                  purchaseAmount = input.toInt
                  if (input.toInt < 1 || input.toInt > maxPurchasable)
                    println("That is not a valid quantity! Please try again!");
                }
              } catch {
                case a : NumberFormatException => {
                  println("That is not a valid number! Please try again!");
                }
              }
            } while (!input.equals("e") && (purchaseAmount < 1 || purchaseAmount > maxPurchasable));

            if (!input.equals("e")) {
              DBManager.buyItem(player_id, item, purchaseAmount, DBManager.getPlayersInfo().filter(p => p._1 == player_id).head._5);
              if (purchaseAmount == 1)
                println(s"Purchased $purchaseAmount $item.")
              else
                println(s"Purchased $purchaseAmount ${item}s.")

              print("Press enter to continue...");
              val toss:String = readLine();
            }
          }
        }
        case "e" => state = "farm";
      }
    }
  }

  private def bankAccount() : Unit = {
    state = "bank account";
    View.bankAccount(player_id);
    val inputOptions:List[String] = List("p", "e");
    var input:String = "";

    while (state.equals("bank account")) {
      do {
        print("Please select an action: ");
        input = readLine();
        if (!inputOptions.contains(input))
          println("Invalid option, please try again!");
      } while (!inputOptions.contains(input));

      input.toLowerCase match {
        case "p" => {
          val account: Map[String, Int] = DBManager.getBankAccount(player_id);
          val maxPaymentAmount: Int = Math.min(account("balance"), account("debt"));
          var paymentAmount: Int = -1;
          if (maxPaymentAmount == 0)
            println("You have no more debt to pay off!");
          else {
            do {
              print(s"Please enter the amount you wish to pay (0-$maxPaymentAmount): ");
              input = readLine();
              try {
                paymentAmount = input.toInt;
                if (paymentAmount < 0 && paymentAmount > maxPaymentAmount)
                  println("That is not a valid amount, please try again!");
              } catch {
                case n: NumberFormatException => {
                  println("That is not a valid number, please try again!");
                }
              }
            } while (paymentAmount < 0 && paymentAmount > maxPaymentAmount);

            if (paymentAmount == 0)
              println("You decided not to pay down your debt.");
            else {
              DBManager.payDebt(player_id, paymentAmount);
              println(s"You paid $paymentAmount towards your debt.");
            }
          }
          print("Press enter to continue...");
          val toss: String = readLine();
        }
        case "e" => state = "farm";
      }
    }
  }

  private def resultScreen() : Unit = {
    View.finalResults(player_id);
    print("Press enter to return to the main menu...");
    val toss:String = readLine();
    state = "main menu";
  }
}