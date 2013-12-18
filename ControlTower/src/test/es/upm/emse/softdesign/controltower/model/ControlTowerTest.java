package test.es.upm.emse.softdesign.controltower.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.airport.ControlTower;
import es.upm.emse.softdesign.controltower.model.airport.LandingField;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;
import es.upm.emse.softdesign.controltower.model.queue.imp.LandingQueue;

/**
 * The class <code>ControlTowerTest</code> contains tests for the class
 * <code>{@link ControlTower}</code>.
 * 
 * @generatedBy CodePro at 26/03/13 19:56
 * @author hades
 * @version $Revision: 1.0 $
 */
public class ControlTowerTest {

	private ControlTower fixture;
	private List<LandingField> fields;
	private Airport airport;

	/**
	 * Run the void addAircraftToRadar(String,int,AircraftType,Enum<?>) method
	 * test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 26/03/13 19:56
	 */
	@Test
	public void testAddAircraftToRadar_1() throws Exception {
		ControlTower fixture = ControlTower.getInstance();
		fixture.setLandingQueue(new LandingQueue(false));
		assertNotNull(fixture);
		String aircraftID = "AA-1";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;

		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);

		Aircraft plane = fixture.getLandingQueue().getAircraft();
		assertEquals(aircraftID, plane.getId());
	}

	/**
	 * Run the void removeAircraftFromRadar(String) method test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 26/03/13 19:56
	 */
	@Test
	public void testRemoveAircraftFromRadar_1() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);

		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.removeAircraftFromRadar(aircraftID);
		assertEquals(aircraftID2, fixture.getLandingQueue().getAircraft()
				.getId());

	}

	@Test
	public void testLandAircraft() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);

		fixture.landAircraft(this.fields);
		assertTrue(this.airport.getLandingField(0).getAircraftOnField().getId()
				.equals(aircraftID));

	}

	@Test
	public void testLandAircraft1() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		assertTrue(this.airport.getLandingField(0).getAircraftOnField().getId()
				.equals(aircraftID));
		assertTrue(this.airport.getLandingField(1).getAircraftOnField().getId()
				.equals(aircraftID2));
		assertTrue(this.airport.getLandingField(2).getAircraftOnField().getId()
				.equals(aircraftID3));

	}

	@Test
	public void testCleanLastParkedAircraft() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanLastParkedAircraft(this.fields);
		assertTrue(this.airport.getLandingField(0).getAircraftOnField() == null);

	}

	@Test
	public void testCleanParkedAircraft() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanParkedAircraft(aircraftID2, this.fields);
		assertTrue(this.airport.getLandingField(1).getAircraftOnField() == null);

	}

	@Test(expected = AircraftOperationException.class)
	public void testCleanParkedAircraft_1() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanParkedAircraft(aircraftID2 + "1", this.fields);
		assertTrue(this.airport.getLandingField(1).getAircraftOnField() == null);

	}

	@Test
	public void testPrepareForTakeoff() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
				.getAircraftOnField();
		assertTrue((preparedForTakeoff.getId() == aircraftID)
				&& (preparedForTakeoff.getTimeOfPrepareToTakeOff() != null)
				&& (preparedForTakeoff.getTimeOfLastLanding() == null));

	}

	@Test
	public void testPrepareForTakeoff2() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
				.getAircraftOnField();
		Aircraft preparedForTakeoff2 = this.airport.getLandingField(1)
				.getAircraftOnField();
		assertTrue((preparedForTakeoff.getId() == aircraftID)
				&& (preparedForTakeoff.getTimeOfPrepareToTakeOff() != null)
				&& (preparedForTakeoff.getTimeOfLastLanding() == null)
				&& (preparedForTakeoff2.getId() == aircraftID2)
				&& (preparedForTakeoff2.getTimeOfPrepareToTakeOff() != null)
				&& (preparedForTakeoff2.getTimeOfLastLanding() == null));

	}
	
	@Test
	public void testPrepareForTakeoffFull() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		String aircraftID4 = "AA-4";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID4, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}
		
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.landAircraft(this.fields);
		Aircraft preparedToTakeOff = fixture.prepareTakeOff(this.fields);
		assertTrue((preparedToTakeOff == null) && (fixture.getParkingQueue().getAircraft(aircraftID).getId().equals(aircraftID)));

	}
	
	@Test
	public void testLandFull() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		String aircraftID4 = "AA-4";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID4, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}
		
		Aircraft landed = fixture.landAircraft(this.fields);
		
		assertTrue((landed == null) && (fixture.getLandingQueue().getAircraft(aircraftID4).getId().equals(aircraftID4)));

	}

	@Test
	public void testTakeoff() throws Exception {

		String aircraftID = "AA-1";
		String aircraftID2 = "AA-2";
		String aircraftID3 = "AA-3";
		int fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft takeoff = fixture.takeOff(this.fields);
		Aircraft onField1 = this.airport.getLandingField(0)
				.getAircraftOnField();
		Aircraft onField2 = this.airport.getLandingField(1)
				.getAircraftOnField();
		Aircraft onField3 = this.airport.getLandingField(2)
				.getAircraftOnField();
		System.out.println(onField1+"---"+onField2 +"---"+ onField3);
		assertTrue(takeoff.getId() == aircraftID
				&& (onField1 == null && onField2== null && onField3
						.getId().equals(aircraftID3)));

	}

	/**
	 * Perform pre-test initialization.
	 * 
	 * @throws Exception
	 *             if the initialization fails for some reason
	 * 
	 * @generatedBy CodePro at 26/03/13 19:56
	 */
	@Before
	public void setUp() throws Exception {
		this.airport = new Airport("test", 3);
		this.fixture = airport.getControlTower();
		this.fields = airport.getLandingFields();

	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * @generatedBy CodePro at 26/03/13 19:56
	 */
	@After
	public void tearDown() throws Exception {
		this.airport = null;
		this.fixture = null;
		this.fields = null;
	}

	/**
	 * Launch the test.
	 * 
	 * @param args
	 *            the command line arguments
	 * 
	 * @generatedBy CodePro at 26/03/13 19:56
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ControlTowerTest.class);
	}
}