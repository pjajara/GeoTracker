package com.vta.gtrack.exceptions;

public class BadLongitudeException extends Exception {

	private static final long serialVersionUID = 1L;

	public String toString(String objectName)
	{
		return "BadLongitudeException: Invalid Longitude Value";
	}
	
}
