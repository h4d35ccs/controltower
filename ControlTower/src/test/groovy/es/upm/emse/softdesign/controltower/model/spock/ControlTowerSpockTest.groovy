package es.upm.emse.softdesign.controltower.model.spock

import static org.junit.Assert.assertTrue;

import java.util.List;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.airport.ControlTower;
import es.upm.emse.softdesign.controltower.model.airport.LandingField;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.library.PlaneType;
import es.upm.emse.softdesign.controltower.model.queue.imp.LandingQueue;
import es.upm.emse.softdesign.controltower.model.queue.imp.ParkingQueue

class ControlTowerSpockTest extends spock.lang.Specification {


	private ControlTower fixture;
	private Airport airport;
	private List<LandingField> fields;

	def setup() {
		airport = new Airport("test", 3);
		fixture = this.airport.getControlTower();
		fixture.setLandingQueue(new LandingQueue(false));
		fixture.setParkingQueue(new ParkingQueue());
		fixture = airport.getControlTower();
		fields = airport.getLandingFields();
	}

	def cleanup() {
		airport = null;
		fixture = null;
		fields = null;
	}

	def "Create one aircraft and add it into the radar"(){

		setup:
		def aircraftID = "AA-1";
		def fuelRemaning = 1;
		def AircraftType aircraftType = AircraftType.PLANE;
		def PlaneType subtype = PlaneType.WIDE_BODY;


		when:
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);


		then:
		fixture.getLandingQueue().getAircraft().getId() == aircraftID;
	}


	def "remove one aircraft from the radar"(){

		setup:
		def  aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);


		when:
		fixture.removeAircraftFromRadar(aircraftID);


		then:
		fixture.getLandingQueue().getAircraft()
				.getId() == aircraftID2;
	}

	def "Land an aircraft"(){

		setup:
		def  aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);


		when:
		fixture.landAircraft(this.fields);


		then:
		this.airport.getLandingField(0).getAircraftOnField().getId() == aircraftID;
	}

	def "Land 3 aircrafts and get them in the landing order first AA-1 then AA-2"(){

		setup:
		def aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def aircraftID3 = "AA-3";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		when:
		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		then:
		this.airport.getLandingField(0).getAircraftOnField().getId() == aircraftID;
		this.airport.getLandingField(1).getAircraftOnField().getId() == aircraftID2;
		this.airport.getLandingField(2).getAircraftOnField().getId() == aircraftID3;
	}

	def "Land 3 aircrafts and try to park an non existing one "(){

		setup:
		def aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def aircraftID3 = "AA-3";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		when:
		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}
		fixture.cleanParkedAircraft(aircraftID2 + "0909090", this.fields);

		then:
		thrown(AircraftOperationException)
	}

	def "prepare for takeoff aircraft"(){

		setup:
		def aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def aircraftID3 = "AA-3";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		when:
		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
				.getAircraftOnField();

		then:
		preparedForTakeoff.getId() == aircraftID;
		preparedForTakeoff.getTimeOfPrepareToTakeOff() != null;
		preparedForTakeoff.getTimeOfLastLanding() == null;
	}

	def "land an aircraft"(){

		setup:
		def aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def aircraftID3 = "AA-3";
		def aircraftID4 = "AA-4";
		def fuelRemaning = 1;

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

		when:
		for (int i = 0; i < 3; i++) {
			fixture.landAircraft(this.fields);
		}

		Aircraft landed = fixture.landAircraft(this.fields);

		then:
		landed == null;
		fixture.getLandingQueue().getAircraft(aircraftID4).getId() == aircraftID4;
	}

	def "takeoff aircraft"(){

		setup:
		def aircraftID = "AA-1";
		def aircraftID2 = "AA-2";
		def aircraftID3 = "AA-3";
		def fuelRemaning = 1;
		AircraftType aircraftType = AircraftType.PLANE;
		PlaneType subtype = PlaneType.WIDE_BODY;
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID2, fuelRemaning, aircraftType,
				subtype);
		fixture.addAircraftToRadar(aircraftID3, fuelRemaning, aircraftType,
				subtype);

		when:
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

		then:
		takeoff.getId() == aircraftID;
		onField1 == null;
		onField2== null;
		onField3.getId()== aircraftID3;
	}
}
