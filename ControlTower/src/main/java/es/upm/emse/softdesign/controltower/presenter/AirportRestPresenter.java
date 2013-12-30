package es.upm.emse.softdesign.controltower.presenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.dao.DaoFactory;
import es.upm.emse.softdesign.controltower.model.dao.imp.AirportDAO;
import es.upm.emse.softdesign.controltower.model.exception.CreateAirportException;
import es.upm.emse.softdesign.controltower.model.exception.GetAirportException;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;
import es.upm.emse.softdesign.controltower.presenter.helper.RestResponse;
import es.upm.emse.softdesign.controltower.presenter.library.ControllerLibrary;
import es.upm.emse.softdesign.controltower.presenter.library.RestStatus;

@Controller
@RequestMapping("/airport")
public class AirportRestPresenter {

	private static Logger logger = Logger
			.getLogger(AirportRestPresenter.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Object> createAirport(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "numberOfLandingsFields") int numberOfLandingFields,
			HttpServletRequest request) {

		RestResponse<Object> response = new RestResponse<Object>();

		if (!ControlTowerUtils.isEmptyObject(name)
				&& numberOfLandingFields != 0) {

			Airport airport = new Airport(name, numberOfLandingFields);

			try {

				AirportDAO airportDao = (AirportDAO) DaoFactory.getInstance()
						.getDAO(DaoFactory.AIRPORT_DAO);
				airportDao.saveObject(airport);
				response.setStatus(RestStatus.SUCCESS);
				response.setResponseObject(airport);

			} catch (CreateAirportException e) {
				response.setStatus(RestStatus.ERROR);
				String errorMsg = ControlTowerUtils.getErrorMsg(
						ControllerLibrary.PROPERTY_FILE_ERRORMSG,
						ControllerLibrary.PROPERTY_KEY_AIRPORT_SERIALIZE_ERROR,
						e);
				response.setMessage(errorMsg);
				logger.error(errorMsg, e);
			}
		} else {
			String errorMsg = ControlTowerUtils
					.getErrorMsg(
							ControllerLibrary.PROPERTY_FILE_ERRORMSG,
							ControllerLibrary.PROPERTY_KEY_AIRPORT_MISSING_PARAMS,
							null);
			response.setMessage(errorMsg);
		}

		return response;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	RestResponse<List<Airport>> getAirportsList() {

		RestResponse<List<Airport>> response = new RestResponse<List<Airport>>();
		List<Airport> airportList = null;

		try {

			AirportDAO airportDao = (AirportDAO) DaoFactory.getInstance()
					.getDAO(DaoFactory.AIRPORT_DAO);
			airportList = airportDao.getObjects(null, new Object[0]);
			response.setResponseObject(airportList);
			response.setStatus(RestStatus.SUCCESS);

		} catch (GetAirportException e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRPORT_GETLIST_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;

	}

	@RequestMapping(value = "/setcurrent", method = RequestMethod.POST)
	public @ResponseBody
	RestResponse<Airport> setCurrentAirport(
			@RequestParam(value = "name") String name,
			HttpServletRequest request) {

		RestResponse<Airport> response = new RestResponse<Airport>();
		Airport airport = null;

		try {
			// Se obtiene el aereopuerto y se coloca en la session
			AirportDAO airportDao = (AirportDAO) DaoFactory.getInstance()
					.getDAO(DaoFactory.AIRPORT_DAO);
			airport = airportDao.getObject(name, new Object[0]);
			request.getSession().setAttribute(
					ControllerLibrary.CURRENT_INSESSION_AIRPORT, airport);

			response.setResponseObject(airport);
			response.setStatus(RestStatus.SUCCESS);
			response.setResponseObject(airport);
		} catch (GetAirportException e) {
			String errorMsg = ControlTowerUtils.getErrorMsg(
					ControllerLibrary.PROPERTY_FILE_ERRORMSG,
					ControllerLibrary.PROPERTY_KEY_AIRPORT_GET_ERROR, e);
			response.setMessage(errorMsg);
		}

		return response;

	}

}