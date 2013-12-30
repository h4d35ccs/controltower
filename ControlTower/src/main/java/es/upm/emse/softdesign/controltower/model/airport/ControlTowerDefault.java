package es.upm.emse.softdesign.controltower.model.airport;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;

/**
 * The default implementation of {@link ControlTower}
 * 
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:45:37
 */
public class ControlTowerDefault extends ControlTower {

	private static Logger logger = Logger.getLogger(ControlTowerDefault.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public ControlTowerDefault() {

	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.emse.softdesign.controltower.model.ControlTower#landAircraft()
	 */
	@Override
	public Aircraft landAircraft(List<LandingField> landingFields, 
			String aircraftId) throws AircraftOperationException {
		
		Aircraft landingAircraft = this.getLandingQueue().getAircraft(aircraftId);
		landingAircraft = landingAircraft.land(landingFields, this.getLandingQueue());
		
		return landingAircraft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.emse.softdesign.controltower.model.ControlTower#landAircraft()
	 */
	@Override
	public Aircraft landAircraft(List<LandingField> landingFields)
			throws AircraftOperationException {
		
		Aircraft landingAircraft = this.getLandingQueue().getAircraft();
		landingAircraft = landingAircraft.land(landingFields, this.getLandingQueue());
		
		return landingAircraft;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.upm.emse.softdesign.controltower.model.ControlTower#
	 * cleanLastParkedAircraft(java.util.List)
	 */
	public Aircraft cleanLastParkedAircraft(List<LandingField> landingFields)
			throws AircraftOperationException {
		Iterator<LandingField> fields = landingFields.iterator();
		LandingField field = null;
		Aircraft aircraft = null;
		// find first valid aircraft
		while (fields.hasNext()) {
			field = fields.next();
			if (!field.isEmpty()
					&& field.getAircraftOnField().getTimeOfLastLanding() != null) {
				aircraft = field.getAircraftOnField();
				break;
			}
		}
		if (aircraft == null) {
			throw new AircraftOperationException(AircraftOperationException.GET_FIRST_LANDING_ERROR);
		}
		return aircraft.park(field, landingFields.iterator(), getParkingQueue());
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.upm.emse.softdesign.controltower.model.ControlTower#cleanParkedAircraft
	 * (java.lang.String, java.util.List)
	 */
	public Aircraft cleanParkedAircraft(String aircraftId,
			List<LandingField> landingFields) throws AircraftOperationException {
		int landingFieldNumber = -99999;
		Aircraft toBeParked = null;
		for (LandingField field : landingFields) {

			if (field.getAircraftOnField().getId().equals(aircraftId)) {
				toBeParked = field.getAircraftOnField();
				field.clearLandingField();
				this.getParkingQueue().putPlane(toBeParked);
				landingFieldNumber = field.getNumber();
				break;
			}
		}

		if (landingFieldNumber == -99999) {
			logger.error(AircraftOperationException.PARK_BY_ID_ERROR + aircraftId);
			throw new AircraftOperationException(AircraftOperationException.PARK_BY_ID_ERROR
					+ aircraftId);
		}
		logger.info("the aircraft with id: " + aircraftId
				+ " was removed from the landing field no: "
				+ landingFieldNumber);
		return toBeParked;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.airport.ControlTower#prepareTakeOff(java.util.List)
	 */
	public Aircraft prepareTakeOff(List<LandingField> landingFields)
			throws AircraftOperationException {
		Aircraft aircraft = getParkingQueue().getAircraft();
		if (aircraft != null) {
			aircraft =aircraft.prepareTakeOff(landingFields, getParkingQueue());
		} else {
			throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF
					+ " Parking queue empty");
		}
		return aircraft;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.airport.ControlTower#prerareTakeOff(java.util.List, java.lang.String)
	 */
	public Aircraft prepareTakeOff(List<LandingField> landingFields,
			String aircraftId) throws AircraftOperationException {
		Aircraft aircraft = getParkingQueue().getAircraft(aircraftId);
		if (aircraft != null) {
			aircraft = aircraft.prepareTakeOff(landingFields, getParkingQueue());
		} else {
			throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF
					+ " aircraft with id " + aircraftId + " not in parking queue");
		}
		return aircraft;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.airport.ControlTower#takeOff(java.util.List)
	 */
	public Aircraft takeOff(List<LandingField> landingFields)
			throws AircraftOperationException {
		
		Iterator<LandingField> fields = landingFields.iterator();
		LandingField field = null;
		Aircraft aircraft = null;
		// find first valid aircraft
		while (fields.hasNext()) {
			field = fields.next();
			if (!field.isEmpty()
					&& field.getAircraftOnField().getTimeOfPrepareToTakeOff() != null) {
				aircraft = field.getAircraftOnField();
				break;
			}
		}
//		if (aircraft == null) {
//			throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF);
//		}
		return aircraft.takeOff(field, fields);
	}

}