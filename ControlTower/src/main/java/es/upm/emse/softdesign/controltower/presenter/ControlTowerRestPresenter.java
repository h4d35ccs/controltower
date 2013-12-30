package es.upm.emse.softdesign.controltower.presenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.airport.ControlTower;
import es.upm.emse.softdesign.controltower.model.airport.LandingField;
import es.upm.emse.softdesign.controltower.model.exception.AddAircraftException;
import es.upm.emse.softdesign.controltower.model.exception.AircraftOperationException;
import es.upm.emse.softdesign.controltower.model.exception.NotInSessionException;
import es.upm.emse.softdesign.controltower.model.exception.RemoveAircraftException;
import es.upm.emse.softdesign.controltower.model.library.AircraftType;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;
import es.upm.emse.softdesign.controltower.presenter.helper.RestResponse;
import es.upm.emse.softdesign.controltower.presenter.library.ControllerLibrary;
import es.upm.emse.softdesign.controltower.presenter.library.RestStatus;

@Controller
@RequestMapping("/control")
public class ControlTowerRestPresenter {
	private static Logger logger = Logger
			.getLogger(ControlTowerRestPresenter.class);

	@RequestMapping(value = "/aircraft/addtoradar", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Object> addAircraftToRadar(
			@RequestParam(value = "type") String type,
			@RequestParam(value = "subtype", required = false) String subtype,
			@RequestParam(value = "fuel", required = false) Integer fuel,
			HttpServletRequest request) {

		RestResponse<Object> response = new RestResponse<Object>();
		Aircraft created = null;
		try {

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);

			ControlTower control = airport.getControlTower();
			AircraftType aircraftType = AircraftType
					.valueOf(type.toUpperCase());

			// Posiblemente adicionar la logica en el metodo addAircraftToRadar
			// par que se genere un id unico, verificando las colas
			String aircraftID = ControlTowerUtils.generateAircraftId(
					aircraftType, aircraftType.getSubType(subtype));
			created = control.addAircraftToRadar(aircraftID, (fuel == null ? 0
					: fuel), aircraftType, aircraftType.getSubType(subtype));

			response.setResponseObject(created);
			response.setStatus(RestStatus.SUCCESS);

		} catch (AddAircraftException e) {
			logger.error("Error while adding the plane to radar", e);
			String errorMsg = ControlTowerUtils
					.getErrorMsg(
							ControllerLibrary.PROPERTY_FILE_ERRORMSG,
							ControllerLibrary.PROPERTY_KEY_CONTROL_ADDAIRRADAR_ERROR,
							e);
			response.setMessage(errorMsg);
		} catch (NotInSessionException e) {
			logger.error(
					"Error while adding the plane to radar, Airport not in session",
					e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}

	@RequestMapping(value = "/aircraft/park", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Aircraft> parkAircraft(
			@RequestParam(value = "aircraftid", required = false) String aircraftId,
			HttpServletRequest request) {

		RestResponse<Aircraft> response = new RestResponse<Aircraft>();

		try {

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();

			Aircraft parked = null;
			List<LandingField> landingFields = airport.getLandingFields();

			// if the aircraftid is null or blank i use cleanLastParkedAircraft
			// otherwise i park the aircraft by the given id
			if (aircraftId != null
					&& (!aircraftId.equals("") || !aircraftId
							.equalsIgnoreCase("null"))) {//en caso que javascript me mande la palabra null

				parked = control.cleanParkedAircraft(aircraftId, landingFields);

			} else {

				parked = control.cleanLastParkedAircraft(landingFields);
			}

			response.setResponseObject(parked);
			response.setStatus(RestStatus.SUCCESS);

		} catch (AircraftOperationException e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_LAND_ERROR, e);
			response.setMessage(errorMsg);
		} catch (NotInSessionException e) {
			logger.error("Error parking plane, Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}

	@RequestMapping(value = "/aircraft/land", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Aircraft> landAircraft(
			@RequestParam(value = "aircraftid", required = false) String aircraftId,
			HttpServletRequest request) {

		RestResponse<Aircraft> response = new RestResponse<Aircraft>();

		try {
			Aircraft landedAircraft = null;

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();
			
			// If an specific aircraft ID is given
			if(aircraftId!=null){
				// Call method to land specific aircraft
				landedAircraft = control.landAircraft(airport.getLandingFields(), aircraftId);
			}
			else{
				// Land older aicraft in the landing queu
				landedAircraft = control.landAircraft(airport.getLandingFields());
			}
			if (landedAircraft == null) {
				String msg = ControlTowerUtils
						.getProperties(
								ControllerLibrary.PROPERTY_FILE_ERRORMSG,
								ControllerLibrary.PROPERTY_KEY_PLANE_GOING_BACK_LANDINGQUEUE);
				response.setMessage(msg);
				response.setStatus(RestStatus.ERROR);
			} else {
				response.setResponseObject(landedAircraft);
				response.setStatus(RestStatus.SUCCESS);
			}

		} catch (AircraftOperationException e) {
			if(e.getMessage()!=null){
				response.setMessage(e.getMessage());
			}
			else{
				String errorMsg = ControlTowerUtils.getErrorMsg(
						ControllerLibrary.PROPERTY_FILE_ERRORMSG,
						ControllerLibrary.PROPERTY_KEY_AIRCRAFT_LAND_ERROR, e);
				response.setMessage(errorMsg);
			}
		} catch (NotInSessionException e) {
			logger.error("Error landing plane,Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		} catch (Exception e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_LAND_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}
	
	@RequestMapping(value = "/aircraft/takeoff/prepare", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Aircraft> prepareAircraftTakeOff(
			@RequestParam(value = "aircraftid", required = false) String aircraftId,
			HttpServletRequest request) {

		RestResponse<Aircraft> response = new RestResponse<Aircraft>();

		try {
			Aircraft preparedAircraft = null;

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();
			
			// If an specific aircraft ID is given
			if(aircraftId!=null){
				// Call method to land specific aircraft
				preparedAircraft = control.prepareTakeOff(airport.getLandingFields(), aircraftId);
			}
			else{
				// Prepare take off of older aicraft in the landing queu
				preparedAircraft = control.prepareTakeOff(airport.getLandingFields());
			}
			//si fue null la aeronave volvio a la cola
			if(preparedAircraft == null){
				String msg = ControlTowerUtils
						.getProperties(
								ControllerLibrary.PROPERTY_FILE_ERRORMSG,
								ControllerLibrary.PROPERTY_KEY_PLANE_GOING_BACK_LANDINGQUEUE);
				response.setMessage(msg);
				response.setStatus(RestStatus.ERROR);
			}else{
				response.setResponseObject(preparedAircraft);
				response.setStatus(RestStatus.SUCCESS);
			}

		} catch (AircraftOperationException e) {
			if(e.getMessage()!=null){
				response.setMessage(e.getMessage());
			}
			else{
				String errorMsg = ControlTowerUtils.getErrorMsg(
						ControllerLibrary.PROPERTY_FILE_ERRORMSG,
						ControllerLibrary.PROPERTY_KEY_AIRCRAFT_PREPARETAKEOFF_ERROR, e);
				response.setMessage(errorMsg);
			}
		} catch (NotInSessionException e) {
			logger.error("Error landing plane,Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		} catch (Exception e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_PREPARETAKEOFF_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}
	
	@RequestMapping(value = "/aircraft/takeoff/do", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Aircraft> takeOffAircraft(HttpServletRequest request) {

		RestResponse<Aircraft> response = new RestResponse<Aircraft>();

		try {
			Aircraft takeOffAircraft = null;

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();
			
			takeOffAircraft = control.takeOff(airport.getLandingFields());
			
			response.setResponseObject(takeOffAircraft);
			response.setStatus(RestStatus.SUCCESS);

		} catch (AircraftOperationException e) {
			if(e.getMessage()!=null){
				response.setMessage(e.getMessage());
			}
			else{
				String errorMsg = ControlTowerUtils.getErrorMsg(
						ControllerLibrary.PROPERTY_FILE_ERRORMSG,
						ControllerLibrary.PROPERTY_KEY_AIRCRAFT_DOTAKEOFF_ERROR, e);
				response.setMessage(errorMsg);
			}
		} catch (NotInSessionException e) {
			logger.error("Error landing plane,Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		} catch (Exception e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_PREPARETAKEOFF_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}

	@RequestMapping(value = "/aircraft/removefromradar", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Object> removeAircraftFromRadar(
			@RequestParam(value = "aircraftid") String aircraftId,
			HttpServletRequest request) {

		RestResponse<Object> response = new RestResponse<Object>();

		try {

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();
			control.removeAircraftFromRadar(aircraftId);

			response.setStatus(RestStatus.SUCCESS);

		} catch (RemoveAircraftException e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_REMOVE_ERROR, e);
			response.setMessage(errorMsg);
		} catch (NotInSessionException e) {
			logger.error("Error landing plane, Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		} catch (Exception e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRCRAFT_REMOVE_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;
	}

	@RequestMapping(value = "/landingfield/clean", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Aircraft> cleanLastParkedAircraft(HttpServletRequest request) {

		RestResponse<Aircraft> response = new RestResponse<Aircraft>();

		try {

			Aircraft parkedAircraft = null;

			Airport airport = (Airport) ControlTowerUtils.getObjectFromSession(
					request, ControllerLibrary.CURRENT_INSESSION_AIRPORT);
			ControlTower control = airport.getControlTower();
			parkedAircraft = control.cleanLastParkedAircraft(airport
					.getLandingFields());

			response.setResponseObject(parkedAircraft);
			response.setStatus(RestStatus.SUCCESS);

		} catch (AircraftOperationException e) {
			String errorMsg = ControlTowerUtils
					.getErrorMsg(
							ControllerLibrary.PROPERTY_FILE_ERRORMSG,
							ControllerLibrary.PROPERTY_KEY_LANDINGFIELDS_CLEAN_ERROR,
							e);
			response.setMessage(errorMsg);
		} catch (NotInSessionException e) {
			logger.error("Error landing plane,Airport not in session", e);
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_NOAIRPORT_SESSION__ERROR, e);
			response.setMessage(errorMsg);
		} catch (Exception e) {
			String errorMsg = ControlTowerUtils
					.getErrorMsg(
							ControllerLibrary.PROPERTY_FILE_ERRORMSG,
							ControllerLibrary.PROPERTY_KEY_LANDINGFIELDS_CLEAN_ERROR,
							e);
			response.setMessage(errorMsg);
		}

		return response;
	}

}
