package csc1035.project3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Receipt {

    /**
     * @author Douglas Gray
     *
     * Uses a given ID to find data on a given transaction
     * in the database then prints the information to a
     * .txt file. This includes the transaction data +
     * the items purchased within that transaction.
     *
     * Modified - to also write each item that was apart
     * of a transaction, it's quantity and price based on
     * sell_price*quantityBought.
     *
     * @param givenId
     *        Give an ID which is then cross-referenced across
     *        the Transaction table in the database.
     *
     */

     public static void printReceipt(int givenId) throws IOException {

         CreateReadUpdateDelete CRD = new CreateReadUpdateDelete();

         String id = null;
         String totalPrice = null;
         String moneyGiven = null;
         String changeGiven = null;
         List tidEntries;
         List sidEntries;
         List stockQuantities;
         ArrayList<String> itemNames = new ArrayList<>();

         // Obtain all the Transaction ID's from Transaction pertaining to a givenId
         tidEntries = CRD.readTableWhere("Transaction","tid", givenId);

         // Obtain all the Stock ID's from Transaction pertaining to a givenID
         sidEntries = CRD.readWithCondition("stockID","ItemSale",
                 "WHERE transactionID = '"+givenId+"'");

         // Obtain all the quantities bought for each item pertaining to a givenID
         stockQuantities = CRD.readWithCondition("quantitySold","ItemSale",
                 "WHERE transactionID = '"+givenId+"'");

         for (Object sid : sidEntries){
             itemNames.add(CRD.readWithCondition("name","Stock",
                     "WHERE sid = '"+sid.toString()+"'").get(0).toString());
         }

         System.out.println(itemNames);
         System.out.println(stockQuantities);

         for (Object i : tidEntries ) {
             Transaction tmp = (Transaction) i;

             id = String.valueOf(tmp.getTid());
             totalPrice = String.valueOf(tmp.getTotalPrice());
             moneyGiven = String.valueOf(tmp.getMoneyGiven());
             changeGiven = String.valueOf(tmp.getChangeGiven());
         }

         // Write to a txt created with the Transaction No.
         File file = new File("receipt#" + givenId + ".txt");

         FileWriter fw = new FileWriter(file);
         BufferedWriter writer = new BufferedWriter(fw);

         writer.write("ITEM NAME       QUANTITY       PRICE\n\n");
         for (int i = 0; i < itemNames.size(); i++){
             writer.write(stockQuantities.get(i)+"x "+itemNames.get(i) + "     " +
                     DatabaseStatistics.hashMapItemPrice.get(itemNames.get(i).toLowerCase())*(int)stockQuantities.get(i)
             +"\n");
         }

         writer.write(
                 "\nTOTAL PRICE:    " + totalPrice + System.lineSeparator()+
                 "MONEY GIVEN:    " + moneyGiven + System.lineSeparator()+
                 "CHANGE GIVEN:    " + changeGiven + System.lineSeparator());
         writer.write("TRANSACTION ID: " + id);
         writer.flush();
         writer.close();
         fw.close();
         }
}


