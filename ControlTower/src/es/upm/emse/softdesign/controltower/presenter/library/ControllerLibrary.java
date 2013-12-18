package es.upm.emse.softdesign.controltower.presenter.library;

/**
 * Constants library for the controler module
 * 
 * @author hades
 * 
 */
public final class ControllerLibrary {

	/**
	 * CURRENT_INSESSION_AIRPORT = "currentAirport";
	 */
	public static final String CURRENT_INSESSION_AIRPORT = "currentAirport";
	/**
	 * PROPERTY_FILE_ERRORMSG = "errormsg.properties";
	 */
	public static final String PROPERTY_FILE_ERRORMSG = "errormsg.properties";
	/**
	 * PROPERTY_KEY_AIRPORT_SERIALIZE_ERROR = "airport.serialize.error";
	 * 
	 * 
	 */
	
	// General
	public static final String PROPERTY_KEY_NOAIRPORT_SESSION__ERROR = "general.airport.notinsession.error";
	
	// Airport
	public static final String PROPERTY_KEY_AIRPORT_SERIALIZE_ERROR = "airport.serialize.error";
	public static final String PROPERTY_KEY_AIRPORT_MISSING_PARAMS = "airport.creation.missing.params";
	public static final String PROPERTY_KEY_AIRPORT_GET_ERROR = "airport.get.error";
	public static final String PROPERTY_KEY_AIRPORT_GETLIST_ERROR = "airport.get.list.error";
	public static final String PROPERTY_KEY_CONTROL_ADDAIRRADAR_ERROR = "airport.get.list.error";
	
	
	// ControlTower
	public static final String PROPERTY_KEY_AIRCRAFT_ADD_ERROR = "control.aircraft.add.error";
	public static final String PROPERTY_KEY_AIRCRAFT_PARK_ERROR = "control.aircraft.park.error";
	public static final String PROPERTY_KEY_AIRCRAFT_LAND_ERROR = "control.aircraft.land.error";
	public static final String PROPERTY_KEY_AIRCRAFT_PREPARETAKEOFF_ERROR = "control.aircraft.takeoff.prepare.error";
	public static final String PROPERTY_KEY_AIRCRAFT_DOTAKEOFF_ERROR = "control.aircraft.takeoff.do.error";
	public static final String PROPERTY_KEY_AIRCRAFT_REMOVE_ERROR = "control.aircraft.remove.error";
	public static final String PROPERTY_KEY_LANDINGFIELDS_CLEAN_ERROR = "control.landingfield.clean";
	public static final String PROPERTY_KEY_PLANE_GOING_BACK_LANDINGQUEUE = "control.landing.goback";
	public static final String PROPERTY_KEY_PLANE_GOING_BACK_PARKINGQUEUE = "control.parking.goback";
	
}
