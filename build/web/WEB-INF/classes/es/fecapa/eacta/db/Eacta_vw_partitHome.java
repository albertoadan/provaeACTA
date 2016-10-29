package es.fecapa.eacta.db;

// Generated 23-jul-2015 17:32:20 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Partit.
 * @see es.fecapa.eacta.db.Eacta_vw_partit
 * @author Hibernate Tools
 */
public class Eacta_vw_partitHome extends BaseHibernateDAO {  //TODO: Añadir extends BaseHibernateDAO 

	private static final Log log = LogFactory.getLog(Eacta_vw_partitHome.class);

	//private final SessionFactory sessionFactory = getSessionFactory();
	//TODO: Cambiar la obtención de la sesion
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

	public void persist(Eacta_vw_partit transientInstance) {
		log.debug("persisting Partit instance");
		try {
			sessionFactory.openSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Eacta_vw_partit instance) {
		log.debug("attaching dirty Partit instance");
		try {
			sessionFactory.openSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Eacta_vw_partit instance) {
		log.debug("attaching clean Partit instance");
		try {
			sessionFactory.openSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Eacta_vw_partit persistentInstance) {
		log.debug("deleting Partit instance");
		try {
			sessionFactory.openSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Eacta_vw_partit merge(Eacta_vw_partit detachedInstance) {
		log.debug("merging Partit instance");
		try {
			Eacta_vw_partit result = (Eacta_vw_partit) sessionFactory.openSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Eacta_vw_partit findById(long id) {
		log.debug("getting Partit instance with id: " + id);
		try {
			//TODO: Cambiar current session
			Eacta_vw_partit instance = (Eacta_vw_partit) sessionFactory.openSession().get(
					"es.fecapa.eacta.db.Eacta_vw_partit", id);
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
	public List findByExample(Eacta_vw_partit instance) {
		log.debug("finding Partit instance by example");
		try {
			List results = sessionFactory.openSession()
					.createCriteria("es.fecapa.eacta.db.Eacta_vw_partit")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	//DATOS NECESARIOS PARA CONFECCIONAR EL ACTA
	@SuppressWarnings("rawtypes")
	public List jugadoresPorPartido(long codpartit) {
		try {
			SQLQuery queryObject=null;
			String queryString = "call eActa(?)";
			queryObject = sessionFactory.openSession().createSQLQuery(queryString)		
				.addScalar("codlic")		//	0
				.addScalar("noms")			//	1
				.addScalar("categoria")		//	2
				.addScalar("submodalitat")	//	3
				.addScalar("codenti")		//	4
				.addScalar("codcate")		//	5
				.addScalar("classe")		//	6
				.addScalar("codequip")		//	7
				.addScalar("codequip2")		//	8
				.addScalar("codequip3")		//	0
				.addScalar("dorsal")		//	10
				.addScalar("eactanomcate")	// 	11
				.addScalar("pin")			//	12
				.addScalar("nif")			//	13
				.addScalar("sancionat");	//	14
			
		queryObject.setParameter(0, codpartit);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}
