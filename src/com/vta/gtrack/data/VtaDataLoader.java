package com.vta.gtrack.data;


import java.io.File;
import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import com.vta.gtrack.producer.DataProducer;

public class VtaDataLoader
{  
	private  String[] stop_Name = null;
	private Double[] longitude=null;	
	private Double[] latitude=null;
	private String[] driverId = null;
	private String[] operationalCode = null;
	private String[] status=null;
	private int[] passCount=null;
	private String[] lastUpdtTime=null;
	private String[] bus_Id = null;
	private int cnt;
	
	private VtaBusInfo data=null;
	private FileInputStream fin=null;
	private DataValidator validate=null;
	int n=0; 
  
  
  public static void main(String[] args )
  { }
  public VtaDataLoader(String filePath)
  {
	  
	  try {
		  
	    File fXmlFile = new File("busInfo\\"+ filePath);
	    
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    Document doc = dBuilder.parse(fXmlFile);
	    doc.getDocumentElement().normalize();
	    cnt = 0;
	    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	    NodeList nList = doc.getElementsByTagName("Stop");
	    
	    NodeList list = doc.getElementsByTagName("Stop");
	    cnt = list.getLength();
	    System.out.println("cnt: "+list.getLength());	
	  
	    longitude=new Double[cnt];
		latitude=new Double[cnt];	
		status=new String[cnt];
		stop_Name=new String[cnt];
		bus_Id=new String[cnt];
		driverId=new String[cnt];	
		operationalCode=new String[cnt];
		passCount=new int[cnt];
		lastUpdtTime = new String[cnt];	
		
	    for (int temp = 0; temp < nList.getLength(); temp++) {
	 
	       Node nNode = nList.item(temp);	    
	       if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
	          Element eElement = (Element) nNode;
	          System.out.println("Status : "  + getTagValue("Status",eElement));
	          System.out.println("Seat Count : "  + getTagValue("PCount",eElement));
	          System.out.println("Longitude : "  + getTagValue("Longitude",eElement));
	          System.out.println("Latitude : "  + getTagValue("Latitude",eElement));
	          System.out.println("DriverId : "  + getTagValue("DriverId",eElement));
	          System.out.println("lastUpdtTime : "  + getTagValue("TimeStamp",eElement));
	          System.out.println("OperationalCode : "  + getTagValue("OperationalCode",eElement));
	          System.out.println("Stop-name : "  + getTagValue("Stop-name",eElement));
	          
	         
	      	status[temp]=getTagValue("Status",eElement);
	      	passCount[temp]=Integer.parseInt(getTagValue("PCount",eElement)); 
	      	longitude[temp]=Double.valueOf(getTagValue("Longitude",eElement));
	      	latitude[temp]=Double.valueOf(getTagValue("Latitude",eElement));
	      	driverId[temp]=getTagValue("DriverId",eElement);
	      	lastUpdtTime[temp]= getTagValue("TimeStamp",eElement);
	      	operationalCode[temp]=getTagValue("OperationalCode",eElement);
	      	stop_Name[temp]=getTagValue("Stop-name",eElement);
	      	bus_Id[temp]=getTagValue("Route-id",eElement);
	      	
	      }
	    }
	   	for(int i=0; i<status.length; i++)
	   	{
	  		System.out.println("Status-->" + status[i]);
	   			System.out.println("Time-->" + lastUpdtTime[i]);
	   			System.out.println("Stop_name-->" + stop_Name[i]);
	   			System.out.println("Latitude-->" + latitude[i]);
	   	}
	  
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	 }
	 
	 private static String getTagValue(String sTag, Element eElement){
	    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0); 
	 
	    return nValue.getNodeValue();    
	 }
	  
  public int  numberOfRecords()
  {
	  return cnt; 
  }
  
  
public  VtaBusInfo generateFakeData(int r)
	{		
			validate=new DataValidator();			 		
					System.out.println("Vta Bus Information validated ");	
					System.out.println("bus_Id[r]:"+bus_Id[r]);
					System.out.println("DataProducer.bus_no"+DataProducer.bus_no);
					System.out.println("longitude[r]:"+longitude[r]);
					System.out.println("latitude[r]:"+latitude[r]);
					System.out.println("status[r]:"+status[r]);
					System.out.println("passCount[r]:"+passCount[r]);
					System.out.println("lastUpdtTime[r]:"+lastUpdtTime[r]);
					System.out.println("stop_Name[r]:"+stop_Name[r]);
					System.out.println("driverId[r]:"+driverId[r]);
					System.out.println("operationalCode[r]:"+operationalCode[r]);
				data=new VtaBusInfo(bus_Id[r], DataProducer.bus_no, longitude[r],latitude[r],
				status[r], passCount[r], lastUpdtTime[r], stop_Name[r], driverId[r], operationalCode[r]);
					
				
		return data;		
	}
}
