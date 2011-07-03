package com.vta.gtrack.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.jms.*;
import javax.naming.*;
import javax.jms.ConnectionFactory;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.vta.gtrack.data.VtaBusInfo;
import com.vta.gtrack.data.VtaDataLoader;


public class DataProducer {

	private Connection connection;
	private Session session;
	private Queue queue;
	private MessageProducer messageProducer = null;
    private ObjectMessage message= null;
    private VtaBusInfo data=null;
    private static VtaDataLoader loader =null;
    private int priority=4; // default priority
    public static String bus_no = null;
    public static String file_Name = null;
    
	
	public static void main(String[] args) 
	{
	DataProducer dg=new DataProducer();
	int n=loader.numberOfRecords();
   	int v=0; 
     	
    	System.out.println(" Sending all messages sequentially ");
		for(int i=v;i<n;i++)
			{				
				dg.vtaBusInfoProducer(i);
				v++;	
			}
	}  	
	
	
	
	public DataProducer()
	{
		
		System.out.println("Enter Bus No. from 23, 68 and 522");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			
	    	bus_no = br.readLine();
	    	if(bus_no.isEmpty()){
	    		System.out.println("Please enter valid Bus Number");
	    		System.exit(0);
	       	}
	    	bus_no = bus_no.trim();
	    	
	    	System.out.println("bus no" +bus_no);
			}
	    catch (IOException e1)
	    {	
			e1.printStackTrace();	
			System.out.println("Invalid Bus Number");
		}
	    file_Name = "VTA"+bus_no+".xml";
	    System.out.println("file name" +file_Name);
	    
		loader=new VtaDataLoader(file_Name);	
	}
	 
	public void vtaBusInfoProducer(int r)
	{
		try{
	
	Properties properties = new Properties();
	
    properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
    properties.put(Context.URL_PKG_PREFIXES, "org.jnp.interfaces");
    properties.put(Context.PROVIDER_URL, "localhost:1099");
	
	InitialContext jndi = new InitialContext(properties);
	ConnectionFactory conFactory = (ConnectionFactory)jndi.lookup("java:/XAConnectionFactory");
	connection = conFactory.createConnection();	
	session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
	
	
	 data= loader.generateFakeData(r);   
	 System.out.println("After fake data");
	    String vtaQueue="queue/VtaQueue";
	    queue = (Queue)jndi.lookup(vtaQueue);
		messageProducer = session.createProducer(queue);
		connection.start();
		System.out.println("After start");
	    message = session.createObjectMessage();
	    message.setObject(data);
	    System.out.println("Sending message: "+data.getBusId());
	    messageProducer.send(message, DeliveryMode.NON_PERSISTENT, priority, 20000);
      }
		catch(NamingException NE)
		{
			System.out.println("Naming Exception: "+NE);
		}
		catch(JMSException JMSE)
		{
			System.out.println("JMS Exception: "+JMSE);
			
		}	
		
		}
	
}
