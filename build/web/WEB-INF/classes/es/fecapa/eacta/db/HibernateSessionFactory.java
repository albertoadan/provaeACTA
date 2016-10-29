package es.fecapa.eacta.db;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the
 * current thread of execution.  Follows the Thread Local Session
 * pattern, see {@link http://hibernate.org/42.html }.
 */
@SuppressWarnings("deprecation")
public class HibernateSessionFactory {
	static final Logger LOGGER = Logger.getLogger(HibernateSessionFactory.class);
	private static org.hibernate.SessionFactory sessionFactory;

	static {
//		try {

			sessionFactory = new Configuration().configure()
					.buildSessionFactory();

//		} catch (Exception e) {
//			LOGGER.error("%%%% Error Creating SessionFactory %%%%", e);
//			throw new ExceptionInInitializerError(e);
//		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}