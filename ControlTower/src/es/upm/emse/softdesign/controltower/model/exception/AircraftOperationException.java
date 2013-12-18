package es.upm.emse.softdesign.controltower.model.exception;

/**
 * Exception that indicates an error while parking or landing an aircraft
 * 
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:01
 */
public class AircraftOperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * LANDING_ERROR =
	 * "The aircraft wasnt able to land because an error, aircraft id:";
	 */
	public static final String LANDING_ERROR = "The aircraft wasn't able to land because an error, aircraft id:";
	/**
	 * LANDING_OCUPY_ERROR =
	 * "The aircraft wasnt able to land because landing fields are ocupy, aircraft id:"
	 * ;
	 */
	public static final String LANDING_OCUPY_ERROR = "The aircraft wasn't able to land because an error, aircraft id:";
	/**
	 * PARKING_ERROR =
	 * "The aircraft wasnt able to go to the parking queue because an error, aircraft id:"
	 * ;
	 */
	public static final String PARKING_ERROR = "The aircraft wasnt able to go to the parking queue because an error, aircraft id:";
	/**
	 * GET_FIRST_LANDING_ERROR = "Can't get the first landing due an error";
	 */
	public static final String GET_FIRST_LANDING_ERROR = "Can't get the first landing due an error";
	/**
	 * public static final String PARK_BY_ID_ERROR =
	 * "The aircraft wasn't able to go to the parking queue because the aircraft  was not found on any landing field, aircraft id:"
	 * ;
	 */
	public static final String PARK_BY_ID_ERROR = "The aircraft wasn't able to go to the parking queue because the aircraft  was not found on any landing field, aircraft id:";

	/**
	 * AIRCRAFT_CANT_LAND_FULL=
	 * "The aircraft can land because this landing field is occupied";
	 */
	public static final String AIRCRAFT_CANT_LAND_FULL = "The aircraft can't land because this landing field is occupied";
	/**
	 * AIRCRAFT_CANT_LAND_NOTALLOWED =
	 * "The aircraft can land because is not allowed to land in this landing field"
	 * ;
	 */
	public static final String AIRCRAFT_CANT_LAND_NOTALLOWED = "The aircraft can't land because is not allowed to land in this landing field";

	/**
	 * AIRCRAFT_CANT_TAKEOFF =
	 * "The aircraft wasn't able to takeoff because an error: ":
	 */
	public static final String AIRCRAFT_CANT_TAKEOFF = "The aircraft wasn't able to takeoff because an error: ";
	/**
	 * AIRCRAFT_CANT_PREPARE_TAKEOFF = "The aircraft wasn't able to prepare for takeoff because an error: ";
	 * 
	 */
	public static final String AIRCRAFT_CANT_PREPARE_TAKEOFF = "The aircraft wasn't able to prepare for takeoff because an error: ";

	public AircraftOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public AircraftOperationException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AircraftOperationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}