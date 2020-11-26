package csc1035.project3;
import org.hibernate.Session;
import org.hibernate.HibernateException;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ID {
    /**
     * @author Douglas Gray
     * Compares all the transaction ID's in the transaction table
     * to a random 6 digit integer in order to create
     * a new ID number unique to a single transaction.
     *
     * @return id
     * Return a unique ID number for a new transaction
     */
    public static int returnId(){
        Random rnd = new Random();
        Session session = HibernateUtil.getSessionFactory().openSession();
        List ids = new ArrayList<>();
        int id;
        int randomInt;
        try {
            session.beginTransaction();
            //List transactions = session.createQuery("from Transaction").list();
            Query transactionIDs = session.createSQLQuery("SELECT tid FROM Transaction");
            ids = transactionIDs.getResultList();
            session.getTransaction().commit();
            } catch (HibernateException e){
            if (session != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally { session.close(); }

        while (true) {
            randomInt = 100000 + rnd.nextInt(900000);
            if (ids.contains(randomInt)) { continue; }
            else {id = randomInt; break;}
        }
        return id;
    }

    /**
     * Generates a transaction ID by looking
     * at all current ID's, finding the max,
     * and then adding one to the max to
     * generate a new id.
     *
     * @return {@code int} returns an int
     *          ID.
     * */
    public static int returnNewId(){
        CreateReadUpdateDelete CRD = new CreateReadUpdateDelete();
        return (int)CRD.readWithCondition("MAX(tid)","Transaction","").get(0)+1;
    }
}
