package es.upm.emse.softdesign.controltower.model.airport;

import java.io.Serializable;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.exception.NoLandingFieldAvailableException;

/**
 * Represent a landing field
 * 
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:45:37
 */
public class LandingField implements Serializable {

	private static final long serialVersionUID = 1L;
	private Aircraft aircraftOnField;

	private int number;
	private LandingField nextField;

	public LandingField(int number) {
		this.number = number;
	}
//for chain of responsability
	public void setNextField(LandingField nextField) {
		this.nextField = nextField;
	}

	/**
	 * validate if an Aircraft could land in this field
	 * 
	 * @param aircraft
	 * @throws AircraftOperationException
	 *             if the aircraft is not allowed to land
	 */
	private void canLandAircraft(Aircraft aircraft)
			throws AircraftOperationException {
		// heres goes the logic to authorize an aircraft to land, in the first
		// version the limitation is that the field is empty or not
		// that's why this method is empty
	}

	/**
	 * Validate if this landing field is available
	 * 
	 * @throws AircraftOperationException
	 *             if this landing field is occupied
	 */
	private void isAvailable() throws AircraftOperationException {

		if (this.aircraftOnField != null) {
			throw new AircraftOperationException(
					AircraftOperationException.AIRCRAFT_CANT_LAND_FULL);
		}

	}

	/**
	 * Indicates if this landing field is empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		boolean isEmpty = false;
		if (this.aircraftOnField == null) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * Removes an Aircraft from the landing field
	 */
	public void clearLandingField() {
		this.aircraftOnField = null;
	}

	public Aircraft getAircraftOnField() {
		return aircraftOnField;
	}

	/**
	 * Sets an aircraft on the field
	 * 
	 * @param aircraftOnField
	 * @throws AircraftOperationException
	 *             if this landing field is occupied or the aircraft can�t land
	 */
	public void setAircraftOnField(Aircraft aircraftOnField)
			throws AircraftOperationException,NoLandingFieldAvailableException {
		try{
			this.isAvailable();
			this.canLandAircraft(aircraftOnField);
			this.aircraftOnField = aircraftOnField;
//			this.timeOfLastLanding = new Date();
//			chain of responsability 
			aircraftOnField.setLandingFieldNumber(this.number);
		}catch(AircraftOperationException e){
			
			if(this.nextField != null){
				this.nextField.setAircraftOnField(aircraftOnField);
			}else{
				throw new NoLandingFieldAvailableException(NoLandingFieldAvailableException.ALL_LANDING_FIELDS_OCCUPY);
			}
		}
		

	}

	public int getNumber() {
		return number;
	}

}