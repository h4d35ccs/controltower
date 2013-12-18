package es.upm.emse.softdesign.controltower.model.exception;
/**
 * Exception class that indicates an error while adding an Aircraft to a queue
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:00
 */
public class AddAircraftException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * String ADD_TO_LANDING_ERROR = "The Aircraft can't be added to the queque due an error, Aircraft id: ";
	 */
	public static final String ADD_TO_LANDING_ERROR = "The Aircraft can't be added to the queque due an error, Aircraft id: ";
	
	public AddAircraftException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	public AddAircraftException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public AddAircraftException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}


}