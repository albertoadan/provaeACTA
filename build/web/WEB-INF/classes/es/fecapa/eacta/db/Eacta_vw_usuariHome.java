package es.fecapa.eacta.db;

// Generated 23-jul-2015 17:32:20 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Usuari.
 * @see es.fecapa.eacta.db.Eacta_vw_usuari
 * @author Hibernate Tools
 */
public class Eacta_vw_usuariHome extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(Eacta_vw_usuariHome.class);

	//private final SessionFactory sessionFactory = getSessionFactory();
	@SuppressWarnings("deprecation")
	private final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Eacta_vw_usuari transientInstance) {
		log.debug("persisting Usuari instance");
		try {
			sessionFactory.openSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Eacta_vw_usuari instance) {
		log.debug("attaching dirty Usuari instance");
		try {
			sessionFactory.openSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Eacta_vw_usuari instance) {
		log.debug("attaching clean Usuari instance");
		try {
			sessionFactory.openSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Eacta_vw_usuari persistentInstance) {
		log.debug("deleting Usuari instance");
		try {
			sessionFactory.openSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Eacta_vw_usuari merge(Eacta_vw_usuari detachedInstance) {
		log.debug("merging Usuari instance");
		try {
			Eacta_vw_usuari result = (Eacta_vw_usuari) sessionFactory.openSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Eacta_vw_usuari findById(long id) {
		log.debug("getting Usuari instance with id: " + id);
		try {
			
			
			Eacta_vw_usuari instance = (Eacta_vw_usuari) sessionFactory.openSession().get(
					"es.fecapa.eacta.db.Usuari", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findByExample(Eacta_vw_usuari instance) {
		log.debug("finding Usuari instance by example");
		try {
			List results = sessionFactory.openSession()
					.createCriteria("es.fecapa.eacta.db.Eacta_vw_usuari")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Emp instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Eacta_vw_usuari as model where model."
					+ propertyName + "= ?";
			Query queryObject = sessionFactory.openSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findByUser(Object user) {
		return findByProperty("user", user);
	}
}
