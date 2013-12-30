package es.upm.emse.softdesign.controltower.model.exception;



/**
 * Exception class that indicate an error getting a saved {@link Airport}
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:01
 */
public class GetAirportException extends DaoOperationException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * GET_SERIALIZED_OBJECT_ERROR="Can't load the airport because the binary file can't be found or can't be read";
	 */
	public static final String GET_SERIALIZED_OBJECT_ERROR ="Can't load the airport because the binary file can't be found or can't be read";
	/**
	 * GENERAL_ERROR="Can't load the airport because a generar exception";
	 */
	public static final String GENERAL_ERROR ="Can't load the airport because a generar exception";
	
	public GetAirportException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public GetAirportException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GetAirportException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}