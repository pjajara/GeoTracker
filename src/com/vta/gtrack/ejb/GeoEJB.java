package com.vta.gtrack.ejb;

import javax.ejb.Remote;
import java.util.List;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.vta.gtrack.data.RouteInfo;
import com.vta.gtrack.data.VtaBusInfo;


@Stateless
@Remote(GeoRemote.class) 
public class GeoEJB implements GeoRemote {
	private static final long serialVersionUID = 8010393805340326247L;
	

	@PersistenceContext(unitName = "Geo_Tracker")
	private EntityManager data;
	public void deleteRoute(){
		
		data.createNativeQuery("Delete from RouteInfo").executeUpdate();
	}
	public void addRouteInfo(String route_id,String location,float longitude, float latitude){

		System.out.println("Adding Route information");
		System.out.println("RouteID: " + route_id+ "EJBloc: "+location+"EJBlon: "+longitude+"EJBlat: "+latitude);
		RouteInfo rInfo=null;
		rInfo = new RouteInfo();
		rInfo.setLoc_name(location);
		rInfo.setLongitude(longitude);
		rInfo.setLatitude(latitude);
		rInfo.setRouteID(route_id);
		data.merge(rInfo);
	}

	public void addBusPoint(VtaBusInfo busInfo)
	{
	System.out.println("Adding a bus point information");
	data.merge(busInfo);
	}

	@SuppressWarnings("unchecked")
	public String getMyBus(String routeId){

		String kml = null;

		String kmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
		        + "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n";

		String kmlDocument = "<Document>";
		String kmlDocumentEnd = "</Document>";

		String kmlPlacemarkStart ="<Placemark>\n";
		String kmlPlacemarkEnd ="</Placemark>\n";

		String kmlNameStart ="<name>";
		String kmlNameEnd ="</name>\n";

		String kmlPointStart ="<Point>\n";
		String kmlPointEnd ="</Point>\n";
		
		String kmlLineStringStart = "<LineString>\n";
		String kmlLineStringEnd = "</LineString>\n";

		String kmlCoordinatesStart ="<coordinates>\n";
		String kmlCoordinatesEnd ="</coordinates>\n";

		String kmlEnd = "</kml>\n";

		Query q = data.createNamedQuery("RouteInfo.getCoordinates");
		q.setParameter("routeId", routeId);
		Query qbus = data.createNamedQuery("VtaBusInfo.getBusForRoute");
		qbus.setParameter("routeId", routeId);

		try
		{
			kml= kmlHead;
			kml = kml+ kmlDocument +"\n";

			kml = kml + kmlNameStart+ "KmlFile" + kmlNameEnd;

			kml = kml + "\n<Style id=\"sh_ylw-pushpin\"> \n"+
				"<IconStyle> \n"+
				"<scale>1.2</scale> \n" +
				"</IconStyle> \n" +
				"<LineStyle> \n" +
				"<width>22</width> \n"+
				"</LineStyle> \n" +
				"</Style> \n" +
				"<StyleMap id=\"msn_ylw-pushpin\"> \n" +
				"<Pair>\n" +
				"<key>normal</key>\n" +
				"<styleUrl>#sn_ylw-pushpin</styleUrl>\n" +
				"</Pair>\n" +
				"<Pair>\n" +
				"<key>highlight</key>\n" +
				"<styleUrl>#sh_ylw-pushpin</styleUrl>\n" +
				"</Pair>\n" +
				"</StyleMap>\n" +
				"<Style id=\"sn_ylw-pushpin\">\n" +
				"<LineStyle>\n" +
				"<width>22</width>\n" +
				"</LineStyle>\n" +
				"</Style>\n" +
				"<Style id=\"busstopstyle\">"+
				"<IconStyle id=\"geoIconStyle\">"+
				"<color>ff0000ff</color>"+
				"<colorMode>normal</colorMode>"+
				"<scale>0.6</scale>"+
				"</IconStyle>"+
				"<LabelStyle id=\"geoLineStyle\">"+
				"<color>ffffffff</color>"+
				"<scale>0.6</scale>"+
				"</LabelStyle>"+
				"<LineStyle id=\"22355327\">"+
				"<color>ff0000ff</color>"+
				"<width>3.0</width>"+
				"</LineStyle>"+
				"</Style>"+
				"<Style id=\"geoRoute\">"+
				"<LabelStyle id=\"geoLineStyle2\">"+
				"<color>7fff0000</color>"+
				"<scale>0.6</scale>"+
				"</LabelStyle>"+
				"<LineStyle id=\"29791654\">"+
				"<color>7fff0000</color>"+
				"<width>3.0</width>"+
				"</LineStyle>"+
				"</Style>";

			kml=kml + kmlPlacemarkStart;
			kml=kml + kmlNameStart;
			kml=kml + "Route is " + routeId; 
			kml=kml + kmlNameEnd;
			kml = kml + "<styleUrl>#geoRoute</styleUrl>";
			kml= kml + kmlLineStringStart;
			kml=kml + kmlCoordinatesStart;

			List<RouteInfo> r = (List<RouteInfo>)q.getResultList();
			for(int i =0; i < r.size();i++)
			{
				kml=kml + r.get(i).getLongitude() + "," + r.get(i).getLatitude() + ",0 ";
			}
			
			kml=kml + kmlCoordinatesEnd;
			kml= kml +kmlLineStringEnd;
			kml=kml + kmlPlacemarkEnd;


			List<RouteInfo> r2 = (List<RouteInfo>)q.getResultList();
			for( int i =0; i < r.size();i++)
			{
				if( r.get(i).getLoc_name().equals("d"))
				{
					continue;
				}
				kml=kml + kmlPlacemarkStart;
				kml=kml + kmlNameStart;
				kml=kml +r.get(i).getLoc_name();
				kml=kml + kmlNameEnd;
				kml=kml + "<styleUrl>#busstopstyle</styleUrl>";

				kml=kml + kmlPointStart;
				kml=kml + kmlCoordinatesStart;
				kml=kml + r.get(i).getLongitude() + "," + r.get(i).getLatitude() + ",0\n";
				kml=kml + kmlCoordinatesEnd;
				kml=kml + kmlPointEnd;
				kml=kml + kmlPlacemarkEnd;
			}

			List<VtaBusInfo> v = (List<VtaBusInfo>)qbus.getResultList();
			for(int i =0; i < v.size();i++)
			{
				kml=kml + kmlPlacemarkStart;
				kml=kml + kmlNameStart;
				kml=kml +"|Bus ID: "+ v.get(i).getBusId()+ "|StopName: "+ v.get(i).getStop_Name()+ "|PCount: "+ v.get(i).getPassCount() + "|Status "+ v.get(i).getStatus()+ "|Op Code "+ v.get(i).getOperationalCode()+ "|TimeStamp : "+ v.get(i).getLastUpdtTime(); //name from route table
				kml=kml + kmlNameEnd;
				kml=kml + kmlPointStart;
				kml=kml + kmlCoordinatesStart;
				kml=kml + v.get(i).getLongiitude() + "," + v.get(i).getLatitude() + ",0\n";
				kml=kml + kmlCoordinatesEnd;
				kml=kml + kmlPointEnd;
				kml=kml + kmlPlacemarkEnd;
			}

			kml = kml + kmlDocumentEnd;
			kml=kml + kmlEnd;

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}


		return kml;
	}


	@AroundInvoke
	public Object preCheck(InvocationContext ctx) throws Exception {
		System.out.println("Captured Context !");
		return ctx.proceed();
	}
}
