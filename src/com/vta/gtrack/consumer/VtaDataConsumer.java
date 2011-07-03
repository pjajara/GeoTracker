package com.vta.gtrack.consumer;

import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;
import com.vta.gtrack.data.VtaBusInfo;
import com.vta.gtrack.ejb.GeoRemote;



@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destination", propertyValue="/queue/VtaQueue"),
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="AUTO_ACKNOWLEDGE")
}, mappedName="VtaQueue")


public class VtaDataConsumer implements MessageListener 
{
	
	static final Logger logger = Logger.getLogger("DataConsumerBean");
    @Resource
    private MessageDrivenContext mdc;
    
    	
	@EJB
	GeoRemote GeoEJB;

	public void onMessage(Message inMessage) {
		ObjectMessage msg=null;
		try {
            if (inMessage instanceof ObjectMessage) {
                msg = (ObjectMessage) inMessage;
                
                VtaBusInfo data=(VtaBusInfo)(msg.getObject());
                logger.info("MESSAGE BEAN: Information Received for BusID: " + data.getBusId());
                GeoEJB.addBusPoint(data);
               
            } else 
            {
                logger.warning( "Error: Invalid Message Type: "+ inMessage.getClass().getName());
            }
        }
		catch (JMSException e)
		{   e.printStackTrace();
            mdc.setRollbackOnly();
        } 
		catch (Throwable te) 
		{   te.printStackTrace();
        }
	}
}
	