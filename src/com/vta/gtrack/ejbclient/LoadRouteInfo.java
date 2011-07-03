package com.vta.gtrack.ejbclient;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import com.vta.gtrack.ejb.GeoRemote;
import com.vta.gtrack.exceptions.BadLatitudeException;
import com.vta.gtrack.exceptions.BadLongitudeException;
import com.vta.gtrack.exceptions.ValidateLatLong;

import java.io.*;
import java.util.Properties;
import java.util.StringTokenizer;

public class LoadRouteInfo {

	private Context ctx;
	private GeoRemote gCreator;

	private ValidateLatLong validation = new ValidateLatLong();
	
	void deleteroutes()
	{
		GeoRemote gClt = null; 
		try {
			gClt =  connectToCreator();
			
			gClt.deleteRoute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void readroutefiles()throws BadLongitudeException,BadLatitudeException
	{
		try
		{	
			GeoRemote gClt = null; 
			gClt =  connectToCreator();
			
          File f = new File("routecoordinates");
      	  File routefiles[] = f.listFiles();

      	  for(int i=0;i<routefiles.length;i++)
      	  {
      		  if(routefiles[i].isFile())
      		  {
      			  System.out.println(routefiles[i]);
      			  
      			BufferedReader bufRdr  = new BufferedReader(new FileReader(routefiles[i]));
      			String line = null;
      			String location=null;
      			float longitude=0;
      			float latitude=0;
      			int col = 0;
      			
      			while((line = bufRdr.readLine()) != null)
      			{
      				StringTokenizer st = new StringTokenizer(line,",");
      				String [] coordinates = new String[st.countTokens()];
      				col=0;
      				while (st.hasMoreTokens())
      				{
      					coordinates[col]= st.nextToken();
      					col++;
      				}
      				
      				location=coordinates[0];
      				longitude=Float.parseFloat(coordinates[1]);
      				latitude=Float.parseFloat(coordinates[2]);
      				System.out.println("Location: "+coordinates[0]+"Longitude: "+coordinates[1]+"Latitude"+coordinates[2]);
      				
      				if(validation.validatecoordiantes(longitude, latitude))
      				{
      					String route_id = null;
      					route_id = routefiles[i].toString().substring(17,routefiles[i].toString().length()-4);
      					gClt.addRouteInfo(route_id,coordinates[0], Float.parseFloat(coordinates[1]), Float.parseFloat(coordinates[2]));
      				}
      			}
      			bufRdr.close(); 
      		  }
      	  }
      	  
		}
		catch(BadLongitudeException e){
			System.out.println(e.toString());
		}
		catch(BadLatitudeException e){
			System.out.println(e.toString());
		}
        catch(Exception e){
        	System.out.println("Error: File Read Operation");
        }
            
	}
	
	private Context getContext() throws Exception {
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
	
	public static void main(String args[])
	{
		System.out.println("Loading Route Information ...");
		LoadRouteInfo clt = new LoadRouteInfo();
		try{
			clt.deleteroutes();
			clt.readroutefiles();
		}
		catch(BadLongitudeException e){
			System.out.println(e.toString());
		}
		catch(BadLatitudeException e){
			System.out.println(e.toString());
		}
		
	}
}
