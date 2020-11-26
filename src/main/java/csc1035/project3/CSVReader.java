package csc1035.project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class CSVReader {

    public CSVReader(){ }

    /**
     * The loadIn method reads in a CSV file from a specified
     * string path. Before adding a stock item to the fullStock
     * ArrayList, the item is checked for any issues such as
     * invalid boolean values, invalid doubles in cost and price,
     * and invalid integers in the amount of stock of the particular
     * item. If the item passes the tests, it will
     * be added to the fullStock ArrayList, and then the ArrayList
     * is looped through and each item is added to the database.
     *
     * Modified to use CreateReadUpdateDelete interface-class.
     * And to disallow duplicates from entering the database,
     * items with the same name are disallowed.
     *
     * @param path
     *        Specifies CSV file
     *
     * @author Sofia Trevino
     * */

    public static void loadIn(String path){
        ArrayList<Stock> fullStock0 = new ArrayList<>();

        //https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/ (USED CODE FROM THIS)

        //Mkyong.com. (2016). How to read and parse CSV file in Java – Mkyong.com. [online]
        //Available at: https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/ [Accessed 21 Feb. 2020].

        String currentLine = "";
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((currentLine = br.readLine()) != null) {
                // uses comma to separate each item on the line
                String[] stockItem = currentLine.split(splitBy);
                System.out.println(Arrays.toString(stockItem));
                boolean invalid = false;
                float cost = 0;
                int stock = 0;
                float price = 0;

                if(!(stockItem[2].toLowerCase().equals("true")) && !(stockItem[2].toLowerCase().equals("false"))){
                    invalid = true;
                }
                //checks if the cost, stock, and price items are numbers, not letters/other characters
                try {
                    //changing types to fit with stock criteria
                    cost = Float.parseFloat(stockItem[3]);
                    stock = Integer.parseInt(stockItem[4]);
                    price = Float.parseFloat(stockItem[5]);
                    System.out.println(Arrays.toString(stockItem));

                } catch(NumberFormatException e){
                    invalid = true;
                }

                if(stockItem.length != 6 || invalid){

                } else{
                    //changing types to fit with stock criteria
                    String name = stockItem[0];
                    String category = stockItem[1];
                    boolean perish = Boolean.parseBoolean(stockItem[2]);
                    //Creates a new stock item and adds it to the fullStock0 ArrayList
                    Stock item = new Stock(name,category,perish,cost,stock,price);
                    fullStock0.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("File Not Extracted From!");
        }

        CreateReadUpdateDelete crudObj = new CreateReadUpdateDelete();
        DatabaseStatistics.extractStockInformation();
        for (Stock stock : fullStock0){ //loops through the fullStock0 ArrayList and adds each to the database
            if (DatabaseStatistics.hashMapItemStock.containsKey(stock.getName().toLowerCase())){}
            else {
                crudObj.create(stock);
                DatabaseStatistics.hashMapItemStock.put(stock.getName().toLowerCase(), stock.getStock());
            }
        }
    }

}
