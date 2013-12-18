package es.upm.emse.softdesign.controltower.model.queue.imp;

import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.exception.RemoveAircraftException;
import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;

/**
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:50:29
 */
public class ParkingQueue implements AirplaneQueue {

	
	private static Logger logger = Logger.getLogger(ParkingQueue.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Queue<Aircraft> parkingQueue;

	public ParkingQueue() {
		parkingQueue = new LinkedList<Aircraft>();
	}
	/**
	 * Gets the first element of the queue <br>
	 * uses {@link AbstractQueue#poll()} method to get the head of the queue
	 * @return {@link Aircraft}
	 */
	public Aircraft getAircraft() {
		return this.parkingQueue.poll();
	}

	/**
	 * puts an airplane into the queue <br>
	 * uses {@link AbstractQueue#offer(Object)} to insert the element
	 * @param aircraft
	 */
	public void putPlane(Aircraft aircraft) {
		this.parkingQueue.offer(aircraft);
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue#removeAircraft(java.lang.String)
	 */
	public void removeAircraft(String id) throws RemoveAircraftException {
		
		if(this.parkingQueue != null) {
			try {
				if(!ControlTowerUtils.removeObjectFromQueue(id, this.parkingQueue, "getId")) {
					logger.error(RemoveAircraftException.AIRCRAFT_ID_NOT_FOUND + id);
					throw new RemoveAircraftException(RemoveAircraftException.AIRCRAFT_ID_NOT_FOUND + id);
				}
			} catch (Exception e) {
				logger.error(RemoveAircraftException.GENERAL_ERROR,e);
				throw new RemoveAircraftException(RemoveAircraftException.GENERAL_ERROR,e);
				
			}
		}else { 
			logger.error(RemoveAircraftException.QUEUE_EMPTY_ERROR);
			throw new RemoveAircraftException(RemoveAircraftException.QUEUE_EMPTY_ERROR); 
		}
	}
	
	@Override
	public Aircraft getAircraft(String aircraftId) {
		for(Aircraft a:this.parkingQueue){
			if(a.getId().equals(aircraftId)){
				this.parkingQueue.remove(a);
				return a;
			}
		}
		return null;
	}

}