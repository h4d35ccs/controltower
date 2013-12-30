package es.upm.emse.softdesign.controltower.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.dao.DaoFactory;
import es.upm.emse.softdesign.controltower.model.dao.imp.AirportDAO;
import es.upm.emse.softdesign.controltower.model.exception.CreateAirportException;
import es.upm.emse.softdesign.controltower.model.exception.GetAirportException;
import es.upm.emse.softdesign.controltower.model.library.AirportLibrary;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;

public class TestAirportDAO {

	private Airport airportTest;
	private AirportDAO dao;

	private static final String SERIALIZED_PREFIX;
	private static final String SERIALIZED_NAME_SEPARATOR;

	static {
		try {
			SERIALIZED_PREFIX = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.SERIALIZED_AIRPORT_PREFIX);

			SERIALIZED_NAME_SEPARATOR = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.SERIALIZED_NAME_SEPARATOR);
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@Before
	public void setUp() throws Exception {
		airportTest = new Airport("Airport Test", 3);
		dao = (AirportDAO) DaoFactory.getInstance().getDAO(
				DaoFactory.AIRPORT_DAO);
	}

	@After
	public void tearDown() throws Exception {
		airportTest = null;
		dao = null;
	}

	@Test
	public void testSaveObject() {
		try {
			dao.saveObject(airportTest);
			String path = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
			File savedAirport = new File(path + SERIALIZED_PREFIX
					+ SERIALIZED_NAME_SEPARATOR + airportTest.getName()
					+ ".bin");
			assertTrue(savedAirport.exists());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveObject2() {
		try {
			String path = ControlTowerUtils.getProperties(
					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
					AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
			File folder = new File(path);
			if (folder.exists()) {
				folder.delete();
			} else {
				fail("To do this test the folder must be created");
			}

			dao.saveObject(airportTest);

			File savedAirport = new File(path + SERIALIZED_PREFIX
					+ SERIALIZED_NAME_SEPARATOR + airportTest.getName()
					+ ".bin");
			assertTrue(savedAirport.exists());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetObject() {
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
		try {
			Airport fetchAirport = dao.getObject(airportTest.getName(),
					new Object[0]);

			assertTrue(fetchAirport.getName().equals(airportTest.getName())
					&& fetchAirport.getNumberOfLandingFields() == airportTest
							.getNumberOfLandingFields());
		} catch (GetAirportException e) {
			fail(e.getMessage());
		}

	}

//	@Test
//	public void testDeleteObject() {
//		Airport toDeleteAirport = new Airport("ToDelete", 10);
//		try {
//			dao.saveObject(toDeleteAirport);
//			dao.deleteObject(toDeleteAirport.getName(), new Object[0]);
//			String path = ControlTowerUtils.getProperties(
//					AirportLibrary.PROPERTY_FILE_CONTROLTOWER,
//					AirportLibrary.PROPERTY_KEY_SERIALIZED_OBJECTS_FOLDER);
//			File deleted = new File(path + SERIALIZED_PREFIX
//					+ SERIALIZED_NAME_SEPARATOR + airportTest.getName()
//					+ ".bin");
//			assertTrue(!deleted.exists());
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}

	@Test
	public void testGetObjects() {
		Airport newAirport = new Airport("new1", 10);
		Airport newAirport2 = new Airport("new2", 10);
		try {
			dao.saveObject(newAirport);
			dao.saveObject(newAirport2);
			List<Airport> list = dao.getObjects(null, new Object[0]);
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
			assertTrue(list.size() == files);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
