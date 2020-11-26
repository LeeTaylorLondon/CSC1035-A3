package testEPOS.JD;
import csc1035.project3.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class MainForTestTable1 {
    public static void main(String[] args) {
        testTable1 t1 = new testTable1("Milk", "Food", true,
                5, 20, 7);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(t1);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            if (session!=null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
