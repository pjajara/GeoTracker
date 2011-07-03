package com.vta.gtrack.exceptions;

public class BadLatitudeException extends Exception {

	private static final long serialVersionUID = 1L;

	public String toString(String objectName)
	{
		return "BadLatitudeException: Invalid Latitude Value";
	}
	
}
