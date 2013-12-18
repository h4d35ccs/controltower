package es.upm.emse.softdesign.controltower.model.airport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;
import es.upm.emse.softdesign.controltower.model.queue.imp.LandingQueue;
import es.upm.emse.softdesign.controltower.model.queue.imp.ParkingQueue;

/**
 * Represent an Airport
 * 
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:45:36
 */
public class Airport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private List<LandingField> landingFields;
	private ControlTower controlTower;
	private int numberOfLandingFields;
	
	private static Logger logger = Logger.getLogger(Airport.class);
	
	/**
	 * Constructor
	 * 
	 * @param name
	 *            {@link String} name of the airport
	 * @param numberOflFields
	 *            int that indicates the number of {@link LandingField} this
	 *            Airport will have
	 */
	public Airport(String name, int numberOflFields) {
		super();
		this.name = name;
		this.landingFields = new ArrayList<LandingField>(numberOflFields);
		this.controlTower = ControlTower.getInstance();
		AirplaneQueue landingQueue = new LandingQueue(false);
		AirplaneQueue parkingQueue = new ParkingQueue();
		this.controlTower.setLandingQueue(landingQueue);
		this.controlTower.setParkingQueue(parkingQueue);
		this.numberOfLandingFields = numberOflFields;
		
		for (int i = 0; i < numberOflFields; i++) {
			LandingField current = new LandingField(i);
			this.landingFields.add(current);
		}
		// sets the next field for the chain of responsability
		for (LandingField field : this.landingFields) {
			int currentField = field.getNumber();
		
			if(currentField != this.landingFields.size()- 1){
				field.setNextField(this.landingFields.get(++currentField));
			}
//			
		}
		logger.info("Airport created with name: " + name
				+ " and landing fields: " + numberOflFields);
	}
	/**
	 * Constructor that indicate if the landing will be prioritized or not
	 * @param name {@link String} name of the airport
	 * @param numberOflFields int number of landingfields
	 * @param landingPrioritized boolean
	 */
	public Airport(String name, int numberOflFields, boolean landingPrioritized){
		this(name,numberOflFields);
		AirplaneQueue landingQueue = new LandingQueue(landingPrioritized);
		this.controlTower.setLandingQueue(landingQueue);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LandingField> getLandingFields() {
		return landingFields;
	}

	public void setLandingFields(List<LandingField> landingFields) {
		this.landingFields = landingFields;
	}

	public ControlTower getControlTower() {
		return controlTower;
	}
	
	public int getNumberOfLandingFields() {
		
		if(this.landingFields != null){
			this.numberOfLandingFields = this.landingFields.size();
		}
		return this.numberOfLandingFields;
	}


	/**
	 * returns an specific landing field <Br>
	 * may throw IndexOutOfBoundsException - if the index is out of range (index
	 * < 0 || index >= size())
	 * 
	 * @param number
	 *            int
	 * @return {@link LandingField}
	 * 
	 */
	public LandingField getLandingField(int number) {
		return this.landingFields.get(number);
	}
}