package com.vta.gtrack.exceptions;

public class ValidateLatLong {

	public boolean validatecoordiantes(float longitude, float latitude) throws BadLongitudeException,BadLatitudeException
	{
		boolean result =true;
		if(longitude < -123||longitude >-121)
			{
			result=false;
			throw new BadLongitudeException();
			}
		else if(latitude > 39||latitude < 36)
		{
			result=false;
			throw new BadLatitudeException();
		}	
		return result;
	}
}
