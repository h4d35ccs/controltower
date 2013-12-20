package es.upm.emse.softdesign.controltower.model.spock


import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft
import es.upm.emse.softdesign.controltower.model.airport.Airport
import es.upm.emse.softdesign.controltower.model.airport.ControlTower
import es.upm.emse.softdesign.controltower.model.airport.LandingField
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException
import es.upm.emse.softdesign.controltower.model.library.AircraftType
import es.upm.emse.softdesign.controltower.model.library.PlaneType
import es.upm.emse.softdesign.controltower.model.queue.imp.LandingQueue
import es.upm.emse.softdesign.controltower.model.queue.imp.ParkingQueue

class ControlTowerSpockTest extends spock.lang.Specification {


	private ControlTower fixture;
	private Airport airport;
	private List<LandingField> fields;
	static final AIRPLANE_ID_1="AA-1";
	static final AIRPLANE_ID_2="AA-2";
	static final AIRPLANE_ID_3="AA-3";
	static final AircraftType AIRCRAFT_TYPE = AircraftType.PLANE;
	static final PlaneType SUBTYPE = PlaneType.WIDE_BODY;

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

		setup:"created an aircraft"
		def aircraftID = AIRPLANE_ID_1;
		def fuelRemaning = 1;
		def AircraftType aircraftType = AIRCRAFT_TYPE;
		def PlaneType subtype = SUBTYPE;


		when:"added to the radar"
		fixture.addAircraftToRadar(aircraftID, fuelRemaning, AIRCRAFT_TYPE,
		subtype);


		then:"the aircraft must be on the landing queue"
		fixture.getLandingQueue().getAircraft().getId() == aircraftID;
	}


	def "remove one aircraft from the radar"(){

		setup:
		def  aircraftID = AIRPLANE_ID_1;
		def aircraftID2 = AIRPLANE_ID_2;
		def fuelRemaning = 1;
		AircraftType aircraftType = AIRCRAFT_TYPE;
		PlaneType subtype = SUBTYPE;
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
		AircraftType aircraftType = AIRCRAFT_TYPE;
		PlaneType subtype = SUBTYPE;
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
		createThreePlanes();

		when:
		landPlanes(3);


		then:
		this.airport.getLandingField(0).getAircraftOnField().getId() == AIRPLANE_ID_1;
		this.airport.getLandingField(1).getAircraftOnField().getId() == AIRPLANE_ID_2;
		this.airport.getLandingField(2).getAircraftOnField().getId() == AIRPLANE_ID_3;
	}

	def "park first plane to land "(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);
		fixture.cleanLastParkedAircraft(this.fields);

		then:

		this.airport.getLandingField(0).getAircraftOnField() == null;
	}
	def "park a picked plane to land "(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);
		fixture.cleanParkedAircraft(AIRPLANE_ID_2,this.fields);


		then:
		this.airport.getLandingField(1).getAircraftOnField() == null;
	}

	def "prepare aircraft to takeoff "(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
		.getAircraftOnField();



		then:

		preparedForTakeoff.getId() == AIRPLANE_ID_1 ;
		preparedForTakeoff.getTimeOfPrepareToTakeOff() != null;
		preparedForTakeoff.getTimeOfLastLanding() == null;
	}

	def "prepare aircraft to takeoff two aircrafts"(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
		.getAircraftOnField();
		Aircraft preparedForTakeoff2 = this.airport.getLandingField(1)
		.getAircraftOnField();



		then:

		preparedForTakeoff.getId() == AIRPLANE_ID_1 
		preparedForTakeoff.getTimeOfPrepareToTakeOff() != null;
		preparedForTakeoff.getTimeOfLastLanding() == null;
		preparedForTakeoff2.getId() == AIRPLANE_ID_2;
		preparedForTakeoff2.getTimeOfPrepareToTakeOff() != null;
		preparedForTakeoff2.getTimeOfLastLanding() == null;
	}

	def "prepare for takeoff aircraft but all landing fields were full"(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);

		fixture.cleanLastParkedAircraft(this.fields);
		fixture.cleanLastParkedAircraft(this.fields);
		fixture.prepareTakeOff(this.fields);
		Aircraft preparedForTakeoff = this.airport.getLandingField(0)
		.getAircraftOnField();

		then:
		preparedForTakeoff.getId() == AIRPLANE_ID_1;
		preparedForTakeoff.getTimeOfPrepareToTakeOff() != null;
		preparedForTakeoff.getTimeOfLastLanding() == null;
	}

	def "Land 3 aircrafts and try to park an non existing one "(){

		setup:
		createThreePlanes();
		def aircraftID4 = "AA-4";
		def fuelRemaning = 1;

		fixture.addAircraftToRadar(aircraftID4, fuelRemaning, AIRCRAFT_TYPE,
		SUBTYPE);

		when:
		landPlanes(3);

		fixture.cleanParkedAircraft( AIRPLANE_ID_2+ "0909090", this.fields);

		then:
		thrown(AircraftOperationException)
	}




	def "land an aircraft but every landing field is full"(){

		setup:
		createThreePlanes();
		def aircraftID4 = "AA-4";
		def fuelRemaning = 1;


		fixture.addAircraftToRadar(aircraftID4, fuelRemaning, AIRCRAFT_TYPE,
		SUBTYPE);

		when:
		landPlanes(3);
		
		Aircraft landed = fixture.landAircraft(this.fields);
		

		then:
		landed == null;
		fixture.getLandingQueue().getAircraft(aircraftID4).getId().equals(aircraftID4);
	}

	def "takeoff aircraft"(){

		setup:
		createThreePlanes();

		when:
		landPlanes(3);

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
		takeoff.getId() == AIRPLANE_ID_1;
		onField1 == null;
		onField2== null;
		onField3.getId()== AIRPLANE_ID_3;
	}

	/**
	 * Creates 3 planes and adds it into the radar
	 */
	def createThreePlanes(){

		def fuelRemaning = 1;
		AircraftType aircraftType = AIRCRAFT_TYPE;
		PlaneType subtype = SUBTYPE;
		fixture.addAircraftToRadar(AIRPLANE_ID_1, fuelRemaning, aircraftType,
		subtype);
		fixture.addAircraftToRadar(AIRPLANE_ID_2, fuelRemaning, aircraftType,
		subtype);
		fixture.addAircraftToRadar(AIRPLANE_ID_3, fuelRemaning, aircraftType,
		subtype);
	}

	def landPlanes(def numberOfPlanes){
		for (int i = 0; i < numberOfPlanes; i++) {
			fixture.landAircraft(this.fields);
		}
	}
}
