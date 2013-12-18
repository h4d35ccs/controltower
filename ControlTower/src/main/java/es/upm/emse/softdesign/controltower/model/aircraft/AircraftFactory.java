package es.upm.emse.softdesign.controltower.model.aircraft;
import es.upm.emse.softdesign.controltower.model.aircraft.imp.AircraftFactoryImp;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;

/**
 * Abstract class that defines a factory of {@link Aircraft}
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:47:16
 */
public abstract class AircraftFactory {


	/**
	 * Returns the default implementation of this factory
	 * @return {@link AircraftFactory}
	 */
	public static AircraftFactory getInstance(){
		return new AircraftFactoryImp ();
	}

	/**
	 * Generate a new Aircraft
	 * @param id {@link String} id of the aircraft
	 * @param aircraftType {@link AircraftType}
	 * @param subtype {@link Enum}
	 * @return {@link Aircraft}
	 */
	public abstract Aircraft getAircraft(String id, AircraftType aircraftType, Enum<?> subtype);

}