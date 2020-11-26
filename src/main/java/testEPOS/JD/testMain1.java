/*
package testEPOS.JD;
import csc1035.project3.ItemSale;
import csc1035.project3.Stock;
import csc1035.project3.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import csc1035.project3.HibernateUtil;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
*/
/**
 * @author Josh Davison
 * class for the testing of the database functions.
 *//*


public class testMain1 {
    */
/*
     * This is a test class to make sure that transactions are created properly within the database.
     * This provides a useful code base to program the nproper transaction handling.
     *//*

    public static void main(String[] args) {
        */
/*
         * Transaction to be created as blank
         * Add several ItemSale instances to transaction
         * 2 Local Postcard
         * 1 Village Canvas
         *

         *//*

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction newTransaction = new Transaction();
            List<ItemSale> saleList = new ArrayList<>();
            List<Stock> stockList = new ArrayList<>();

            session.beginTransaction();
            Query query1 = session.createQuery("from Stock s where s.name = 'Local Postcard'");
            Query query2 = session.createQuery("from Stock s where s.name = 'Village Canvas'");

            Object tmp = query1.getResultList().get(0);
            Stock item1 = (Stock) tmp;
            stockList.add(item1);

            tmp = query2.getResultList().get(0);
            Stock item2 = (Stock) tmp;
            stockList.add(item2);

            ItemSale saleItem1 = new ItemSale(item1, 2);
            ItemSale saleItem2 = new ItemSale(item2, 1);


            //setting saleItem1
            saleItem1.setTid(newTransaction);
            saleItem1.setTotalPrice(item1.getSell_price() * saleItem1.getQuantitySold());
            List<ItemSale> addSales = item1.getSales();
            addSales.add(saleItem1);
            item1.setSales(addSales);
            session.update(item1);

            //setting saleItem2
            saleItem2.setTid(newTransaction);
            saleItem2.setTotalPrice(item2.getSell_price() * saleItem2.getQuantitySold());
            addSales = item2.getSales();
            addSales.add(saleItem2);
            item2.setSales(addSales);
            session.update(item2);

            saleList.add(saleItem1);
            saleList.add(saleItem2);

            newTransaction.setItems(saleList);
            newTransaction.setMoneyGiven(160);
            int total = 0;
            for (ItemSale i : saleList) {
                total += i.getTotalPrice();
            }
            newTransaction.setTotalPrice(total);
            newTransaction.setChangeGiven(newTransaction.getMoneyGiven() - total);
            // setting total price, then calculating change given

            //persisting
            session.persist(newTransaction);

            for (ItemSale i : saleList) {
                session.persist(i);
            }


            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
*/
