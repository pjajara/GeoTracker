package com.vta.gtrack.ejbclient;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import com.vta.gtrack.ejb.GeoRemote;

public class GeoClient {
	private Context ctx;
	private GeoRemote gCreator;

	void PrintRouteInfo() 
	{
		try{
		GeoRemote gClt = null; 
		gClt =  connectToCreator();
				
		FileOutputStream fout=new FileOutputStream("kml/VTABus.kml");
		System.out.println("Enter Route No. :");
		InputStreamReader in= new InputStreamReader(System.in);
		BufferedReader bin= new BufferedReader(in);
		String rno=bin.readLine();
		System.out.println("Finding the buses in route :"+rno);		
		String KML= gClt.getMyBus(rno);
		fout.write(KML.getBytes(),0, KML.length());
		fout.close();
		
		}
		catch(Exception e)
		{
		System.out.println("Exception: " + e.toString());
		}
		
	
	}
	private Context getContext() throws Exception {
		if (ctx != null)
			return ctx;

		Properties props = new Properties();
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		props.put(Context.URL_PKG_PREFIXES,
				"org.jboss.naming:org.jnp.interfaces");
		ctx = new InitialContext(props);

		return ctx;
	}


	private GeoRemote connectToCreator() throws Exception {
		Context ctx = getContext();
		
		try{
		
		Object ref = ctx.lookup("GeoEJB/remote");
		gCreator = (GeoRemote) PortableRemoteObject.narrow(ref,
				GeoRemote.class);

		}
		catch(Exception e)
		{
		
		System.out.println(e.toString());
		}
		return gCreator;
	}

	public static void main(String[] args) {
		System.out.println("===========================");
		System.out.println("VTA GeoTracking System Powered by SJSU \n");
		System.out.println("===========================");
		GeoClient clt = new GeoClient();
		
		clt.PrintRouteInfo();
	}
}
