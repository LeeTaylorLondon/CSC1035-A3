package csc1035.project3;

import java.util.List;

/**
 * @author Jordan Barnes
 * @author Lee Taylor
 *
 * This interface is in place to create communication
 * between the program and database whilst significantly
 * reducing code duplication.
 *
 * 28/02/2020 - Jordan Barnes' explained and shared code
 * for the interface class during a lecture
 * whilst also granting permission to use in our projects.
 *
 * Modified to serve the purpose of our methods specifically.
 * Renamed some methods, removed some methods and updated
 * parameter lists.
 * */

public interface ICreateReadUpdateDelete<E> {

    /**
     * Inserts a new stock item into the database.
     *
     * @param e the object to insert.
     * */
    void create(E e);

    /**
     * Reads data from the database.
     *
     * @param c the column to read from
     * */
    List read(String c);

    /**
     * Reads data from the database returns a list of records.
     *
     * @param t the table to read from
     *
     * @param c the column to read from
     *
     * @param i the id to obtain
     *
     * */
    List readTableWhere(String t, String c, int i);

    /**
     * Overwrites an existing stock value.
     *
     * @param i new value to overwrite with
     * @param n the record overwrite with this name
     * */
    void updateStockValue(int i, String n);


    /**
     * Deletes a record with the specified item name.
     *
     * @param s the record with this name
     *          is deleted.
     * */
    void deleteByName(String s);

    /**
     * Selects columns whilst specifying WHERE conditions
     *
     * @param c the column to read from
     * @param w the WHERE condition
     * */
    List readWithCondition(String c, String t, String w);
}
