/*
package testEPOS.JD;

import csc1035.project3.HibernateUtil;
import csc1035.project3.ItemSale;
import csc1035.project3.Stock;
import csc1035.project3.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

*/
/**
 * @author Josh Davison
 * class for the implementation of database functions.
 *//*


class ProcessTransaction {

    public static float processTransaction(HashMap<String, Integer> shoppingList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = new Transaction();
        float changeGiven = 0;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            List<ItemSale> saleList = new ArrayList<>();

            session.beginTransaction();
            for (String k : shoppingList.keySet()) {
                // getting the required stock item with a query
                Query query = session.createQuery("from Stock s where s.name = :passString");
                List results = query.setParameter("passString", k).getResultList();
                // creating the temporary objects
                Object tmp = results.get(0);
                Stock item = (Stock)tmp;
                ItemSale sale = new ItemSale(item, shoppingList.get(k));

                // setting up the ItemSale
                sale.setTid(newTransaction);
                // setting the total price of this particular item
                sale.setTotalPrice(item.getSell_price()*sale.getQuantitySold());
                // updating the item in Stock table
                List<ItemSale> addSales = item.getSales();
                addSales.add(sale);
                item.setSales(addSales);
                item.setStock(item.getStock() - sale.getQuantitySold());
                session.update(item);

                saleList.add(sale);

            }

            newTransaction.setItems(saleList);

            int grandTotal = 0;
            for (ItemSale i : saleList) {
                grandTotal += i.getTotalPrice();
            }
            newTransaction.setTotalPrice(grandTotal);

            // persisting changes
            session.persist(newTransaction);
            for (ItemSale i : saleList) {
                session.persist(i);
            }

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
            float moneyInput = getMoney(newTransaction);
            // processing the money
            changeGiven = moneyInput - newTransaction.getTotalPrice();
            // updating newTransaction
            newTransaction.setMoneyGiven(moneyInput);
            newTransaction.setChangeGiven(changeGiven);
            // updating the table
            session.update(newTransaction);
            session.getTransaction().commit();
        } catch(HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert session != null;
            session.close();
        }
        return changeGiven;
    }

    private static float getMoney(Transaction t) {
        Scanner userInput = new Scanner(System.in);
        boolean validAmount = false;
        float m = 0;
        System.out.println("The total price is : " + t.getTotalPrice());
        while (!validAmount) {
            try {
                System.out.println("\n" + "The amount given must be greater than or equal to the total.");
                System.out.print("Enter Amount: ");
                m = Float.parseFloat(userInput.nextLine());
                if (m >= t.getTotalPrice()) {
                    validAmount = true;
                }
            } catch(Exception e) {
                System.out.println("That is an invalid input, please input an amount of money.");
            }
        }
        System.out.println();
        return m;
    }
}
*/
