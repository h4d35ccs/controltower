package es.upm.emse.softdesign.controltower.model.exception;



/**
 * Exception class that indicate an error getting a saved {@link Airport}
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:01
 */
public class NotInSessionException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * SESSION_OBJECT_ERROR="Can't load the airport because the binary file can't be found or can't be read";
	 */
	public static final String SESSION_OBJECT_ERROR ="Can't load the Object because it has nor been added to the session";
	/**
	 * GENERAL_ERROR="Can't load the airport because a general exception";
	 */
	public static final String GENERAL_ERROR ="Can't load the Object because a general exception";
	
	public NotInSessionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public NotInSessionException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NotInSessionException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}