package es.upm.emse.softdesign.controltower.model.dao;


import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.dao.imp.AirportDAO;

/**
 * Factory that generates DAO Objects
 * @author hades
 * @param <T>
 *
 */
public class DaoFactory {
	
	
	private static Logger logger = Logger.getLogger(DaoFactory.class);
	/**
	 * AIRPORT_DAO = 1;
	 */
	public static final int AIRPORT_DAO = 1;
	/**
	 * Private Constructor
	 */
	private DaoFactory() {
		
	}
	/**
	 * Returns a new instance of this class
	 * @return {@link DaoFactory}
	 */
	public static DaoFactory getInstance(){
		return new DaoFactory();
	}
	/**
	 * Returns an instance of a dao
	 * @param dao
	 * @return
	 */
	public GenericDAO<?> getDAO (int dao){
		GenericDAO<?> daoObject = null;
		switch (dao) {
		
		case AIRPORT_DAO:
			daoObject = new AirportDAO();
			logger.info("Airport DAO created");
			break;

		default:
			logger.error("DAO not implemented, option:"+dao);
			throw new RuntimeException("DAO not implemented, option:"+dao);
		}
		return daoObject;
	}
}
