package es.upm.emse.softdesign.controltower.model.airport;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.aircraft.AircraftFactory;
import es.upm.emse.softdesign.controltower.model.exception.AddAircraftException;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.exception.RemoveAircraftException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;
import es.upm.emse.softdesign.controltower.model.queue.imp.ParkingQueue;

/**
 * Class that defines a Control Tower 
 * @author hades 
 * @version 1.0
 * @created 20-mar-2013 0:45:36
 */
public abstract class ControlTower implements Serializable{

	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(ControlTower.class);
	private static final long serialVersionUID = 1L;
	private AirplaneQueue landingQueue;
	private AirplaneQueue parkingQueue;
	

	/**
	 * Puts an aircraft in the parking queue ( {@link AirplaneQueue}
	 * @param aircraft {@link Aircraft}
	 * @throws AircraftOperationException if can't add the Aircraft to the parking queue
	 */
	//protected abstract void parkAircraft(Aircraft aircraft)
	//		throws AircraftOperationException;
/**
 * Gets the first {@link Aircraft} from the landing queue ( {@link AirplaneQueue}) and try to put it into a empty {@link LandingField}.<BR>
 * Returns <b>NULL</b> if the aircraft can't land and returned to the landing queue
 * @param landingFields List<{@link LandingField}> landing fields to check
 * @return {@link Aircraft} the aircraft that landed
 * @throws AircraftOperationException if can't land the Aircraft due an error
 */
	public abstract Aircraft landAircraft(List<LandingField> landingFields) throws AircraftOperationException;
	
	/**
	 * Asks the aircraft with the specific Id to land into an empty {@link LandingField}<BR>
	 * Returns <b>NULL</b> if the aircraft can't land and returned to the landing queue
	 * @param aircraftId {@link String} Id of the aircraft that will be ask to land
	 * @return {@link Aircraft} the aircraft that landed
	 * @throws AircraftOperationException if can't land the Aircraft due an error
	 */
	public abstract Aircraft landAircraft(List<LandingField> landingFields, String aircraftId) throws AircraftOperationException;

	/**
	 * Checks for the oldest {@link Aircraft} parked in a {@link LandingField} and put it into the  park queue
	 * @param landingFields List< {@link LandingField}>
	 * @return {@link Aircraft} the parked aircraft
	 * @throws AircraftOperationException if can't move the plane from the Landing Fields
	 */
	public abstract Aircraft cleanLastParkedAircraft(List<LandingField> landingFields)
			throws AircraftOperationException;
	/**
	 * Find an {@link Aircraft} parked on a {@link LandingField} and move it to the {@link ParkingQueue}
	 * @param aircraftId {@link String} Aircraft id
	 * @param landingFields {@link LandingField}
	 * @return {@link Aircraft} the parked aircraft
	 * @throws AircraftOperationException if the given id is not found
	 */
	public abstract Aircraft cleanParkedAircraft(String aircraftId,List<LandingField> landingFields)
			throws AircraftOperationException;
	
	/**
	 * Gets an {@link Aircraft} from the parking queue and put it in the {@link LandingField}<BR>
	 * Returns <b>NULL</b> if the aircraft can't prepare for takeoff and returned to the parking queue
	 * @param landingFields {@link LandingField} to check if the obtained aircraft can takeoff
	 * @return {@link Aircraft} that was put in the landing field
	 * @throws AircraftOperationException if can't put the aircraft in a landing field or an error occurs
	 */
	public abstract Aircraft prepareTakeOff(List<LandingField> landingFields)throws AircraftOperationException;
	/**
	 * Gets an specific {@link Aircraft} from the parking queue and put it in the {@link LandingField}BR>
	 * Returns <b>NULL</b> if the aircraft can't prepare for takeoff and returned to the parking queue
	 * @param landingFields {@link LandingField} to check if the obtained aircraft can takeoff
	 * @param aircraftId {@link String} with the aircraft id
	 * @return link Aircraft} that was put in the landing field
	 * @throws AircraftOperationException if can't put the aircraft in a landing field or an error occurs
	 */
	public abstract Aircraft prepareTakeOff(List<LandingField> landingFields, String aircraftId) throws AircraftOperationException;
	
	/**
	 * remove an aircraft from the landing field simulating the takeoff process
	 * @param landingFields {@link LandingField} 
	 * @throws AircraftOperationException if an error occurs
	 */
	public abstract Aircraft takeOff(List<LandingField> landingFields)throws AircraftOperationException;
	
	
	/**
	 * Add an new aircraft to the landing queue <br>
	 * This method generate a new {@link Aircraft}  using {@link AircraftFactory#getAircraft(String, AircraftType, Enum)}
	 * @param aircraftID {@link String} with the id of the aircraft
	 * @param fuelRemaning int representing the fuel remaining
	 * @param aircraftType {@link AircraftType} that indicate the type of aircraft
	 * @param subtype {@link Enum} indicates the subtype of the Aircraft
	 * @return {@link Aircraft} the created aircraft
	 * @throws AddAircraftException if can't add the Aircraft to the landing queue
	 */
	public Aircraft  addAircraftToRadar(String aircraftID, int fuelRemaning,
			AircraftType aircraftType, Enum<?> subtype) throws AddAircraftException {
		Aircraft newAircraft = null;
		try {
			//get the factory instance
			AircraftFactory factory = AircraftFactory.getInstance();
			// generates the new aircraft
			newAircraft = factory.getAircraft(aircraftID, aircraftType,subtype);
			this.landingQueue.putPlane(newAircraft);
			
		} catch (Exception e) {
			if(newAircraft!=null){
				logger.error(AddAircraftException.ADD_TO_LANDING_ERROR + newAircraft.getId(),e);
				throw new AddAircraftException(AddAircraftException.ADD_TO_LANDING_ERROR + newAircraft.getId(),e);
			}else{
				logger.error(AddAircraftException.ADD_TO_LANDING_ERROR + " NO AIRCRAFT WAS CREATED",e);
				throw new AddAircraftException(AddAircraftException.ADD_TO_LANDING_ERROR + " NO AIRCRAFT WAS CREATED",e);
			}
			
		}
		return newAircraft;
	}
	/**
	 * removes the given aircraft from the landing queue
	 * @param aircraftID
	 * @throws RemoveAircraftException if can�t removes the aircraft from the queue
	 */
	public void removeAircraftFromRadar(String aircraftID) throws RemoveAircraftException{
		
		this.landingQueue.removeAircraft(aircraftID);
	}
	
	
	
	
	public AirplaneQueue getLandingQueue() {
		return landingQueue;
	}

	public void setLandingQueue(AirplaneQueue landingQueue) {
		this.landingQueue = landingQueue;
	}

	public AirplaneQueue getParkingQueue() {
		return parkingQueue;
	}

	public void setParkingQueue(AirplaneQueue parkingQueue) {
		this.parkingQueue = parkingQueue;
	}
	/**
	 * Returns an instance of this class
	 * 
	 * @return {@link ControlTower}
	 */
	public static ControlTower getInstance() {
		return new ControlTowerDefault();
	}

}