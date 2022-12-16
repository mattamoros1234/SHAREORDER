package it.unibo.paw.hibernate.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = initHibernateUtil();

	private static final String CREATE_PACCHI = "CREATE TABLE pacchi(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	statoPacco varchar(50), " +
			"   emailMittente varchar(50), " +
			"   emailDestinatario varchar(50), " +
			"   indirizzoMittente varchar(50)," + 
			"   indirizzoDestinatario varchar(50)," +
			"   emailCorriere varchar(50)" + 
			")";
	
	private static final String CREATE_GRUPPI = "CREATE TABLE gruppi(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"	nomeGruppo varchar(50), " +
			"   emailCreatore varchar(50) " +
			")";
	
	private static final String CREATE_CORRIERI = "CREATE TABLE corrieri(" +
			"	id INT NOT NULL PRIMARY KEY," +
			"   emailCorriere varchar(50) " +
			")";
	

	private static SessionFactory initHibernateUtil() {
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (HibernateException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}

	public static void dropAndCreateTables() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
					
			try {
				session.createSQLQuery("DROP TABLE pacchi").executeUpdate();
				session.createSQLQuery("DROP TABLE gruppi").executeUpdate();
				session.createSQLQuery("DROP TABLE corrieri").executeUpdate();
			} catch (HibernateException e) {
				System.out.println("dropTable(): failed to drop tables " + e.getMessage());
			}
			session.createSQLQuery(CREATE_PACCHI).executeUpdate();
			session.createSQLQuery(CREATE_GRUPPI).executeUpdate();
			session.createSQLQuery(CREATE_CORRIERI).executeUpdate();
			
			tx.commit();
		} finally {
			session.close();
		}
	}
}
