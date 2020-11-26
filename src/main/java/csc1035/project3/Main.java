package csc1035.project3;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains a single method to
 * offer the user choices on how to interact
 * with the system. The choices displayed are
 * methods from the other classes. Using a
 * case-switch the user may specify the
 * method/command to select.
 *
 * @see CustomerTransaction#beginCustomerTransaction()
 * @see ModifyStock#insertStock()
 * @see ModifyStock#updateStock(String, int)
 * @see ModifyStock#increaseStock(String, int)
 * @see ModifyStock#decreaseStock(String, int)
 * @see ModifyStock#deleteByName(String)
 * @see Output#viewCountOfAvailableStock()
 * @see Output#outputToCSV()
 * @see Output#stockTableCreator(List)
 * */
public class Main {

    public static void main(String[] args) throws IOException {

        // ------[Beginning Of Main]------

        // Scanner Object Constructed
        Scanner scanner = new Scanner(System.in);

        String choice;

        while (true) {
            System.out.println("\n--------[Main Menu]--------");
            System.out.println("Select an Option Below");
            System.out.println("1 - Open [Customer Transaction] Menu");
            System.out.println("2 - Open [Modify Stock] Menu");
            System.out.println("3 - Open [Output] Menu");
            System.out.println("4 - Insert Data From CSV");
            System.out.println("5 - View Count of Available Stock");

            System.out.print("Enter choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    DatabaseStatistics.extractStockInformation();
                    CustomerTransaction.beginCustomerTransaction();
                    break;

                case "2":
                    modifyStockMenu();
                    break;

                case "3":
                    outputMenu();
                    break;

                case "4":
                    System.out.print("Enter directory + filename to extract from: ");
                    String csvPath = inputString();
                    CSVReader.loadIn(csvPath);
                    break;

                case "5":
                    DatabaseStatistics.extractStockInformation();
                    Output.viewCountOfAvailableStock();
                    break;

            }
        }
    }

    /**
     * This method is used to take input
     * from the user. This method was made
     * to significantly reduce code duplication.
     *
     * @return {@code String} returns the user's
     *         input which is of type String.
     * */
    private static String inputString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This method displays all modify stock methods separate
     * from the main menu to decrease the number of options
     * displayed on the main menu preventing the user from being
     * overwhelmed.
     *
     * @author Lee Taylor
     * */
    public static void modifyStockMenu(){

        boolean modify = true;
        String choice = "";
        Scanner scanner = new Scanner(System.in);

        while(modify){
            // Modify Stock Methods
            System.out.println("\n--------[Modify Stock Menu]--------");
            System.out.println("1 - [Insert] Stock Item To Database");
            System.out.println("2 - [Set] Stock Of An Item");
            System.out.println("3 - [Increase] Stock Of An Item");
            System.out.println("4 - [Decrease] Stock Of An Item");
            System.out.println("5 - [Delete] Stock Item From Database");
            System.out.println("6 - View Database Content's");
            System.out.println("7 - Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextLine();

            switch (choice){
                case "1":
                    // INSERT STOCK ITEM INTO DATABASE
                    ModifyStock.insertStock();
                    break;

                case "2":
                    // CHANGE STOCK OF AN ITEM
                    System.out.print("\nEnter item name: ");
                    String itemName = inputString();
                    String newStock = "";
                    // Preventing Stock From Being a Null Value and a Negative Value + Input New Value For Stock
                    while(newStock.equals("")) {
                        System.out.print("Enter new stock: ");
                        newStock = inputString();
                        newStock = newStock.replaceAll("[^0-9]", "");
                    }
                    // After New Stock Value Has Been Validated It is Parsed as an int
                    int intNewStock = Integer.parseInt(newStock);
                    ModifyStock.updateStock(itemName, intNewStock);
                    break;

                case "3":
                    // INCREASE STOCK OF AN ITEM
                    System.out.print("\nEnter Item Name: "); // Input Item Name
                    String itemNameIncrease = inputString();
                    String addStock = "";
                    // Preventing Stock From Being a Null Value and a Negative Value + Input New Value For Stock
                    while(addStock.equals("")) {
                        System.out.print("Enter Stock Increase: ");
                        addStock = inputString();
                        addStock = addStock.replaceAll("[^0-9-]", "");
                    }
                    // After New Stock Value Has Been Validated It is Parsed as an int
                    int intAddStock = Integer.parseInt(addStock);

                    DatabaseStatistics.extractStockInformation();
                    ModifyStock.increaseStock(itemNameIncrease, intAddStock);
                    break;

                case "4":
                    // DECREASE STOCK OF AN ITEM
                    System.out.print("\nEnter Item Name: "); // Input Item Name
                    String itemNameDecrease = inputString();
                    String minusStock = "";
                    // Preventing Stock From Being a Null Value and a Negative Value + Input New Value For Stock
                    while(minusStock.equals("")) {
                        System.out.print("Enter Stock Decrease: ");
                        minusStock = inputString();
                        minusStock = minusStock.replaceAll("[^0-9]", "");
                    }
                    // After New Stock Value Has Been Validated It is Parsed as an int
                    int intMinusStock = Integer.parseInt(minusStock);
                    ModifyStock.decreaseStock(itemNameDecrease, intMinusStock);
                    break;

                case "5":
                    // DELETE AN ITEM
                    DatabaseStatistics.extractStockInformation();
                    ModifyStock.deleteStock();
                    break;

                case "6":
                    Output.stockTableCreator(Output.toArrayList());
                    break;

                case "7":
                    // BACK
                    modify = false;
                    break;
            }

        }

    }

    public static void outputMenu(){

        // Scanner Object Constructed
        Scanner scanner = new Scanner(System.in);

        boolean output = true;
        String choice;

        while (output) {
            System.out.println("\n--------[Output Menu]--------");
            System.out.println("Select an Option Below");
            System.out.println("1 - Output Database Contents to .csv file");
            System.out.println("2 - Output Database Contents to .txt file");
            System.out.println("3 - View Database Content's");
            System.out.println("4 - Back");

            System.out.print("Enter choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Output.outputToCSV();
                    System.out.println("\nDatabase Contents Outputted @ top-level directory -> 'stock_output.csv'");
                    break;

                case "2":
                    System.out.println("What would you like to call the text file? (excluding the '.txt', ending)");
                    Scanner userInput = new Scanner(System.in);
                    String answer = userInput.nextLine();
                    Output.toTXT(answer);
                    break;

                case "3":
                    Output.stockTableCreator(Output.toArrayList());
                    break;

                case "4":
                    output = false;
                    break;
            }
        }

    }

}
