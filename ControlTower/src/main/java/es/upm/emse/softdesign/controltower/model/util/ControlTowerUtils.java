package es.upm.emse.softdesign.controltower.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.exception.NotInSessionException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.library.AirportLibrary;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;

/**
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:51:45
 */
public final class ControlTowerUtils {
	// logger
	private static Logger logger = Logger.getLogger(ControlTowerUtils.class);

	private ControlTowerUtils() {

	}

	/**
	 * Retrieves an {@link Object} from the session
	 * 
	 * @param request
	 *            {@link HttpServletRequest} the request
	 * @param sessionAttKey
	 *            {@link String} with the key used to retrieve the object from
	 *            the session
	 * @return {@link Object}
	 * @throws NotInSessionException
	 */
	public static Object getObjectFromSession(HttpServletRequest request,
			String sessionAttKey) throws NotInSessionException {

		Object ob = request.getSession().getAttribute(sessionAttKey);
		if (ob == null) {
			throw new NotInSessionException(
					NotInSessionException.SESSION_OBJECT_ERROR);
		}

		return ob;
	}

	/**
	 * Returns a serialize object
	 * 
	 * @param name
	 *            {@link String} with the object name <b>without the file
	 *            extension or the path </b>
	 * @return {@link Object}
	 * @throws IOException
	 *             if can't read the file or can't be found
	 * @throws ClassNotFoundException
	 *             Airport airport
	 */
	public static Object getSerializedObject(final String name)
			throws IOException, ClassNotFoundException {
		logger.info("Object to get: " + name);
		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
		FileInputStream fis = new FileInputStream(path + name
				+ AirportLibrary.SERIALIZED_FILE_EXTENSION);

		ObjectInputStream ois = new ObjectInputStream(fis);
		Object toReturn = null;
		try {
			toReturn = ois.readObject();
		} finally {
			ois.close();
		}
		logger.info("object load:" + toReturn);
		return toReturn;
	}

	/**
	 * 
	 * @param prefix
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Object> getSerializedObjectsList(final String prefix)
			throws IOException, ClassNotFoundException {

		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);

		File dir = new File(path);

		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(prefix);
			}
		});

		List<Object> objectList = new ArrayList<Object>();
		FileInputStream fis;
		ObjectInputStream ois;
		if (foundFiles != null) {
			for (File file : foundFiles) {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				try {
					Object toReturn = ois.readObject();
					objectList.add(toReturn);
				} finally {
					ois.close();
				}
			}
		}

		return objectList;
	}

	/**
	 * Serilize an object
	 * 
	 * @param object
	 *            {@link Serializable}
	 * @param fileName
	 *            {@link String}
	 * @throws IOException
	 *             if can't wirte the file
	 */
	public static void saveObject(Serializable object, final String fileName)
			throws IOException {

		logger.info("Object to serialize: " + object);
		// the path must be ended in /
		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
		logger.debug("serialized object folder: " + path);
		File saveFolder = new File(path);

		if (!saveFolder.exists()) {
			boolean created = saveFolder.mkdir();
			logger.info("Save folder created: " + path + "-" + created);
		}
		FileOutputStream fs = new FileOutputStream(path + fileName
				+ AirportLibrary.SERIALIZED_FILE_EXTENSION);
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(fs);
			os.writeObject(object);
		} finally {
			if (os != null) {
				os.close();
			}
		}

		logger.info("Object serialized");
	}

	/**
	 * returns the value of a property
	 * 
	 * @param resourceName
	 * @param propertyKey
	 * @return value {@link String}
	 * @throws IOException
	 *             - if an error occurred when reading from the input stream.
	 * @throws IllegalArgumentException
	 *             - if the input stream contains a malformed Unicode escape
	 *             sequence.on
	 */
	public static String getProperties(final String resourceName,
			final String propertyKey) throws IOException {
		logger.info("property name: " + resourceName + " property key:"
				+ propertyKey);
		Properties prop = new Properties();
		prop.load(ControlTowerUtils.class.getClassLoader().getResourceAsStream(
				resourceName));
		String value = prop.getProperty(propertyKey);
		logger.info("returns:" + value);
		return value;

	}

	/**
	 * returns true if any of the arguments is null or blank
	 * 
	 * @param values
	 * 
	 */
	public static boolean hasBlankValues(Object... values) {
		boolean result = false;

		for (int i = 0; i < values.length; i++) {
			if (isEmptyObject(values[i])) {
				return true;
			}
		}

		return result;

	}

	/**
	 * For objects other than {@link String} returns true when these are null,
	 * for {@link String} Objects returns true if are null or blank
	 * 
	 * @param object
	 */
	public static boolean isEmptyObject(Object object) {
		return (object == null || (object instanceof String && ""
				.equals(((String) object).replaceAll("\\s", ""))));
	}

	/**
	 * Method that removes an object from a queue, this methods uses
	 * {@link java.lang.reflect.Method#invoke(Object, Object...)} to obtain the
	 * value to compare
	 * 
	 * @param compareAtribute
	 *            {@link String} with the value to compare if that object should
	 *            be removed
	 * @param queue
	 *            {@link Queue} whit the elements
	 * @param getMethodName
	 *            {@link String} with the <b> EXACT NAME OF THE METHOD THAT
	 *            RETURNS THE VALUE TO BE COMBARED </b>, it should be a get
	 *            method with no params and return a {@link String}
	 * @return boolean true if found and removes, false otherwise
	 * @throws NoSuchMethodException
	 *             if the given method name does not match
	 * @throws SecurityException
	 *             If a security manager, s, is present and any of the following
	 *             conditions is met: <br>
	 *             invocation of s.checkMemberAccess(this, Member.PUBLIC) denies
	 *             access to the method<br>
	 *             the caller's class loader is not the same as or an ancestor
	 *             of the class loader for the current class and invocation of
	 *             s.checkPackageAccess() denies access to the package of this
	 *             class
	 * @throws InvocationTargetException
	 *             If a security manager, s, is present and any of the following
	 *             conditions is met: invocation of s.checkMemberAccess(this,
	 *             Member.PUBLIC) denies access to the method the caller's class
	 *             loader is not the same as or an ancestor of the class loader
	 *             for the current class and invocation of
	 *             s.checkPackageAccess() denies access to the package of this
	 *             class
	 * @throws IllegalAccessException
	 *             If a security manager, s, is present and any of the following
	 *             conditions is met: invocation of s.checkMemberAccess(this,
	 *             Member.PUBLIC) denies access to the method the caller's class
	 *             loader is not the same as or an ancestor of the class loader
	 *             for the current class and invocation of
	 *             s.checkPackageAccess() denies access to the package of this
	 *             class
	 * @throws IllegalArgumentException
	 *             If a security manager, s, is present and any of the following
	 *             conditions is met: invocation of s.checkMemberAccess(this,
	 *             Member.PUBLIC) denies access to the method the caller's class
	 *             loader is not the same as or an ancestor of the class loader
	 *             for the current class and invocation of
	 *             s.checkPackageAccess() denies access to the package of this
	 *             class
	 */
	public static boolean removeObjectFromQueue(String compareAtribute,
			Queue<? extends Object> queue, String getMethodName)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		boolean found = false;
		for (Object object : queue) {
			java.lang.reflect.Method method = object.getClass().getMethod(
					getMethodName, new Class<?>[] {});
			String result = (String) method.invoke(object, new Object[] {});

			if (result.equalsIgnoreCase(compareAtribute)) {
				queue.remove(object);
				found = true;
				break;

			}
		}
		return found;
	}

	/**
	 * Gets a msg from a property file to generate an error message <br>
	 * if the property or the key is missing, the message of the original
	 * exception will be used
	 * 
	 * @param resourceName
	 *            {@link String} with the name of the property file
	 * @param key
	 *            {@link String} whit the key in the property file
	 * @param e
	 *            {@link Exception} original exception, if is null a default msg
	 *            will be added (<i>An error occurs please check the logs</i>)
	 * @return {@link String} the error msg
	 * 
	 */
	public static String getErrorMsg(String resourceName, String key,
			Exception e) {
		String errorMsg = null;
		try {
			errorMsg = ControlTowerUtils.getProperties(resourceName, key);
		} catch (IOException e1) {
			// because the generated exception, by default the original
			// exception msg will be the msg
			logger.error(
					"Can't generate the error message property or key not found",
					e1);
			if (e != null) {
				errorMsg = e.getMessage();
			} else {
				errorMsg = "An error occurs please check the logs";
			}
		}
		return errorMsg;
	}

	/**
	 * Deletes a serialized object from the filesystem
	 * 
	 * @param fileName
	 *            {@link String} filename
	 * @throws IOException
	 *             if can't access to the file or the file is missing
	 */
	public static void deleteSerializedObject(final String fileName)
			throws IOException {
		logger.info("Object to delete: " + fileName);
		// the path must be ended in /
		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);

		File fileToDelete = new File(path + fileName
				+ AirportLibrary.SERIALIZED_FILE_EXTENSION);
		boolean deleted = fileToDelete.delete();
		if (!deleted) {
			fileToDelete.deleteOnExit();
			logger.info("Object was not deleted, trying delete On Exit ");
		} else {
			logger.info("Object was deleted");
		}

	}

	/**
	 * Generates an ID by the given {@link AircraftType} and Subtype
	 * 
	 * @param aircraftType
	 *            {@link AircraftType}
	 * @param subtype
	 *            {@link Enum} subtype
	 * @return {@link String}
	 */
	public static String generateAircraftId(AircraftType aircraftType,
			Enum<?> subtype) {
		StringBuilder id = new StringBuilder();
		// obtengo los milisegundos
		long timestamp = new Date().getTime();
		// genero un numero aleatoreo para dividir los milisegundos
		Random randomGenerator = new Random();
		long random = randomGenerator.nextInt(100000000);
		// obtengo el identificador
		long idLong = timestamp / random;
		switch (aircraftType) {
		case PLANE:
			id.append("AA");
			PlaneType pType = (PlaneType) subtype;
			switch (pType) {
			case WIDE_BODY:
				id.append("WB");
				break;

			case DOUBLE_DECK:
				id.append("DD");
				break;

			case NARROW_BODY:
				id.append("NB");
				break;

			default:
				logger.error("plane type not valid " + pType);
				throw new RuntimeException("plane type not valid " + pType);
			}
			id.append('-');
			id.append(idLong);
			break;

		default:
			logger.error("Aircraft type not valid " + aircraftType);
			throw new RuntimeException("Aircraft type not valid "
					+ aircraftType);
		}
		logger.info("id generated:" + id.toString());
		return id.toString();
	}

}