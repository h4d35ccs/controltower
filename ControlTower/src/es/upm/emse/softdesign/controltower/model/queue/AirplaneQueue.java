package es.upm.emse.softdesign.controltower.model.queue;
import java.io.Serializable;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.exception.RemoveAircraftException;

/**
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:49:11
 */
public interface AirplaneQueue extends Serializable{

	/**
	 * gets the first {@link Aircraft} from the queue
	 * @return {@link Aircraft}
	 */
	 Aircraft getAircraft();
	 
	 /**
	 * gets the aircraft {@link Aircraft} with the specific Id from the queue
	 * @param id {@link String}
	 * @return {@link Aircraft}
	 */
	 Aircraft getAircraft(String aircraftId);

	/**
	 * puts an {@link Aircraft} inside the queue
	 * @param aircraft {@link Aircraft}
	 */
	 void putPlane(Aircraft aircraft);
	/**
	 * Removes the plane from the queue
	 * @param id {@link String}
	 * @throws RemoveAircraftException if the queue is empty or the id does not match 
	 */
	 void removeAircraft(String id) throws RemoveAircraftException;

}