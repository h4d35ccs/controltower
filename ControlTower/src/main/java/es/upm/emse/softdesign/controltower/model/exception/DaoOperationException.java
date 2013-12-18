package es.upm.emse.softdesign.controltower.model.exception;

/**
 * Indicate an exception while getting, setting or saving an object through the DAO
 * @author hades
 *
 */
public  class DaoOperationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * METHOD_NOT_IMPLEMENTED = "This DAO implementation do no implements this method";
 */
	public static final String METHOD_NOT_IMPLEMENTED = "This DAO implementation do no implements this method"; 
	/**
	 *  DELETE_OBJECT_ERROR = "Can't delete object due an error, object id: ";
	 */
	public static final String DELETE_OBJECT_ERROR = "Can't delete object due an error, object id: ";
	
	public DaoOperationException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DaoOperationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DaoOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
