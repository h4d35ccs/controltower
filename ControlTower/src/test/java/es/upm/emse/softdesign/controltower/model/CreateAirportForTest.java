package es.upm.emse.softdesign.controltower.model;

import es.upm.emse.softdesign.controltower.model.airport.Airport;
import es.upm.emse.softdesign.controltower.model.dao.DaoFactory;
import es.upm.emse.softdesign.controltower.model.dao.GenericDAO;
import es.upm.emse.softdesign.controltower.model.exception.DaoOperationException;
/**
 * Class for creating airports for <b> TESTING ONLY </b>
 * @author hades
 *
 */
public final class CreateAirportForTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String airportName ="Airport Test";
		int fields = 2;
	
		Airport airportNew = new Airport(airportName,fields);
		GenericDAO<Airport> dao = (GenericDAO<Airport>) DaoFactory.getInstance().getDAO(DaoFactory.AIRPORT_DAO);
		try {
			dao.saveObject(airportNew);
			System.out.println("Airport created:"+airportNew.getName());
		} catch (DaoOperationException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
