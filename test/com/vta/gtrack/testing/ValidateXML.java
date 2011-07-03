/*
* Validation class to validate input data
*/
package com.vta.gtrack.testing;

import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import org.w3c.dom.*;

public class ValidateXML {

    /*Declaring a constructor*/
    public ValidateXML() {
    }

    //method to validate the file
    public Document validateFile(String fileName) {
        Document doc = null;
        try {
       
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	    docBuilderFactory.setValidating(true);

            //setting attributes
        	docBuilderFactory.setAttribute(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");

            docBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", "schema.xsd");

            /*Creating instance of document builder class*/
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            //setting error handler - WMBErrorHandler class
            docBuilder.setErrorHandler(new VtaErrorHandler());

            /*Parse the input file*/
            doc = docBuilder.parse(new File(fileName));
           
        } catch (SAXException ex) {           
            System.out.println("SAXException thrown while parsing the file " + ex.toString());
            doc = null;
        } catch (Exception ex) {
            System.out.println("Exception raised while parsing the file " + ex.toString());
            doc = null;
        }
        return doc;
    }
}
