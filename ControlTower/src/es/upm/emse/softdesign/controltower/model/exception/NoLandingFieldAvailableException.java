package es.upm.emse.softdesign.controltower.model.exception;
/**
 * Indicate that all landing field available 
 * @author ottoabreu
 *
 */
public class NoLandingFieldAvailableException extends Exception {

	/**
	 * ALL_LANDING_FIELDS_OCCUPY="Can't perform the operation because all land fields are occupy";
	 */
	public static final String ALL_LANDING_FIELDS_OCCUPY="Can't perform the operation because all land fields are occupy";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoLandingFieldAvailableException() {
		// TODO Auto-generated constructor stub
	}

	public NoLandingFieldAvailableException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NoLandingFieldAvailableException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public NoLandingFieldAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
