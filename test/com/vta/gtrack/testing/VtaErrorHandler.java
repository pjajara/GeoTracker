/*
 * Error handler class
 */

package com.vta.gtrack.testing;

import org.xml.sax.*;

public class VtaErrorHandler implements ErrorHandler
{
        // All of these methods are defined by ErrorHandler class
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("Exception occured at line number : " + exception.getLineNumber() + " and the reason is : "+ exception.getMessage());
            throw new SAXException();
        }

        public void error(SAXParseException exception) throws SAXException {
                  System.out.println("Exception occured at line number : " + exception.getLineNumber() + " and the reason is : "+ exception.getMessage());
                  throw new SAXException();
            
        }

        public void fatalError(SAXParseException exception) throws SAXException {
                  System.out.println("Exception occured at line number : " + exception.getLineNumber() + " and the reason is : "+ exception.getMessage());
                  throw new SAXException();
            
        }

}
