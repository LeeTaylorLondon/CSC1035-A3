package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

public class CustomerTransaction {
    /**
     * This HashMap is used as a shopping list
     * to store the current items to buy and
     * how many. Where the key is the item
     * and the value is the quantity to buy.
     *
     * The reason for using a hashMap is
     * because an item only needs to be mentioned
     * once.
     *
     * Using a HM also prevents the scenario in
     * which, one could buy an item twice
     * specifying a valid amount of stock but
     * the total stock sums to more than a
     * valid amount. i.e the shopping list
     * contains item X twice where the buy
     * amount is [x,5] and [x,6] buying 11 of X
     * but the stock for, x, is 6. A hashMap
     * prevents this as specifying an item
     * that already exists overwrites the current
     * <k,v> pair.
     * */
    public static HashMap<String, Integer> shoppingList = new HashMap<>();

    /**
     * This boolean simply flags whether or not a transaction
     * has ended or not.
     * */
    private static boolean endTransaction = false;

    /**
     * This method allows for the user to choose five options
     * whilst a customer transaction is taking place.
     *
     * Whilst a transaction is taking place one may, add items
     * to the transaction. Each item added, is checked to exist
     * within the database and they are buying a valid amount.
     *
     * One may remove an item from the current transaction.
     *
     * One may cancel the entire current transaction, having
     * no affect on the database.
     *
     * One may finish the transaction. The total price, and change is
     * calculated based on the money given and items bought.
     *
     * One may view the entire stock outputted as an ASCII table.
     *
     * One may search an item by name and it's stock and price is
     * outputted in a small ASCII table.
     *
     * @see #buyItem()
     * @see #viewCurrentShoppingList()
     * @see #removeAnItem()
     * @see #viewStockInventory()
     * @see #searchItem()
     * @see #payForTransaction()
     * @see #processTransaction(HashMap, float)
     *
     * @author Lee Taylor
     * */
    public static void beginCustomerTransaction() throws IOException {

    Scanner scanner = new Scanner(System.in);

    String choice;

        endTransaction = false;

        while(!endTransaction) {
            System.out.println("\n[Customer Transaction Menu ~ Transaction: In-Progress]");
            // Buyer Choices
            System.out.println("1 - Buy Another Item");
            System.out.println("2 - View Shopping List");
            System.out.println("3 - Remove an Item");
            System.out.println("4 - Cancel Transaction");
            System.out.println("5 - Finish Transaction");
            System.out.println("[Search/Navigation Options]");
            System.out.println("6 - View Current Stock");
            System.out.println("7 - Search an Item");

            System.out.print("\nEnter choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // BUY ITEM
                    buyItem();
                    break;

                case "2":
                    // VIEW SHOPPING LIST
                    viewCurrentShoppingList();
                    break;

                case "3":
                    // REMOVE AN ITEM - REMOVE SPECIFIED ITEM FROM LIST
                    removeAnItem();
                    break;

                case "4":
                    // CANCEL TRANSACTION - CLEAR LIST OF ITEMS
                    shoppingList.clear();
                    endTransaction = true;
                    break;

                case "5":
                    payForTransaction();
                    break;

                case "6":
                    viewStockInventory();
                    break;

                case "7":
                    searchItem();
                    break;

            }
        }
    }

    /**
     * This method calculates the total price
     * for the current transaction taking place.
     * The user is prompted to enter an amount given,
     * however if the price is too high for the customer
     * the option to go back, is given by typing '0'.
     * The purpose of this process taking place before
     * DB transactions to take place is to give the user
     * the option to go back.
     *
     * @author Lee Taylor
     * */
    public static void payForTransaction() throws IOException {
        Scanner scanner = new Scanner(System.in);
        float total = 0;
        float moneyGiven = -1f;

        // Calculates the total price for the transaction
        for (String itemName : CustomerTransaction.shoppingList.keySet()){
            total += DatabaseStatistics.hashMapItemPrice.get(itemName)*CustomerTransaction.shoppingList.get(itemName);
        }

        // Checks if the transaction's total is 0
        if (total == 0){
            System.out.println("Cannot finish a transaction with no items!");
            return;
        }

        // Displays total price
        System.out.println("\nCurrent Total: "+total); // DEBUG
        System.out.println("Type '0' to return to the previous menu.");

        // Accepts user input of money given
        while (moneyGiven < total){
            System.out.print("Enter Amount Given: ");
            try {
                moneyGiven = scanner.nextFloat();
            } catch (InputMismatchException e){
                System.out.println("Invalid Input!");
                moneyGiven = 0;
                break;
            }
            if (moneyGiven == 0)
                break;
        }

        // If a valid amount of money is given - DB transactions take place and a receipt is generated
        if (moneyGiven != 0) {
            processTransaction(shoppingList, moneyGiven);
            endTransaction = true;
            shoppingList.clear();
            CreateReadUpdateDelete CRD = new CreateReadUpdateDelete();
            Receipt.printReceipt((int)CRD.readWithCondition("MAX(tid)","Transaction","").get(0));
        }
    }

    /**
     * This method is used to take input
     * from the user. This method was made
     * to significantly reduce code duplication.
     *
     * @return {@code String} returns the user's
     *         input which is of type String.
     *
     * @author Lee Taylor
     * */
    private static String inputString(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * This method allows the user to add an item and how much
     * of that item to buy to a list.
     *
     * Each item and quantity specified is validated before adding to
     * the shopping list.
     *
     * A method extracting stock information from the database
     * is run beforehand, so when buying an item it's existence
     * and stock amount can be validated using the information
     * extracted from the database.
     *
     * @see DatabaseStatistics#extractStockInformation()
     *
     * @author Lee Taylor
     * */
    public static void buyItem(){

        System.out.print("Enter Item-Name: ");
        String itemName = inputString();

        System.out.print("Enter Quantity To Buy: ");
        String buyAmount = inputString();
        buyAmount = buyAmount.replaceAll("[^0-9]","");

        int quantityToBuy = -1;
        try {
            quantityToBuy = Integer.parseInt(buyAmount);
        } catch (NumberFormatException ignore){} // If quantityToBuy is invalid it'll be set to -1

        try { // If (The item does not exist) OR (The quantity to buy is greater than the stock)
            // OR (The quantity to buy is less than 0) THEN [Do not add the item to the shopping list]
            if (!DatabaseStatistics.hashMapItemStock.containsKey(itemName.toLowerCase())
                    || quantityToBuy > DatabaseStatistics.hashMapItemStock.get(itemName.toLowerCase())
            || quantityToBuy < 0) {
                System.out.println("\nItem Not Added To Shopping List!");
                System.out.println("Invalid Item-Name or Invalid Quantity To Buy!");
            } else {
                // ADD TO SHOPPING LIST
                shoppingList.put(itemName.toLowerCase(), quantityToBuy);
                System.out.println("\nItem Added To Shopping List!");
            }
        } catch (NullPointerException e){
            System.out.println("\nItem Not Added To Shopping List!");
            System.out.println("Invalid Item-Name or Invalid Quantity To Buy!");
        }

    }

    /**
     * This method prints out the current customer transaction
     * in an ASCII table format.
     *
     * This uses methods from the Output class to calculate
     * both the width of the name column and to draw the name column.
     *
     * The current items being bought before they are bought are stored
     * in a HashMap {@code shoppingList} the contents from here are simply
     * extracted and displayed using, .format.
     *
     * @see Output#calculateItemNameWidth()
     * @see Output#drawItemNameWidth()
     *
     * @author Lee Taylor
     * */
    public static void viewCurrentShoppingList(){

        int itemNameWidth = Output.calculateItemNameWidth();
        String itemNameDashes = Output.drawItemNameWidth();

        String quantityDashes = "---------------";
        String priceQuantityDashes = "---------------";

        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);
        System.out.format("|%"+itemNameWidth+"s|%15s|%15s|\n","Item Name","Quantity","Price");
        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);

        for (String item : shoppingList.keySet()){
            System.out.format("|%"+itemNameWidth+"s|%15s|%15s|\n", item, shoppingList.get(item.toLowerCase()),
                    DatabaseStatistics.hashMapItemPrice.get(item.toLowerCase())*shoppingList.get(item.toLowerCase()));
        }

        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);

    }

    /**
     * This method removes an item from the shopping list
     * that stores all items of the current transaction.
     *
     * @see #inputString()
     *
     * @author Lee Taylor
     * */

    public static void removeAnItem(){

        System.out.print("Enter Name of Item You Wish To Remove: ");
        String itemToRemove = inputString();
        itemToRemove = itemToRemove.toLowerCase();

        try {
            shoppingList.remove(itemToRemove.toLowerCase());
            System.out.println("Item: "+itemToRemove+" Removed!");
        } catch (NullPointerException e){
            System.out.println("Item: "+itemToRemove+" Not Removed - Could Not Find This Item!");
        }

    }

    /**
     * This method allows the user to view the current items in
     * stock and their individual prices with their available quantity.
     *
     * @author Lee Taylor
     * */
    public static void viewStockInventory(){

        int itemNameWidth = Output.calculateItemNameWidth();
        String itemNameDashes = Output.drawItemNameWidth();

        Output.drawHeader1();

        for (String item : DatabaseStatistics.hashMapItemStock.keySet()){
            System.out.format("|%"+itemNameWidth+"s|%15s|%15s|\n", item.toUpperCase(),
                    DatabaseStatistics.hashMapItemStock.get(item.toLowerCase()),
                    DatabaseStatistics.hashMapItemPrice.get(item.toLowerCase()));
        }

        String quantityDashes = "---------------";
        String priceQuantityDashes = "---------------";
        System.out.format("+%s+%s+%s+\n",itemNameDashes, quantityDashes, priceQuantityDashes);

    }

    /**
     * This method allows the user to search an
     * item by name and return it's current stock
     * and item price.
     *
     * @author Lee Taylor
     * */
    public static void searchItem(){

        System.out.print("Enter Item Name: ");
        String input = inputString().toLowerCase();

        if (!(DatabaseStatistics.hashMapItemStock.containsKey(input))){
            System.out.println("Item Not Found!");
        } else {
            input = input.toLowerCase();

            Output.drawHeader1();
            int itemNameWidth = Output.calculateItemNameWidth();

            System.out.format("|%"+itemNameWidth+"s|%15s|%15s|\n", input.toUpperCase(),
                    DatabaseStatistics.hashMapItemStock.get(input), DatabaseStatistics.hashMapItemPrice.get(input));
            Output.drawCloser1();

        }

    }

    /**
     * @author Josh Davison
     * class for the implementation of database functions.
     *
     * Modified - payment takes place before DB transactions
     * and - fixed bug where grand total was not recording decimal places
     * 9.75 would become 9, calculating incorrect change. Fixed by changing
     * data type of grandTotal from int to float.
     */
    private static void processTransaction(HashMap<String, Integer> shoppingList, float moneyGiven) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = new Transaction();
        float changeGiven = 0;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<ItemSale> saleList = new ArrayList<>();

            session.beginTransaction();
            for (String k : shoppingList.keySet()) {
                // getting the required stock item with a query
                // (SELECT * FROM Stock WHERE name = itemName)
                Query query = session.createQuery("from Stock s where s.name = :passString"); // HERE
                List results = query.setParameter("passString", k).getResultList();
                // creating the temporary objects
                Object tmp = results.get(0);
                Stock item = (Stock)tmp;
                ItemSale sale = new ItemSale(item, shoppingList.get(k));

                // setting up the ItemSale
                sale.setTid(newTransaction);
                // setting the total price of this particular item
                sale.setTotalPrice((float)item.getSell_price()*sale.getQuantitySold());
                // updating the item in Stock table
                List<ItemSale> addSales =
                        item.getSales();
                addSales.add(sale);
                item.setSales(addSales);
                item.setStock(item.getStock() - sale.getQuantitySold());
                session.update(item); // HERE
                saleList.add(sale);
            }

            newTransaction.setItems(saleList);

            float grandTotal = 0; // CHANGED FROM INT TO FLOAT - PRICE CAN CONTAIN DECIMAL PLACES!
            for (ItemSale i : saleList) {
                grandTotal += i.getTotalPrice();
            }
            newTransaction.setTotalPrice(grandTotal);

            // persisting changes
            session.persist(newTransaction);
            for (ItemSale i : saleList) {
                session.persist(i);
            } // HERE

            session.getTransaction().commit();
        } catch(HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }

        // getting the final part of the transaction done
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //float moneyInput = getMoney(newTransaction);
            // processing the money
            changeGiven = moneyGiven - newTransaction.getTotalPrice();
            // updating newTransaction
            newTransaction.setMoneyGiven(moneyGiven);
            newTransaction.setChangeGiven(changeGiven);
            // updating the table
            session.update(newTransaction); // HERE
            session.getTransaction().commit();
        } catch(HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
    }

}
