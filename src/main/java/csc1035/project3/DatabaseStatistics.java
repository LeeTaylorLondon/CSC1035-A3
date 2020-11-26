package csc1035.project3;

import java.util.HashMap;
import java.util.List;

public class DatabaseStatistics {
    /**
     * This hashMap is used to prevent
     * items with equal names from entering
     * the database. This stores item name
     * as key and it's stock as value.
     *
     * This is achieved by storing the name
     * of every item from the database in
     * this hashMap. Therefore when
     * inserting a new item this hashMap
     * is checked to determine whether an
     * item already exists with a name.
     *
     * Upon start up of this program this
     * hashMap is updated and during
     * insertion or deletion of an item
     * this hashMap must be updated. In
     * order to retain consistency between
     * this HashMap and the database.
     *
     * This hashMap is also used to prevent
     * users from buying an item N amount
     * of times, where N is greater than
     * the amount of stock for an item.
     * As not preventing this would lead
     * to storing a negative stock value
     * for an item in the database.
     * */
    public static HashMap<String, Integer> hashMapItemStock = new HashMap<>();

    /**
     * This hashMap stores the item as the
     * key and it's sell price as the value.
     *
     * This is used to calculate the price
     * of an item multiplied by any int,
     * for the shopping list.
     * */
    public static HashMap<String, Float> hashMapItemPrice = new HashMap<>();

    /**
     * This object is here to allow for
     * create, read, update and delete
     * operations to take place. Via the
     * pre-defined CRUD operations from
     * it's interface and class.
     * */
    private static CreateReadUpdateDelete crudObj = new CreateReadUpdateDelete();

    /**
     * This method generates a hashMap
     * in which all item names are recorded
     * as the key and value for constant
     * lookup time, but to also prevent
     * storing duplicates. As this can be
     * used to check for duplicates.
     *
     * @author Lee Taylor
     * */
    public static void extractStockInformation(){

        hashMapItemPrice.clear(); // The HashMaps are cleared to ensure no conflicting/previous data is retained
        hashMapItemStock.clear();

        List allItemNames = crudObj.read("name");

        List allStockAmounts = crudObj.read("stock");

        List allPriceAmounts = crudObj.read("sell_price");

        for (int i = 0; i < allItemNames.size(); i++){ // Stores all items with their stock amount
            hashMapItemStock.put(allItemNames.get(i).toString().toLowerCase(), (Integer) allStockAmounts.get(i));
        }

        for (int i = 0; i < allPriceAmounts.size(); i++){ // Stores all items with price amount
            hashMapItemPrice.put(allItemNames.get(i).toString().toLowerCase(), (Float) allPriceAmounts.get(i));
        }

    }

}
