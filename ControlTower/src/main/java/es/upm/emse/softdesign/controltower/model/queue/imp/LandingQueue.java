package es.upm.emse.softdesign.controltower.model.queue.imp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

import es.upm.emse.softdesign.controltower.model.aircraft.Aircraft;
import es.upm.emse.softdesign.controltower.model.exception.RemoveAircraftException;
import es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue;
import es.upm.emse.softdesign.controltower.model.util.ControlTowerUtils;

/**
 * Represent a landing queue
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:50:29
 */
public class LandingQueue implements AirplaneQueue,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Queue<Aircraft> landingQueue;
	private static Logger logger = Logger.getLogger(LandingQueue.class);

	/**
	 * Constructor
	 * 
	 * @param prioritize
	 *            boolean that indicate if the queue must use some priority
	 */
	public LandingQueue(boolean prioritize) {
		//Uses a priorityqueue
		if (prioritize) {

			this.landingQueue = new PriorityQueue<Aircraft>(10, new Comparator<Aircraft>(){ 

				@Override
				//compares the amount of fuel remaning to sort the queue see java.util.Comparator javadoc 
				public int compare(Aircraft arg0, Aircraft arg1) {
				    int toCompare = 0;
				    
				    if(arg0.getFuelRemaning() > arg1.getFuelRemaning()){
				    	toCompare = 1;
				    }else if(arg0.getFuelRemaning() < arg1.getFuelRemaning()){
				    	toCompare = -1;
				    }else if (arg0.getFuelRemaning() == arg1.getFuelRemaning()){
				    	 toCompare = 0;
				    }
					return toCompare;
				}
				
			});
			//uses a simple linkedlist
		} else {

			this.landingQueue = new LinkedList<Aircraft>();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue#getAircraft(String aircraftId)
	 */
	public Aircraft getAircraft(String aircraftId) {
		for(Aircraft a:this.landingQueue){
			if(a.getId().equals(aircraftId)){
				this.landingQueue.remove(a);
				return a;
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue#getAircraft()
	 */
	public Aircraft getAircraft() {
		return this.landingQueue.poll();
	}

	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue#putPlane(es.upm.emse.softdesign.controltower.model.aircraft.Aircraft)
	 */
	public void putPlane(Aircraft aircraft) {
		this.landingQueue.offer(aircraft);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see es.upm.emse.softdesign.controltower.model.queue.AirplaneQueue#removeAircraft(java.lang.String)
	 */
	public void removeAircraft(String id) throws RemoveAircraftException {
		if(this.landingQueue != null){ 
			try {
				if(!ControlTowerUtils.removeObjectFromQueue(id, this.landingQueue, "getId")){ 
					logger.error(RemoveAircraftException.AIRCRAFT_ID_NOT_FOUND + id);
					throw new RemoveAircraftException(RemoveAircraftException.AIRCRAFT_ID_NOT_FOUND + id);
				}
			} catch (Exception e) {
				logger.error(RemoveAircraftException.GENERAL_ERROR,e);
				throw new RemoveAircraftException(RemoveAircraftException.GENERAL_ERROR,e);
				
			}
		}else{  
			logger.error(RemoveAircraftException.QUEUE_EMPTY_ERROR);
			throw new RemoveAircraftException(RemoveAircraftException.QUEUE_EMPTY_ERROR); 
		}
		
	}

}