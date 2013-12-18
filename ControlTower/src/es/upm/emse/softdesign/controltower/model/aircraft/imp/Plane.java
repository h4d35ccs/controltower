package es.upm.emse.softdesign.controltower.model.aircraft.imp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.airport.LandingField;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.exception.NoLandingFieldAvailableException;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;
import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;

/**
 * Represent a Plane
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:49:56
 */
public class Plane extends Aircraft {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(Plane.class);
	
	private String model;
	private PlaneType subType;
	/**
	 * Constructor
	 * @param id {@link String}
	 * @param fuelRemaning int 
	 * @param subtype {@link PlaneType}
	 */
	public Plane(String id, int fuelRemaning, PlaneType subtype){
		super(id,fuelRemaning);
		this.subType = subtype;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#getSubtype()
	 */
	public Enum<? extends PlaneType>  getSubtype() {
		
		return this.subType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#land(java.util.List, es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue)
	 */
	//TODO arreglar que pueda devolver el que aterriza
	 public Aircraft land(List<LandingField> landingFields,
				AirplaneQueue landingQueue) throws AircraftOperationException{
			
			// Serch for the first landingField available
//			boolean allOccupy = true;
			Aircraft landed = null;
			try {
				LandingField landingField = landingFields.get(0);
				landingField.setAircraftOnField(this);
				landed = this;
				this.timeOfLastLanding = new Date();
				logger.info("The aircraft :" + this.getId()
						+ " has landed in " + this.landingFieldNumber + "at: "
						+ timeOfLastLanding);
//				for (LandingField field : landingFields) {
//					// verify if exist one landing field available
//					try {
//						field.setAircraftOnField(this);
//						this.timeOfLastLanding = new Date();
//						logger.info("The aircraft :" + this.getId()
//								+ " has landed in " + field.getNumber() + "at: "
//								+ timeOfLastLanding);
//						allOccupy = false;
//						landed = this;
//						break;
//					} catch (AircraftOperationException e) {
//						logger.warn(e.getMessage());
//					}
//				}
//				// if all the fields are occupied put the plane into the queue again
//				if (allOccupy) {
//					logger.info("The aircraft :" + this.getId()
//							+ "is back to the landing queue");
//					landingQueue.putPlane(this);
//				}
			}catch(NoLandingFieldAvailableException e){
				logger.info("The aircraft :" + this.getId()
						+ "is back to the landing queue");
				landingQueue.putPlane(this);
			} catch (Exception e) {
				logger.error(
						AircraftOperationException.LANDING_ERROR + this.getId(),
						e);
				throw new AircraftOperationException(AircraftOperationException.LANDING_ERROR
						+ this.getId(), e);
			}
			return landed;
		}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#prepareTakeOff(java.util.List, es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue)
	 */
	public Aircraft prepareTakeOff(List<LandingField> landingFields,
			AirplaneQueue parkingQueue) throws AircraftOperationException {
		Aircraft takingOff = null;
		try {
			LandingField landingField = landingFields.get(0);
			landingField.setAircraftOnField(this);
			this.timeOfPrepareToTakeOff = new Date();
			takingOff = this;
			logger.info("The aircraft :" + this.getId()
					+ "is ready to takeoff in field "+this.landingFieldNumber);
//			parkingQueue.removeAircraft(getId());
//			boolean emptyFieldFound = false;
//			for (LandingField field : landingFields) {
//				if (field.isEmpty()) {
//					emptyFieldFound = true;
//					field.setAircraftOnField(this);
//					this.setTimeOfPrepareToTakeOff(new Date());
//					this.landingFieldNumber = field.getNumber();
//					logger.info("The aircraft :" + this.getId()
//							+ "is ready to takeoff");
//					takingOff = this;
//					break;
//				}
//			}
//			if (!emptyFieldFound) {
//				logger.info("The aircraft :" + this.getId()
//						+ "can't prepare to takeoff, all fields occupy ");
//				parkingQueue.putPlane(this);
//			}
//	 } catch (RemoveAircraftException e) {
//			logger.error(
//					RemoveAircraftException.AIRCRAFT_ID_NOT_FOUND + this.getId(),
//					e);
//			throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF
//					+ this.getId(), e);
//		}
		}catch(NoLandingFieldAvailableException e){
			logger.info("The aircraft :" + this.getId()
					+ "can't prepare to takeoff, all fields occupy ");
			parkingQueue.putPlane(this);
		 } catch (Exception e) {
				logger.error(
						AircraftOperationException.AIRCRAFT_CANT_PREPARE_TAKEOFF+ e.getMessage(),
						e);
				throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_PREPARE_TAKEOFF
						+ e.getMessage(), e);
			}
		return takingOff;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#park(es.upm.emse.softdesign.controltower.model.airport.LandingField, java.util.Iterator, es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue)
	 */
	public Aircraft park(LandingField landingField, Iterator<LandingField> landingFields,
			AirplaneQueue parkingQueue) throws AircraftOperationException {
		try {
			LandingField longerWaiting = findLongerWaiting(landingFields, true);
			logger.info("Plane:"+this.getId()+" called for parking");
			if (longerWaiting == null) {
				this.clearLanding();
				landingField.clearLandingField();
				parkingQueue.putPlane(this);
				logger.info("Plane:"+this.getId()+" park");
				return this;
			} else {
				// there was an other plane having landed before, so transfer control to that one
				Aircraft aircraft = longerWaiting.getAircraftOnField();
				return aircraft.park(longerWaiting, landingFields, parkingQueue);
			}
		} catch (Exception e) {
			logger.error(AircraftOperationException.PARKING_ERROR+this.getId(),e);
			throw new AircraftOperationException(AircraftOperationException.PARKING_ERROR+this.getId(),e);
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#takeOff(es.upm.emse.softdesign.controltower.model.airport.LandingField, java.util.Iterator)
	 */
	public Aircraft takeOff(LandingField landingField, Iterator<LandingField> landingFields)
			throws AircraftOperationException {
		logger.info("Plane:"+this.getId()+" called for takeOff");
		try{
			LandingField longerWaiting = this.findLongerWaiting(landingFields, false);
			if (longerWaiting == null) {
				logger.debug("  -> didn't found longer waiting one as this (" + getId() + ")!");
				clearPrepareToTakeOffTime();
				landingField.clearLandingField();
				logger.info("Plane:"+this.getId()+" takeOff");
				this.landingFieldNumber = null;
				this.timeOfLastLanding = null;
				this.timeOfPrepareToTakeOff = null;
				return this;
			} else {
				// there was an other plane having been prepared for takeoff before,
				// so transfer control to that one
				logger.debug("  -> found longer waiting one (" + getId() + ")!");
				Aircraft aircraft = longerWaiting.getAircraftOnField();
				return aircraft.takeOff(longerWaiting, landingFields);
			}
		}catch(Exception e){
			logger.error(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF,e);
			throw new AircraftOperationException(AircraftOperationException.AIRCRAFT_CANT_TAKEOFF,e);
		}
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#forcePark(es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue, es.upm.emse.softdesign.controltower.model.airport.LandingField)
	 */
	public void forcePark(AirplaneQueue parkingQueue, LandingField field)
			throws AircraftOperationException {
		field.clearLandingField();
		parkingQueue.putPlane(this);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.Aircraft#forceTakeOff(es.upm.emse.softdesign.controltower.model.airport.LandingField)
	 */
	public void forceTakeOff(LandingField landingField)
			throws AircraftOperationException {
		logger.info("Plane:"+this.getId()+" forceTakeOff called");
		this.landingFieldNumber = null;
		this.timeOfLastLanding = null;
		this.timeOfPrepareToTakeOff = null;
		landingField.clearLandingField();
	}
	
	// tries to find landing field with aircraft that has been waiting longer already
	// returns null if no other aircraft has landed/been prepared for takeoff before, or
	// the aircraft that has been waiting longer
	// (land = true means we are trying to park, land = false means we are trying to take off)
	private LandingField findLongerWaiting(Iterator<LandingField> landingFields, boolean land) {
		Date thisDate = land ? getTimeOfLastLanding() : getTimeOfPrepareToTakeOff();
		while (landingFields.hasNext()) {
			LandingField field = landingFields.next();
			if (!field.isEmpty()) {
				Aircraft a = field.getAircraftOnField();
				Date otherDate = land ? a.getTimeOfLastLanding() : a.getTimeOfPrepareToTakeOff();
				if (otherDate != null && otherDate.before(thisDate)) {
					return field;
				}
			}
		}
		return null;
	}

}