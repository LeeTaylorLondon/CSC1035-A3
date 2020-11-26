package csc1035.project3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Output {

    /**
     * This method generates a count of available stock, displayed in a
     * table of contents. A method is run beforehand to extract all
     * necessary information from the database. This information is then
     * analysed to calculate the width of the name column and then draw
     * the column. The extracted information is outputted below in a
     * pretty format (ASCII table).
     *
     * @see DatabaseStatistics#extractStockInformation()
     * @see #calculateItemNameWidth()
     * @see #drawItemNameWidth()
     *
     * @author Lee Taylor
     * */
    public static void viewCountOfAvailableStock(){

        int itemNameWidth = calculateItemNameWidth();
        String itemNameDashes = drawItemNameWidth();

        String quantityDashes = "---------------";

        System.out.format("+%s+%s+\n",itemNameDashes, quantityDashes);
        System.out.format("|%"+itemNameWidth+"s|%15s|\n","Item Name","Quantity");
        System.out.format("+%s+%s+\n",itemNameDashes, quantityDashes);

        for (String item : DatabaseStatistics.hashMapItemStock.keySet()){
            System.out.format("|%"+itemNameWidth+"s|%15s|\n", item, DatabaseStatistics.hashMapItemStock.get(item.toLowerCase()),
                    DatabaseStatistics.hashMapItemStock.get(item.toLowerCase()));
        }

        System.out.format("+%s+%s+\n",itemNameDashes, quantityDashes);

    }

    /**
     * This method returns the length of the longest name
     * in the database. As all the item names are extracted
     * from the database beforehand.
     *
     * @return {@code int}
     *         The length of the longest name
     *
     * @see #drawItemNameWidth()
     * @see #viewCountOfAvailableStock()
     *
     * @author Lee Taylor
     * */
    public static int calculateItemNameWidth(){

        int itemNameWidth = 15;
        for (String itemName : DatabaseStatistics.hashMapItemStock.keySet()){
            if (itemName.length() > itemNameWidth)
                itemNameWidth = itemName.length();
        }
        return itemNameWidth;
    }

    /**
     * This method returns a String, which is the name-column
     * as a string of dashes i.e "---------" which is used to
     * visually build the table.
     *
     * @return {@code String}
     *        Returns a string of dashes where the number of
     *        dashes is equal to the longest item name
     *
     * @see #calculateItemNameWidth()
     * @see #viewCountOfAvailableStock()
     *
     * @author Lee Taylor
     * */
    public static String drawItemNameWidth(){

        int itemNameWidth = calculateItemNameWidth();
        String itemNameDashes = "";
        for (int i = 0; i < itemNameWidth; i++){
            itemNameDashes = itemNameDashes.concat("-");
        }

        return itemNameDashes;

    }

    /**
     * This method draws a pre-defined header with three
     * columns - item name - quantity and price. Used in
     * receipts and viewing stock.
     *
     * @see CustomerTransaction#searchItem()
     * @see CustomerTransaction#viewStockInventory()
     *
     * @author Lee Taylor
     * */
    public static void drawHeader1(){

        int itemNameWidth = Output.calculateItemNameWidth();
        String itemNameDashes = Output.drawItemNameWidth();

        String quantityDashes = "---------------";
        String priceQuantityDashes = "---------------";

        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);
        System.out.format("|%"+itemNameWidth+"s|%15s|%15s|\n","Item Name","In Stock","Price");
        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);

    }

    /**
     * This method draws a pre-defined closer for three
     * columns. Used in receipts and viewing stock.
     *
     * @see CustomerTransaction#searchItem()
     * @see CustomerTransaction#viewStockInventory()
     *
     * @author Lee Taylor
     * */
    public static void drawCloser1(){

        String itemNameDashes = Output.drawItemNameWidth();
        String quantityDashes = "---------------";
        String priceQuantityDashes = "---------------";
        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);

    }

    /**
     * Outputs an SQL table of the stock into a CSV file.
     *
     * Modified to use interface. Such that the data extracted
     * from the database is done so using the pre-defined methods
     * from the CRUD interface-class.
     *
     * @see CreateReadUpdateDelete#read(String)
     *
     * @author Tiger Kato
     * */
    public static void outputToCSV (){

        CreateReadUpdateDelete crudObj = new CreateReadUpdateDelete();
        List results = crudObj.read("name");
        List results2 = crudObj.read("category");
        List results3 = crudObj.read("perishable");
        List results4 = crudObj.read("cost");
        List results5 = crudObj.read("stock");
        List results6 = crudObj.read("sell_price");

        try{
            FileWriter fw = new FileWriter("stock_output.csv");
            BufferedWriter bw = new BufferedWriter(fw) ;
            for (int i = 0; i < results.size() ; i++) {
                bw.write(results.get(i) + "," + results2.get(i) + "," + results3.get(i) + ","
                        + results4.get(i) + "," + results5.get(i) + "," + results6.get(i) + "\n");
                fw.flush();
                bw.flush();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /**
     * @author Sofia T
     *
     * The stockTableCreator creates a stock table of all
     * the items currently in the database. Each column has the
     * ability to grow or shrink depending on the length of the
     * item's name or price for example, minus the "Perishable",
     * column, which doesn't grow or shrink as the word is already
     * longer than the length of, "true", or, "false", which are
     * the only two options for that column.
     *
     * @param stock
     *        An ArrayList of all the stock items currently in the database.
     * */

    public static String stockTableCreator(List<Stock> stock){
        //name,category,perishable,cost,stock,sell_price
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> perishable = new ArrayList<>();
        ArrayList<String> cost = new ArrayList<>();
        ArrayList<String> stockNum = new ArrayList<>();
        ArrayList<String> price = new ArrayList<>();

        //name column-----------------------------------------------------------
        int maxWidth = 6; //"name" + one space on each side.
        int finalNameLen = 0;

        for(Stock items: stock){
            //System.out.println(items.toString());
            if(items.getName().length() > maxWidth){
                //System.out.println(items);
                maxWidth = items.getName().length();
            }
        }

        String space = " ";
        //System.out.println(maxWidth);

        int index = 0;
        for(Stock each : stock){
            name.add(each.getName());
            int multiplier = 0;

            multiplier = maxWidth - each.getName().length();

            if(multiplier != 0){
                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }

                name.set(index,"|" + each.getName() + space );
            } else {
                name.set(index,"|" + each.getName() + " ");
            }
            space = " ";

            index++;
        }
        finalNameLen = maxWidth + 1;

        //category column--------------------------------------------------------------

        maxWidth = 10; //"category" + one space on each side.
        int finalCatLen = 0;

        for(Stock items: stock){
            if(items.getCategory().length() > maxWidth){
                maxWidth = items.getCategory().length();
            }
        }

        space = " ";
        index = 0;
        for(Stock each : stock){
            category.add(each.getCategory());
            int multiplier = 0;

            multiplier = maxWidth - each.getCategory().length();

            if(multiplier != 0){

                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }

                category.set(index,"|" + each.getCategory() + space );
            } else {
                category.set(index,"|" + each.getCategory()+ " ");
            }
            space = " ";
            index++;
        }
        finalCatLen = maxWidth +1;

        //perishable column-------------------------------------------------------------

        maxWidth = 12; //"Perishable" + one space on each side.
        int finalPerLen = 0;
        space = " ";

        index = 0;
        for(Stock each : stock){
            perishable.add(each.getCategory());
            int multiplier = 0;
            multiplier = maxWidth - String.valueOf(each.getPerishable()).length();
            if(multiplier != 0){
                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }
                perishable.set(index,"|" + each.getPerishable() + space);
            }
            space = " ";
            index++;
        }
        finalPerLen = maxWidth +1 ;

        //cost column------------------------------------------------------------------------

        maxWidth = 6; //"cost" + one space on each side.
        int finalCosLen = 0;
        for(Stock items: stock){
            String s = Float.toString(items.getCost());
            if(s.length() > maxWidth){
                maxWidth = s.length();
            }
        }

        space = " ";
        index = 0;
        for(Stock each : stock){
            String s = Float. toString(each.getCost());
            cost.add(s);
            int multiplier = 0;
            multiplier = maxWidth - s.length();
            if(multiplier != 0){
                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }
                cost.set(index,"|" + each.getCost() + space);
            } else {
                cost.set(index,"|" + each.getCost()+ " ");
            }
            space = " ";

            index++;
        }
        finalCosLen = maxWidth + 1 ;

        //stock column------------------------------------------------------------------------

        maxWidth = 7; //"stock" + one space on each side.
        int finalStoLen = 0;

        for(Stock items: stock){
            String s = Integer.toString(items.getStock());
            //System.out.println(items.toString());
            if(s.length() > maxWidth){
                //System.out.println(items);
                maxWidth = s.length();
            }
        }

        space = " ";

        index = 0;
        for(Stock each : stock){
            String s = Integer. toString(each.getStock());
            stockNum.add(s);
            int multiplier = 0;
            multiplier = maxWidth - s.length();
            if(multiplier != 0){
                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }
                stockNum.set(index,"|" + each.getStock() + space);
            } else {
                stockNum.set(index,"|" + each.getStock()+ " ");
            }
            space = " ";
            index++;
        }
        finalStoLen = maxWidth + 1 ;

        //price column---------------------------------------------------------------------

        maxWidth = 7; //"price" + one space on each side.
        int finalPriLen = 0;
        for(Stock items: stock){
            String s = Float.toString(items.getSell_price());

            if(s.length() > maxWidth){
                maxWidth = s.length();
            }
        }
        space = " ";
        index = 0;
        for(Stock each : stock){
            String s = Float.toString(each.getSell_price());
            price.add(s);
            int multiplier = 0;
            multiplier = maxWidth - s.length();
            if(multiplier != 0){
                for(int i = 0; i < multiplier; i++){
                    space = space + " ";
                }
                price.set(index,"|" + each.getSell_price() + space + "|");
            } else {
                price.set(index,"|" + each.getSell_price()+ " |");
            }
            space = " ";
            index++;
        }
        finalPriLen = maxWidth + 1;

        //TABLE---------------------------------------------------------------------

        //Border line section
        String lines = "+";
        for(int i = 0 ; i <finalNameLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        for(int i = 0 ; i <finalCatLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        for(int i = 0 ; i <finalPerLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        for(int i = 0 ; i <finalCosLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        for(int i = 0 ; i <finalStoLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        for(int i = 0 ; i <finalPriLen; i++){
            lines = lines + "-";
        }
        lines = lines + "+";
        System.out.println(lines); //Top Border Line


        //Column name section

        String columnNames = "| Name";
        for(int i = 0; i <= finalNameLen - 6; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "| Category";
        for(int i = 0; i <= finalCatLen - 10; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "| Perishable";
        for(int i = 0; i <= finalPerLen - 12; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "| Cost";
        for(int i = 0; i <= finalCosLen - 6; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "| Stock";
        for(int i = 0; i <= finalStoLen - 7; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "| Price";
        for(int i = 0; i <= finalPriLen - 7; i++){
            columnNames = columnNames + space;
        }
        columnNames = columnNames + "|";

        System.out.println(columnNames); //Column Titles
        System.out.println(lines); //Middle Border Line

        //Body section
        String totalTable = lines + "\n" + columnNames + "\n" + lines;

        for(int i = 0; i < stock.size(); i++){ //Body section
            //name,category,perishable,cost,stock,sell_price
            System.out.println(name.get(i) + category.get(i) + perishable.get(i) + cost.get(i) + stockNum.get(i) +
                    price.get(i));
            //Adds to the string that we can use to pass through to the toTXT method.
            String row = name.get(i) + category.get(i) + perishable.get(i) + cost.get(i)+ stockNum.get(i) +
                    price.get(i);

            totalTable = totalTable + "\n" + row;

        }
        System.out.println(lines); //Bottom Border Line
        totalTable = totalTable +  "\n" + lines;
        return totalTable;
    }

    /**
     * The toArrayList method creates an ArrayList of all the items in
     * the Stock Database when it is called. This is done because the
     * ASCII table is created by taking in an ArrayList of stock objects.
     *
     * @author Sofia Trevino
     * */

    public static ArrayList toArrayList(){

        CreateReadUpdateDelete object = new CreateReadUpdateDelete();

        List names = object.read("name");
        List category = object.read("category");
        List perishable = object.read("perishable");
        List cost = object.read("cost");
        List stock = object.read("stock");
        List sell_price = object.read("sell_price");

        ArrayList<Stock> stockArrayList = new ArrayList<>();

        for(int i = 0; i < names.size(); i++){
            //Converts the objects to the proper types for the Stock item.
            boolean perish = Boolean.parseBoolean(perishable.get(i).toString());
            float cos = Float.parseFloat(cost.get(i).toString());
            int stoc = Integer.parseInt(stock.get(i).toString());
            float price = Float.parseFloat(sell_price.get(i).toString());


            stockArrayList.add(new Stock(names.get(i).toString(),category.get(i).toString(),perish,cos,stoc,price));
        }
        return stockArrayList;
    }

    /**
     * @author Sofia T
     *
     * Outputs the content from the stockTableCreator (An ASCII table),
     * to a txt file with the user-specified name. Above the ASCII
     * table is also the date and time from of when the data was pulled
     * from the table.
     *
     * @param fileName
     *        This is the file name specified by the user through the
     *        Scanner in the main method. The user does not need to
     *        add ".txt", to the end of the name, as it will be added
     *        within the method.
     * */

    public static void toTXT(String fileName){

        try {
            //Creates the new file
            PrintStream stock = new PrintStream(new File(fileName +".txt"));

            //Adds the current date to the top of the txt file.
            java.util.Date date=new java.util.Date();
            stock.println(date);

            //prints the output from the stockTableCreator to the txt file.
            stock.println(stockTableCreator(toArrayList()));
            stock.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
