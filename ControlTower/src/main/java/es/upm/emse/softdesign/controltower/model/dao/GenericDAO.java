package es.upm.emse.softdesign.controltower.model.dao;

import java.util.List;

import es.upm.emse.softdesign.controltower.model.exception.DaoOperationException;
/**
 * Interface that defines an Data Access Object in a generic way
 * @author hades
 *
 * @param <T> The Data transfer Object that this DAO uses
 */
public interface GenericDAO<T> {
/**
 * Retrieves an object from persistence	<br>
 * This method searches by an id in {@link String}, but if necessary more params could be used
 * by using the second parameter. The implementation must Know the order and type of this extra params
 * @param objectID {@link String}
 * @param params {@link Object}... Array of extra params 
 * @return <T> the DTO
 * @throws DaoOperationException if can't get the object, or can't find it
 */
  T getObject(String objectID, Object... params)throws DaoOperationException;
  /**
   * Save an object into the persistence
   * @param objectToSave <T> DTO to save
   * @throws DaoOperationException if can't save the object
   */
  void saveObject(T objectToSave)throws DaoOperationException;
  /**
   * Updates the given object
   * @param objectToUpdate <T> the object to be updated
   * @throws DaoOperationException if can't update the object
   */
  void updateObject(T objectToUpdate)throws DaoOperationException;
  /**
   * Gets a list of object by the given criteria<bR>
   * if there's no results, this method return null
   * @param query {@link String} whit the criteria
   * @param extraParams {@link Object} extra criteria params if needed 
   * @return {@link List}<T> whit the result of the query
   * @throws DaoOperationException if can't get the result
   */
  List<T> getObjects(String query, Object...extraParams)throws DaoOperationException;
  /**
   * Deletes an object by the id given <br>
   * @param objectId {@link String}
   * @param objects {@link Object}...extra params if needed
   */
  void deleteObject(String objectId, Object... params) throws DaoOperationException;

}
