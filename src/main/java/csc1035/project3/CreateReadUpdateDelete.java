package csc1035.project3;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class CreateReadUpdateDelete<E> implements ICreateReadUpdateDelete<E> {

    private Session session = null;

    /**
     * @author Jordan Barnes
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * @param e this is the object to insert into the database.
     * */
    @Override
    public void create(E e) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(e);
            session.getTransaction().commit();
        } catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * @author Jordan Barnes
     * @author Lee Taylor
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * This shared base code was then modified to use a query.
     *
     * @param column
     *        {@code String} This determines
     *        the column to read from.
     * */
    @Override
    public List read(String column) {

        List entries = null;
        String strQuery = ("SELECT "+column+" FROM Stock");

        try {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();


            Query query = session.createSQLQuery(strQuery); // Extracts all item names
            entries = query.getResultList();

            session.getTransaction().commit();

        } catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entries;

    }

    /**
     * @author Jordan Barnes
     * @author Lee Taylor
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * This shared base code was then modified to use a query.
     *
     * @param stock
     *        {@code int} This is the new value
     *        to overwrite the existing data with
     * @param item
     *        {@code String} This determines the
     *        which record to overwrite based on name
     * */
    @Override
    public void updateStockValue(int stock, String item) {

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Query queryChangeStock = session.createSQLQuery("UPDATE Stock " + // Generates query to update stock
                                                                "SET stock = :stockValue " +
                                                                "WHERE name = :stockName");
            queryChangeStock.setParameter("stockValue", stock);
            queryChangeStock.setParameter("stockName", item);
            queryChangeStock.executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    /**
     * @author Jordan Barnes
     * @author Lee Taylor
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * This shared base code was then modified to use a query.
     *
     * @param s
     *        {@code String} This determines which
     *        record to delete based on name.
     * */
    @Override
    public void deleteByName(String s) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Query deleteQuery = session.createSQLQuery("DELETE from Stock WHERE name = :theItemName");
            deleteQuery.setParameter("theItemName",s);
            deleteQuery.executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * @author Jordan Barnes
     * @author Douglas Gray
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * This shared base code was then modified to use a query.
     *
     * @param table
     *        {@code String} This determines
     *        the to table read from.
     *
     * @param column
     *        {@code String} This determines
     *        the column to search through.*
     *
     * @param input
     *        {@code String} This determines
     *        the input to find.*
     * */
    @Override
    public List readTableWhere(String table, String column, int input) {

        List entries = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Query query = session.createQuery("from " +table+ " data where data."+column+" = :id");
            query.setParameter("id", input);

            entries = query.getResultList();
        }
        catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entries;
    }

    /**
     * @author Jordan Barnes
     * @author Lee Taylor
     *
     * 28/02/2020 - Jordan Barnes' explained and shared code
     * for the interface method definitions during a lecture
     * whilst also granting permission to use in our projects.
     *
     * The given code was modified to perform a SELECT statement
     * in which the column(s), table and WHERE condition are
     * customisable.
     *
     * @param column
     *        {@code String} This determines which
     *        column to read from
     * @param table
     *        {@code String} This determines which
     *        table to read from
     * @param whereCondition
     *        {@code String} This determines the
     *        WHERE condition of the statement
     * */
    @Override
    public List readWithCondition(String column, String table, String whereCondition){

        List entries = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            String strQuery = ("SELECT "+column+" FROM "+table+" "+whereCondition);
            System.out.println(strQuery);
            Query query = session.createSQLQuery(strQuery);
            entries = query.getResultList();

            session.getTransaction().commit();
        } catch (HibernateException ex){
            if (session!=null) session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entries;
    }


}
