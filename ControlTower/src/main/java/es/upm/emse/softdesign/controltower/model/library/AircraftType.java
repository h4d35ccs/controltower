package es.upm.emse.softdesign.controltower.model.library;

/**
 * @author hades
 * @version 1.0
 * @created 20-mar-2013 0:48:39
 */
public enum AircraftType {

	PLANE;
	
	public Enum<?> getSubType(String subtype){
		switch(this){
			case PLANE:
				if(subtype==null){
					return PlaneType.WIDE_BODY;
				}
				return PlaneType.valueOf(subtype);
		}
		return null;
	}
}