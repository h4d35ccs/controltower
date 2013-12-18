package es.upm.emse.softdesign.controltower.model.aircraft;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.upm.emse.softdesign.controltower.model.airport.LandingField;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;
import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;

/**
 * Class that represent an Aircraft
 * 
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:47:16
 */
public abstract class Aircraft implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fuelRemaning;
	private String id;
	private Enum<? extends AircraftType> type;
	protected Date timeOfLastLanding;
	protected Date timeOfPrepareToTakeOff;
	protected Integer landingFieldNumber;

	
	/**
	 * COnstructor
	 * 
	 * @param id
	 *            {@link String}
	 * @param fuelRemaning
	 *            int
	 */
	public Aircraft(String id, int fuelRemaning) {
		super();
		this.fuelRemaning = fuelRemaning;
		this.id = id;
	}

	/**
	 * Returns the Aircraft subtype
	 * 
	 * @return {@link PlaneType}
	 */
	public abstract Enum<?> getSubtype();

	/**
	 * Lands this aircraft based on the availability of the landing fields.<br>
	 * If no LandingField is available this aircraft returns to the landing
	 * Queue <br>
	 *   Returns null if the aircraft returned to the queue
	 * 
	 * @param landingFields
	 *            {@link List<{@link LandingField}> }
	 * @param landingQueue
	 *            {@link AirplaneQueue}
	 *  @return {@link Aircraft} the aircraft that landed
	 */
	public abstract Aircraft land(List<LandingField> landingFields,
			AirplaneQueue landingQueue) throws AircraftOperationException;

	/**
	 * Method that puts this aircraft from the parking queue to an available
	 * Landing field, previous to takeoff.<br>
	 *  Returns null if the aircraft returned to the queue
	 * 
	 * @param landingFields
	 *            {@link List}< {@link LandingField}>
	 * @param parkingQueue
	 *            {@link AirplaneQueue} queue of parking aircraft, the aircraft
	 *            should return to this queue if all the tracks are unavailable
	 * @throws AircraftOperationException
	 *             if an error occurs while putting the aircraft in the landing
	 *             field
	 *  @return {@link Aircraft} the aircraft that is ready to takeoff
	 */
	public abstract Aircraft prepareTakeOff(List<LandingField> landingFields,
			AirplaneQueue parkingQueue) throws AircraftOperationException;

	/**
	 * Method that put this aircraft from a landing field to the parking queue
	 * 
	 * @param landingField
	 *            {@link LandingField}
	 * @param landingFields
	 *            {@link Iterator}< {@link LandingField}>
	 * @param parkingQueue
	 *            {@link AirplaneQueue} the queue where this plane is going to
	 *            park
	 * @throws AircraftOperationException
	 *             if an error occurs while parking
	 */
	public abstract Aircraft park(LandingField landingField, Iterator<LandingField> landingFields,
			AirplaneQueue parkingQueue) throws AircraftOperationException;

	/**
	 * Forces the removal of this aircraft from the landingfield
	 * 
	 * @param parkingQueue
	 *            {@link AirplaneQueue} where this aircraft is going after
	 *            removing from the field
	 * @param field
	 *            {@link LandingField} where this aircraft is. Must be removed
	 *            after the execution of this method
	 * @throws AircraftOperationException
	 *             if this aircraft is not present in the {@link LandingField}
	 *             or an error occurs
	 */
	public abstract void forcePark(AirplaneQueue parkingQueue, LandingField field)
			throws AircraftOperationException;

	/**
	 * Remove this aircraft from the landing field and puts the plane in the sky
	 * 
	 * @param landingFields
	 *            {@link Iterator}< {@link LandingField}>
	 * @param landingField
	 *            {@link LandingField} the field in which this Aircraft is, if
	 *            this plane takes off must be removed the reference to this
	 *            object
	 * @throws AircraftOperationException
	 *             if an error occurs while taking off
	 */
	public abstract Aircraft takeOff(LandingField landingField,
			Iterator<LandingField> landingFields) throws AircraftOperationException;

	/**
	 * Forces the takeoff, this aircraft will be removed from the landing field
	 * and puts the plane in the sky
	 * 
	 * @param landingField
	 *            {@link LandingField} the field in which this Aircraft is, if
	 *            this plane takes off the reference to this object must 
	 *            be removed
	 * @throws AircraftOperationException
	 *             if an error occurs while taking off
	 */
	public abstract void forceTakeOff(LandingField landingField)
			throws AircraftOperationException;

	// Getters and setters
	/**
	 * Returns the Aircraft Type
	 * 
	 * @return {@link AircraftType}
	 */
	public Enum<? extends AircraftType> getType() {
		return type;
	}

	/**
	 * Sets the aircraft type
	 * 
	 * @param type
	 */
	public void setType(Enum<? extends AircraftType> type) {
		this.type = type;
	}

	public int getFuelRemaning() {
		return fuelRemaning;
	}

	public void setFuelRemaning(int fuelRemaning) {
		this.fuelRemaning = fuelRemaning;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTimeOfLastLanding() {
		return timeOfLastLanding;
	}

	public void setTimeOfLastLanding(Date timeOfLastLanding) {
		this.timeOfLastLanding = timeOfLastLanding;
	}

	public Date getTimeOfPrepareToTakeOff() {
		return timeOfPrepareToTakeOff;
	}

	public void setTimeOfPrepareToTakeOff(Date timeOfPrepareToTakeOff) {
		this.timeOfPrepareToTakeOff = timeOfPrepareToTakeOff;
	}
	
	public Integer getLandingFieldNumber() {
		return landingFieldNumber;
	}

	public void setLandingFieldNumber(Integer landingFieldNumber) {
		this.landingFieldNumber = landingFieldNumber;
	}

	/**
	 * Cleans the tributes when landing
	 */
	protected void clearLanding(){
		this.timeOfLastLanding = null;
		this.landingFieldNumber = null;
	}
	/**
	 * Cleans the attributes after taking off
	 */
	protected void clearPrepareToTakeOffTime(){
		this.timeOfPrepareToTakeOff = null;
		this.landingFieldNumber = null;
	}

}