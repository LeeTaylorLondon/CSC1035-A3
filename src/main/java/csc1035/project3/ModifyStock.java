package csc1035.project3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ModifyStock extends CreateReadUpdateDelete {

    private static CreateReadUpdateDelete crudObj = new CreateReadUpdateDelete();

    /**
     * This method changes the stock of an item
     * to any given value that is positive.
     * Such that passing the name of the item
     * and it's new stock value will set the
     * stock of the specified item to the given
     * value regardless of it's current value.
     *
     * @param itemName
     *        Determines the item to change
     *
     * @param newStock
     *        Specifies the new number of stock
     *
     * @author Lee Taylor
     * */
    public static void updateStock(String itemName, int newStock){
        // Creates a connection between Intellij and Database
        crudObj.updateStockValue(newStock, itemName);
    }


    /**
     * This method increases the stock amount of an item given.
     * For example if "Eggs" were stored and it's stock as 25,
     * passing "Eggs" and 25 to this method would increase the
     * stock of Eggs from it's current value to 50.
     *
     * @param itemName
     *        Specifies which stock
     *        item to increase
     *
     * @param addStock
     *        Specifies how much
     *        to increase by
     *
     * @author Lee Taylor
     * */
    public static void increaseStock(String itemName, int addStock){

        if (DatabaseStatistics.hashMapItemStock.containsKey(itemName.toLowerCase()) && addStock > 0){
            int newStock = DatabaseStatistics.hashMapItemStock.get(itemName.toLowerCase()) + addStock;

            crudObj.updateStockValue(newStock, itemName);
        }
    }


    /**
     * This method decreases the stock amount of an item given.
     * For example if "Eggs" were stored and it's stock is 25,
     * passing "Eggs" and 25 to this method would decrease the
     * stock of Eggs from it's current value to 0.
     *
     * @param itemName
     *        Specifies which stock
     *        item to increase
     *
     * @param minusStock
     *        Specifies how much
     *        to increase by
     *
     * @author Lee Taylor
     * */
    public static void decreaseStock(String itemName, int minusStock){

        DatabaseStatistics.extractStockInformation();

        if (DatabaseStatistics.hashMapItemStock.containsKey(itemName.toLowerCase()) && minusStock > 0){
            int newStock = DatabaseStatistics.hashMapItemStock.get(itemName.toLowerCase()) - minusStock;

            if (newStock > -1) {
                crudObj.updateStockValue(newStock, itemName);
            } else
                System.out.println("\nINVALID AMOUNT TO DECREASE BY - DATABASE NOT MODIFIED!");
        }
    }

    /**
     * This method allows for the user to input stock.
     * Try-catch-exceptions are used to prevent the
     * user from inputting incorrect data types.
     *
     * When a user does enter an invalid data type
     * such as "nope" for, boolean perishable, then
     * the current input is completely disregarded.
     *
     * @author Lee Taylor
     * */
    public static void insertStock(){

        Scanner scanner = new Scanner(System.in);

        String itemName = "";
        String category = "";
        boolean perishable;
        float cost = -0.1f;
        int stock = -1;
        float sellPrice = -6.0f;
        String choice = "";

        while (itemName.equals("")) {
            System.out.print("Input Item Name: ");  // ITEM NAME
            itemName = scanner.nextLine();
        }

        while (category.equals("")) {
            System.out.print("Input Category: ");   // CATEGORY
            category = scanner.nextLine();
        }

        System.out.print("('true' OR 'false') Input Perishable: ");     // PERISHABLE?
        try {
            perishable = scanner.nextBoolean();
        } catch (InputMismatchException e){
            System.out.println("\nInvalid Input For Perishable ('true' OR 'false')");
            System.out.println(" - Invalid Stock Item NOT Recorded. Please Try Again!");
            return;
        }

        try {
            while (cost <= 0 ) {
                System.out.print("(Cannot Be A Negative Value) Input Cost: ");       // COST
                cost = scanner.nextFloat();
            }
        } catch (InputMismatchException e){
            System.out.print("\nInvalid Input For Cost! - Invalid Stock Item NOT Recorded. Please Try Again!");
            return;
        }

        try {
            while (stock <= 0) {
                System.out.print("Input Stock: ");      // STOCK
                stock = scanner.nextInt();
            }
        } catch (InputMismatchException e){
            System.out.print("\nInvalid Input For Stock! - Invalid Stock Item NOT Recorded. Please Try Again!");
            return;
        }

        try {
            while (sellPrice <= 0) {
                System.out.print("Sell Price: ");       // SELL_PRICE
                sellPrice = scanner.nextFloat();
            }
        } catch (InputMismatchException e){
            System.out.print("\nInvalid Input For Sell Price! - Invalid Stock Item NOT Recorded. Please Try Again!");
            return;
        }

        Scanner scannerChoice = new Scanner(System.in);

        DatabaseStatistics.extractStockInformation();
        if (DatabaseStatistics.hashMapItemStock.containsKey(itemName.toLowerCase())){
            System.out.println("\nAn item already exists with that name!");
            System.out.println("Stock item disregarded");
        } else {

            Stock stockObj = new Stock(itemName, category, perishable, cost, stock, sellPrice);

            System.out.println("\nCurrent Object's Attributes To Insert: " + stockObj);
            System.out.print("\nPlease type 'INSERT' to insert the entry. " +
                    "Typing anything else will DISREGARD the entire input: ");

            choice = scannerChoice.nextLine();

            if (choice.equals("INSERT")) {

                crudObj.create(stockObj);
                DatabaseStatistics.hashMapItemStock.put(itemName.toLowerCase(), stock);
                System.out.println("Added Stock Item To Database!");

            } else {
                System.out.println("Disregarded stockObj");
            }
        }
    }

    /**
     * This method deletes a stock item
     * within the database.
     *
     * This method takes user input for
     * them to specify an item within the
     * database, if the item's name is
     * spelled correctly then the user
     * is offered a choice to confirm they
     * wish to delete the item. If the
     * specified item cannot be found,
     * then the database is not affected
     * and the user is informed.
     *
     * @author Lee Taylor
     * */
    public static void deleteStock(){

        Scanner scanner = new Scanner(System.in); // Scanner/Input Object Constructed

        System.out.print("Enter Item Name: "); // Prompt Input item-name to delete
        String itemName = scanner.nextLine();

        if (DatabaseStatistics.hashMapItemStock.containsKey(itemName.toLowerCase())){
            String choice = ""; // If the item exists THEN give the user a choice to delete the item
            while(!(choice.equals("y")) && !(choice.equals("n"))) {
                System.out.print("Are you sure you wish to PERMANENTLY DELETE " +
                        "this item, " + itemName + ", from the database (y/n): ");
                choice = scanner.nextLine().toLowerCase();
            }
            if (choice.equals("y")){
                // If their choice is "y" then generate SQL to delete the item in Stock
                crudObj.deleteByName(itemName);
                System.out.println("Item, " + itemName + ", Deleted!");
            } else
                // If their choice is "n" then the item is not deleted
                System.out.println("Item, "+itemName+", Not Deleted!");

        } else
            // If the specified item cannot be found then the database is not affected and the user is informed
            System.out.println("Item does not exist!");

    }

}
