package com.vta.gtrack.data;
import java.util.Date;


public class DataValidator 
	{
	
	public boolean validateBusInfo(Double longitude, Double latitude,
								String status, int passCount, Date lastUpdtTime)
	{
	boolean val1=validateStatus(status);
	boolean val2=validateLocation(longitude, latitude);
	
	if((val1&&val2)==false)
	{   
	return false;
	}
	else if(passCount>70||passCount<0)
	{
	return false;
	}
	else if (lastUpdtTime.after(new Date()))
	{		
	return false;
	}
	else
	return true;
	}
	
	protected boolean validateStatus(String status)
	{	
		try
		{			
		VtaBusInfo.Status sts=VtaBusInfo.Status.valueOf(status);
		System.out.println("Bus Status: "+sts);
		return true;
		}
		catch(IllegalArgumentException e)
		{
		System.out.println("Error: Invalid Status");		
		return false;}		
	}
	public boolean validateLocation( Double longitude, Double latitude)
	{ 
		if(longitude.intValue()<-123 || longitude.intValue()>-121)	
		{
		System.out.println("Error: Invalid Longitude");
		return false;
		}
		else if(latitude.intValue()<36 || latitude.intValue()>39)
		{
		System.out.println("Error: Invalid Latitude");
		return false;
		}
		else
		return true;
	}
}
