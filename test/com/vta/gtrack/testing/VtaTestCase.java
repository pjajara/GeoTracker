package com.vta.gtrack.testing;

import java.io.File;
import java.util.Date;

import com.vta.gtrack.ejbclient.*;

import com.vta.gtrack.data.*;
import junit.framework.TestCase;

public class VtaTestCase extends TestCase {
DataValidator dv;
String msg,data[];
boolean flag;
int assert_value;
	protected void setUp() throws Exception {
		super.setUp();
		dv = new DataValidator();
	}
	

	public void testInvalidLatitude(){
		msg= "-124, 37.11 ";
		data=msg.split(",");
		int assert_value=0;
		flag=dv.validateLocation(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		if(flag)
		{
				assert_value=1;
		}
		else{
				assert_value=0;
		}
		assertEquals(0,assert_value);
	}
	
	public void testValidLatitude(){
		msg= "-122.333, 37.111 ";
		data=msg.split(",");
		int assert_value=0;
		flag=dv.validateLocation(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		if(flag)
		{
				assert_value=1;
		}
		else{
				assert_value=0;
		}
		assertEquals(1,assert_value);
	}
	
	public void testInvalidLongitude(){
		msg="-122.4444,40 ";
		data=msg.split(",");
		int assert_value=0;
		flag=dv.validateLocation(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		if(flag){
			assert_value=1;
		}
		else{
			assert_value=0;
		}
		assertEquals(0,assert_value);
	}
	
	public void testValidLongitude(){
		msg="-122.444,36.98998 ";
		data=msg.split(",");
		assert_value=0;
		flag=dv.validateLocation(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
		if(flag){
			assert_value=1;
		}
		else{
			assert_value=0;
		}
		assertEquals(1,assert_value);
	}
	
		
	public void testInvalidPassCount(){
		int psgCnt=79;
		flag = dv.validateBusInfo(-122.22, 36.88, "stop", psgCnt, null);
		
		if(flag){
			assert_value=1;
		}
		else{
			assert_value=0;
		}
		assertEquals(0,assert_value);
	}	
	
	public void testFileExistence(){
		//loadrouteinfo lri  = new loadrouteinfo();
		File f = new File("routecoordinates\\23.txt");
		flag=f.exists();
		f= new File("routecoordinates\\522.txt");
		flag=f.exists();
		f= new File("routecoordinates\\68.txt");
		flag=f.exists();
		
		if(flag){
			assert_value=1;
		}
		else{
			assert_value=0;
		}
		assertEquals(1,assert_value);
		
	}
		

	protected void tearDown() throws Exception {
		super.tearDown();
	}	
}
