package es.upm.emse.softdesign.controltower.model.spock

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.dao.DaoFactory;
import es.upm.emse.softdesign.controltower.model.dao.imp.AirportDAO;
import es.upm.emse.softdesign.controltower.model.exception.CreateAirportException;
import es.upm.emse.softdesign.controltower.model.library.AirportLibrary;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;

class AirportDAOSpockTest extends spock.lang.Specification {


	private Airport airportTest;
	private AirportDAO dao;

	private static def SERIALIZED_PREFIX;
	private static final def SERIALIZED_NAME_SEPARATOR;


	static {

		SERIALIZED_PREFIX = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.SERIALIZED_AIRPORT_PREFIX);

		SERIALIZED_NAME_SEPARATOR = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.SERIALIZED_NAME_SEPARATOR);
	}

	def setup() {
		airportTest = new Airport("Airport_Test", 3);
		dao = (AirportDAO) DaoFactory.getInstance().getDAO(
				DaoFactory.AIRPORT_DAO);
	}

	def cleanup() {
		airportTest = null;
		dao = null;
	}

	def "create an airport and save it"(){

		setup:"the path where the object will be"
		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);

		when:"the object is saved into the file system"
		dao.saveObject(airportTest);


		then:"the file must exist"
		File savedAirport = new File(path + SERIALIZED_PREFIX
				+ SERIALIZED_NAME_SEPARATOR + airportTest.getName()
				+ ".bin");
		savedAirport.exists() == true;
	}

	def "create an airport and save it even if the folder does not exist"(){
		setup:"the folder must not exist in the system have to be created"

		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
		File folder = new File(path);
		if (folder.exists()) {
			folder.delete();
		} else {
			fail("To do this test the folder must be created");
		}

		when:"the object is saved into the file system"
		dao.saveObject(airportTest);

		then:"the file must exist"
		File savedAirport = new File(path + SERIALIZED_PREFIX
				+ SERIALIZED_NAME_SEPARATOR + airportTest.getName()
				+ ".bin");
		savedAirport.exists() == true;
	}

	def "get an airport"(){
		setup:"The airport must exist in order to getit"
		String path = null;
		try {
			path = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
		} catch (IOException e1) {
			fail(e1.getMessage());
		}
		File savedFIle = new File(path + SERIALIZED_PREFIX
				+ SERIALIZED_NAME_SEPARATOR + airportTest.getName() + ".bin");
		if (!savedFIle.exists()) {
			try {
				dao.saveObject(airportTest);
			} catch (CreateAirportException e) {
				fail(e.getMessage());
			}
		}

		when:"the airport is retreived"
		Airport fetchAirport = dao.getObject(airportTest.getName(),
				new Object[0]);

		then:"The airport must be the the same as the one retreived"
		fetchAirport.getName()==airportTest.getName() && fetchAirport.getNumberOfLandingFields() == airportTest
				.getNumberOfLandingFields()
	}

	def "get more airports"(){

		setup:
		Airport newAirport = new Airport("new1", 10);;
		Airport newAirport2 = new Airport("new2", 10);;
		dao.saveObject(newAirport);
		dao.saveObject(newAirport2);

		when:"You should get a list of airports"
		def listAirports = dao.getObjects(null, new Object[0]);
		String path = ControlTowerUtils.getProperties(
				AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
				AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
		File folder = new File(path);
		int files = folder.listFiles(new FileFilter() {

					@Override
					public boolean accept(File arg0) {

						return arg0.getName().startsWith(SERIALIZED_PREFIX);
					}
				}).length;
		then:
		listAirports.size() == files
	}
}
