package es.fecapa.eacta.db;

// Generated 24-ago-2015 17:06:06 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Eactaevent.
 * @see es.fecapa.eacta.db.Eactaevent
 * @author Hibernate Tools
 */
public class EactaEventHome extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(EactaEventHome.class);

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

	public void persist(EactaEvent transientInstance) {
		log.debug("persisting Eactaevent instance");
		try {
			sessionFactory.openSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EactaEvent instance) {
		log.debug("attaching dirty Eactaevent instance");
		try {
			sessionFactory.openSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(EactaEvent instance) {
		log.debug("attaching clean Eactaevent instance");
		try {
			sessionFactory.openSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EactaEvent persistentInstance) {
		log.debug("deleting Eactaevent instance");
		try {
			sessionFactory.openSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EactaEvent merge(EactaEvent detachedInstance) {
		log.debug("merging Eactaevent instance");
		try {
			EactaEvent result = (EactaEvent) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EactaEvent findById(es.fecapa.eacta.db.EactaEvent id) {
		log.debug("getting Eactaevent instance with id: " + id);
		try {
			EactaEvent instance = (EactaEvent) sessionFactory
					.getCurrentSession().get("es.fecapa.eacta.db.Eactaevent",
							id);
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
	public List findByExample(EactaEvent instance) {
		log.debug("finding Eactaevent instance by example");
		try {
			List results = sessionFactory.openSession()
					.createCriteria("es.fecapa.eacta.db.Eactaevent")
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
	public List eventosPorPartido(long codpartit) {
		try {
			SQLQuery queryObject=null;
			String queryString = "SELECT * FROM `eacta_event` WHERE `codpartit`=?";			
			queryObject = sessionFactory.openSession().createSQLQuery(queryString)				
				.addScalar("codpartit")	//	0
				.addScalar("id")			//	1
				.addScalar("tipoevent")		//	2
				.addScalar("event")			//	3
				.addScalar("part")			//	4
				.addScalar("crono")			//	5
				.addScalar("localvisitant")	//	6
				.addScalar("dorsal")		//	7
				.addScalar("atribut1")		//	8
				.addScalar("atribut2")		//	9
				.addScalar("atribut3")		//	10
				.addScalar("atribut4")		//	11
				.addScalar("atribut5")		//	12
				.addScalar("atribut6")		//	13
				.addScalar("atribut7")		//	14
				.addScalar("atribut8")		//	15
				.addScalar("atribut9");		//	16
						
			queryObject.setParameter(0, codpartit);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
		
	@SuppressWarnings("rawtypes")
	public List eventosPartidoPorPartido(long codpartit) {	
		try {
			SQLQuery queryObject=null;
			String queryString = "SELECT * FROM `eacta_event` WHERE `codpartit`=? AND "
					+ "(event='Total Gols' OR event='Hora Inici Real' OR event='Servei Inicial' OR event='Gol' OR event='Total Faltes' "
					+ "OR event='Directa' OR event='Penal' OR event='Tarja Blava' "
					+ "OR event='Tarja Vermella' OR event='Temps Mort')"
					+ "ORDER BY part ASC, crono DESC";			
			queryObject = sessionFactory.openSession().createSQLQuery(queryString)				
				.addScalar("codpartit")		//	0
				.addScalar("id")			//	1
				.addScalar("tipoevent")		//	2
				.addScalar("event")			//	3
				.addScalar("part")			//	4
				.addScalar("crono")			//	5
				.addScalar("localvisitant")	//	6
				.addScalar("dorsal")		//	7
				.addScalar("atribut1")		//	8
				.addScalar("atribut2")		//	9
				.addScalar("atribut3")		//	10
				.addScalar("atribut4")		//	11
				.addScalar("atribut5");		//	12
						
			queryObject.setParameter(0, codpartit);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public int crearEactaEvent(EactaEvent instance) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			String sqlQuery = "INSERT INTO `eacta_event`(`codpartit`, `tipoevent`, `event`, `part`, `crono`, `localvisitant`, `dorsal`, `atribut1`, `atribut2`, `atribut3`, `atribut4`, `atribut5`, `atribut6`, `atribut7`, `atribut8`, `atribut9`) "
			+ " VALUES (" + instance.getCodpartit() + "," + instance.getTipoevent()  + ",'" + instance.getEvent() + "','" 
			+ instance.getPart() + "','" + instance.getCrono() + "','" + instance.getLocalvisitant() + "'," + instance.getDorsal() + ",'" 
			+ instance.getAtribut1() + "','" 	+ instance.getAtribut2() + "','" + instance.getAtribut3() + "','" 
			+ instance.getAtribut4() + "','" 	+ instance.getAtribut5() + "','" + instance.getAtribut6() + "','" 
			+ instance.getAtribut7() + "','" 	+ instance.getAtribut8() + "','" + instance.getAtribut9() + "')";
			//INSERT INTO `eacta_event`(`codpartit`, `id`, `tipoevent`, `event`, `part`, `crono`, `localvisitant`, `dorsal`, `atribut1`, `atribut2`, `atribut3`, `atribut4`, `atribut5`, `atribut6`, `atribut7`, `atribut8`, `atribut9`, `dataalta`, `datamod`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6],[value-7],[value-8],[value-9],[value-10],[value-11],[value-12],[value-13],[value-14],[value-15],[value-16],[value-17],[value-18],[value-19])
			transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("crearEactaevent failed", re);
			throw re;
		} 
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int actualizarEactaevent(EactaEvent instance) {
//		System.out.println("crono= " + instance.getCrono() + ", localvisitant= " + instance.getLocalvisitant()  + 
//					", dorsal`=" + instance.getDorsal() +
//					", atribut1`='" + instance.getAtribut1() + 
//					", atribut2`='" + instance.getAtribut2() + 
//					", atribut3`='" + instance.getAtribut3() + 
//					", atribut4`='" + instance.getAtribut4() + 
//					", atribut5`='" + instance.getAtribut5() + 
//					", atribut6`='" + instance.getAtribut6() + 
//					", atribut7`='" + instance.getAtribut7() + 
//					", atribut8`='" + instance.getAtribut8() + 
//					", atribut9`='" + instance.getAtribut9());
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
		try {
			String sqlQuery = "UPDATE `eacta_event` SET `part`='" + instance.getPart() + "'" 
					+ ",`crono`='" + instance.getCrono()  + "' "
					+ ",`localvisitant`='" + instance.getLocalvisitant()  + "' "
					+ ",`dorsal`=" + instance.getDorsal() 
					+ ",`atribut1`='" + instance.getAtribut1() + "' "
					+ ",`atribut2`='" + instance.getAtribut2() + "' "
					+ ",`atribut3`='" + instance.getAtribut3() + "' "
					+ ",`atribut4`='" + instance.getAtribut4() + "' "
					+ ",`atribut5`='" + instance.getAtribut5() + "' "
					+ ",`atribut6`='" + instance.getAtribut6() + "' "
					+ ",`atribut7`='" + instance.getAtribut7() + "' "
					+ ",`atribut8`='" + instance.getAtribut8() + "' "
					+ ",`atribut9`='" + instance.getAtribut9() + "' "
					+ "WHERE `codpartit`=" + instance.getCodpartit() + " AND `event`='" + instance.getEvent() + "'";
			//UPDATE `eacta_event` SET `codpartit`=[value-1],`id`=[value-2],`tipoevent`=[value-3],`event`=[value-4],`part`=[value-5],`crono`=[value-6],`localvisitant`=[value-7],`dorsal`=[value-8],`atribut1`=[value-9],`atribut2`=[value-10],`atribut3`=[value-11],`atribut4`=[value-12],`atribut5`=[value-13],`atribut6`=[value-14],`atribut7`=[value-15],`atribut8`=[value-16],`atribut9`=[value-17],`dataalta`=[value-18],`datamod`=[value-19] WHERE 1
	        transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int actualizarEactaResultado(EactaEvent instance, String url, String resultado) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {	
			String sqlQuery = "UPDATE `eacta_event` SET `atribut2`='" + resultado + "', `atribut3`='" + url + "' "
					+ "WHERE `codpartit`=" + instance.getCodpartit() + " AND `tipoevent`='" + instance.getTipoevent() + "'";
			transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int eliminarEactaevent(EactaEvent instance) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
			String sqlQuery = "DELETE FROM `eacta_event` WHERE `codpartit`=" + instance.getCodpartit() + " AND `id`='" + instance.getId() + "'";
			//DELETE FROM `eacta_event` WHERE 1
		     transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int eliminarEactaeventGol(EactaEvent instance) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
		try {	
			String sqlQuery = "DELETE FROM `eacta_event` WHERE `codpartit`=" + instance.getCodpartit() + " AND `tipoevent`=110";
			//DELETE FROM `eacta_event` WHERE 1
		    transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int actualizarEactaeventEventoPartidoLV(EactaEvent instance) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
		try {
			String sqlQuery = "UPDATE `eacta_event` SET `part`='" + instance.getPart() + "'" 
					+ ",`crono`='" + instance.getCrono()  + "' "
					+ ",`localvisitant`='" + instance.getLocalvisitant()  + "' "
					+ ",`dorsal`=" + instance.getDorsal() 
					+ ",`atribut1`='" + instance.getAtribut1() + "' "
					+ ",`atribut2`='" + instance.getAtribut2() + "' "
					+ ",`atribut3`='" + instance.getAtribut3() + "' "
					+ ",`atribut4`='" + instance.getAtribut4() + "' "
					+ ",`atribut5`='" + instance.getAtribut5() + "' "
					+ ",`atribut6`='" + instance.getAtribut6() + "' "
					+ ",`atribut7`='" + instance.getAtribut7() + "' "
					+ ",`atribut8`='" + instance.getAtribut8() + "' "
					+ ",`atribut9`='" + instance.getAtribut9() + "' "
					+ "WHERE `codpartit`=" + instance.getCodpartit() + " AND `event`='" + instance.getEvent() + "'"
					+ " AND `localvisitant`='" + instance.getLocalvisitant()  + "'";
			//UPDATE `eacta_event` SET `codpartit`=[value-1],`id`=[value-2],`tipoevent`=[value-3],`event`=[value-4],`part`=[value-5],`crono`=[value-6],`localvisitant`=[value-7],`dorsal`=[value-8],`atribut1`=[value-9],`atribut2`=[value-10],`atribut3`=[value-11],`atribut4`=[value-12],`atribut5`=[value-13],`atribut6`=[value-14],`atribut7`=[value-15],`atribut8`=[value-16],`atribut9`=[value-17],`dataalta`=[value-18],`datamod`=[value-19] WHERE 1	
	        transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int actualizarEactaeventEventoPartidoLVParte(EactaEvent instance) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
		try {
			String sqlQuery = "UPDATE `eacta_event` SET `part`='" + instance.getPart() + "'" 
					+ ",`crono`='" + instance.getCrono()  + "' "
					+ ",`localvisitant`='" + instance.getLocalvisitant()  + "' "
					+ ",`dorsal`=" + instance.getDorsal() 
					+ ",`atribut1`='" + instance.getAtribut1() + "' "
					+ ",`atribut2`='" + instance.getAtribut2() + "' "
					+ ",`atribut3`='" + instance.getAtribut3() + "' "
					+ ",`atribut4`='" + instance.getAtribut4() + "' "
					+ ",`atribut5`='" + instance.getAtribut5() + "' "
					+ ",`atribut6`='" + instance.getAtribut6() + "' "
					+ ",`atribut7`='" + instance.getAtribut7() + "' "
					+ ",`atribut8`='" + instance.getAtribut8() + "' "
					+ ",`atribut9`='" + instance.getAtribut9() + "' "
					+ "WHERE `codpartit`=" + instance.getCodpartit() + " AND `event`='" + instance.getEvent() + "'"
					+ " AND `localvisitant`='" + instance.getLocalvisitant()  + "' AND  `part`='" + instance.getPart() + "'";
			//UPDATE `eacta_event` SET `codpartit`=[value-1],`id`=[value-2],`tipoevent`=[value-3],`event`=[value-4],`part`=[value-5],`crono`=[value-6],`localvisitant`=[value-7],`dorsal`=[value-8],`atribut1`=[value-9],`atribut2`=[value-10],`atribut3`=[value-11],`atribut4`=[value-12],`atribut5`=[value-13],`atribut6`=[value-14],`atribut7`=[value-15],`atribut8`=[value-16],`atribut9`=[value-17],`dataalta`=[value-18],`datamod`=[value-19] WHERE 1
			transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
	
	public int eliminarEacta(long codpartit) {
		Session session = sessionFactory.openSession();
        Transaction transaction = null;
		try {
			String sqlQuery = "DELETE FROM `eacta_event` WHERE `codpartit`=" + codpartit;
			//DELETE FROM `eacta_event` WHERE 1
	        transaction = session.beginTransaction();
	        SQLQuery query = session.createSQLQuery(sqlQuery);
	        int result = query.executeUpdate();
	        transaction.commit();
			return result;
		} catch (RuntimeException re) {
			log.error("actualizarEactaevent failed", re);
			throw re;
		}
		finally {
			if (session != null){
	            session.close();
	            session = null;
	        }
		}
	}
}
