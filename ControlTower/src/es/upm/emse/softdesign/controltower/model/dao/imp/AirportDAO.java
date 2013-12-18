package es.upm.emse.softdesign.controltower.model.dao.imp;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.dao.GenericDAO;
import es.upm.emse.softdesign.controltower.model.exception.CreateAirportException;
import es.upm.emse.softdesign.controltower.model.exception.DaoOperationException;
import es.upm.emse.softdesign.controltower.model.exception.GetAirportException;
import es.upm.emse.softdesign.controltower.model.library.AirportLibrary;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;

/**
 * DAO class to handle the Airport Object
 * 
 * @author hades
 * 
 */
public class AirportDAO implements GenericDAO<Airport> {

	private static Logger logger = Logger.getLogger(AirportDAO.class);

	private static final String SERIALIZED_PREFIX;
	private static final String SERIALIZED_NAME_SEPARATOR;

	static {
		try {
			SERIALIZED_PREFIX = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.SERIALIZED_AIRPORT_PREFIX);

			SERIALIZED_NAME_SEPARATOR = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.SERIALIZED_NAME_SEPARATOR);
		} catch (IOException e) {
			logger.error("Failed to initialize the necessary constants", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * default constructor
	 */
	public AirportDAO() {

	}

	/**
	 * Gets a saved Airport<br>
	 * 
	 * @param objectID
	 *            {@link String} with the name of the saved airport
	 * @param params
	 *            NOT IN USE
	 * @return {@link Airport}
	 * @throws GetAirportException
	 *             if can't read or load the file
	 */
	@Override
	public Airport getObject(String objectID, Object... params)
			throws GetAirportException {
		Airport airport = null;
		try {
			airport = (Airport) ControlTowerUtils
					.getSerializedObject(SERIALIZED_PREFIX
							+ SERIALIZED_NAME_SEPARATOR + objectID);
		} catch (IOException e) {
			logger.error(GetAirportException.GET_SERIALIZED_OBJECT_ERROR, e);
			throw new GetAirportException(
					GetAirportException.GET_SERIALIZED_OBJECT_ERROR, e);
		} catch (Exception e) {
			logger.error(GetAirportException.GENERAL_ERROR, e);
			throw new GetAirportException(GetAirportException.GENERAL_ERROR, e);
		}
		logger.info("Returning airport :" + airport.getName());
		return airport;
	}

	/**
	 * Save an Airport object into the file system using {@link Serializable}<br>
	 * the name of the file is extracted from the method
	 * {@link Airport#getName()}<br>
	 * the file path is extracted from the property file defined in
	 * {@link AirportLibrary#PROPERTY_FILE_CONTROLTOWER} under the key
	 * {@link AirportLibrary#PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER}
	 * 
	 * @param objectToSave
	 *            {@link Airport} object to be saved in the file system
	 * @throws CreateAirportException
	 *             if can't save the file
	 */
	@Override
	public void saveObject(Airport objectToSave) throws CreateAirportException {
		try {

			ControlTowerUtils.saveObject(objectToSave, SERIALIZED_PREFIX
					+ SERIALIZED_NAME_SEPARATOR + objectToSave.getName());

		} catch (IOException e) {
			logger.error(CreateAirportException.CREATE_AIRPORT_ERROR, e);
			throw new CreateAirportException(
					CreateAirportException.CREATE_AIRPORT_ERROR, e);
		}
	}

	@Override
	/**
	 * Not implemented
	 */
	public void updateObject(Airport objectToUpdate)
			throws DaoOperationException {
		DaoOperationException ex = new DaoOperationException(
				DaoOperationException.METHOD_NOT_IMPLEMENTED) {
			private static final long serialVersionUID = 10L;
		};

		throw ex;
	}

	/**
	 * Gets all the serialized objects from files with the prefix
	 * {@link AirportLibrary#SERIALIZED_AIRPORT_PREFIX} in their names
	 * 
	 * @param query
	 *            NOT IN USE
	 * @param extraParams
	 *            NOT IN USE
	 * @throws GetAirportException
	 *             if can't get the list of airports
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Airport> getObjects(String query, Object... extraParams)
			throws GetAirportException {
		try {

			List<Object> airportsList = ControlTowerUtils
					.getSerializedObjectsList(SERIALIZED_PREFIX);
			return (List) airportsList;

		} catch (IOException e) {
			logger.error(GetAirportException.GENERAL_ERROR, e);
			throw new GetAirportException(
					GetAirportException.GET_SERIALIZED_OBJECT_ERROR, e);
		} catch (ClassNotFoundException e) {
			logger.error(GetAirportException.GENERAL_ERROR, e);
			throw new GetAirportException(
					GetAirportException.GET_SERIALIZED_OBJECT_ERROR, e);
		}
	}

	/**
	 * Deletes an Airport
	 * 
	 * @param objectId
	 * @param extraParams
	 *            NOT IN USE
	 * @throws DaoOperationException
	 *             if can't delete the object
	 */
	@Override
	public void deleteObject(String objectId, Object... params)
			throws DaoOperationException {
		try {
			ControlTowerUtils.deleteSerializedObject(objectId);
		} catch (IOException e) {
			logger.error(DaoOperationException.DELETE_OBJECT_ERROR + objectId,
					e);
			throw new DaoOperationException(
					DaoOperationException.DELETE_OBJECT_ERROR + objectId, e);
		}
		logger.info("object: " + objectId + " deleted");
	}

}
