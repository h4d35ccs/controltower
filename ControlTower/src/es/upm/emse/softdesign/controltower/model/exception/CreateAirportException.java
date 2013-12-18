package es.upm.emse.softdesign.controltower.model.exception;


/**
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:00
 */
public class CreateAirportException extends DaoOperationException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * CREATE_AIRPORT_ERROR= "Can't save the airport object because an IOException ";
	 */
	public static final String CREATE_AIRPORT_ERROR = "Can't save the airport object because an IOException ";
	public CreateAirportException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public CreateAirportException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public CreateAirportException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}