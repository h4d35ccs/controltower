package es.upm.emse.softdesign.controltower.model.aircraft.imp;
import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;
import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.aircraft.AircraftFactory;

/**
 * Default Factory class for Aircraft
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:49:56
 */
public class AircraftFactoryImp extends AircraftFactory {
	
	private static Logger logger = Logger.getLogger(AircraftFactoryImp.class);
	/**
	 * Default constructor
	 */
	public AircraftFactoryImp(){

	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.aircraft.AircraftFactory#getAircraft(java.lang.String, es.upm.emse.softdesign.controltower.model.library.AircraftType)
	 */
	public Aircraft getAircraft(String id, AircraftType aircraftType, Enum<?> subType){
		Aircraft aircraft = null;
		switch (aircraftType) {
		case PLANE:
			logger.info("generating a PLANE");
			aircraft = new Plane(id,0,(PlaneType) subType);
			aircraft.setType(AircraftType.PLANE);
			break;
		default:
			throw new RuntimeException("there is no default type of aircraft defined, please use one form the ENUM AircraftType");
			

		}
		return aircraft;
	}

}