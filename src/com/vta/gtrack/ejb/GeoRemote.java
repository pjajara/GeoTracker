package com.vta.gtrack.ejb;

import com.vta.gtrack.data.VtaBusInfo;


public interface GeoRemote 
{
	abstract void addBusPoint(VtaBusInfo busInfo);

	abstract String getMyBus(String route_id);
	
	abstract void addRouteInfo(String route_id, String location,float longitude, float latitude);
	
	abstract void deleteRoute();
}
