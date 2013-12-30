package es.upm.emse.softdesign.controltower.model.exception;
/**
 * Indicate an error while removing an aircraft from a queue
 * @author ottoabreu
 *
 */
public class RemoveAircraftException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 	QUEUE_EMPTY_ERROR = "Can't remove the aircraft because the queue is empty";
	 */
	public static final String QUEUE_EMPTY_ERROR = "Can't remove the aircraft because the queue is empty";
	/**
	 *  AIRCRAFT_ID_NOT_FOUND = "Can't remove the aircraft because the given id does not match any aircraft inside the queue, id: ";
	 */
	public static final String AIRCRAFT_ID_NOT_FOUND = "Can't remove the aircraft because the given id does not match any aircraft inside the queue, id: ";
    /**
     * GENERAL_ERROR = "Can't remove the aircraft because  a general error";
     */
	public static final String GENERAL_ERROR = "Can't remove the aircraft because  a general error";
	
	public RemoveAircraftException() {
		// TODO Auto-generated constructor stub
	}

	public RemoveAircraftException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RemoveAircraftException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RemoveAircraftException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
