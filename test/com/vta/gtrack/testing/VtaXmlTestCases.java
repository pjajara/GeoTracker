/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vta.gtrack.testing;

import org.xml.sax.*;
import org.junit.*;
import org.w3c.dom.Document;
import junit.framework.TestCase;



public class VtaXmlTestCases extends TestCase{

    public VtaXmlTestCases() {
    }

    @Before
    
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testForInvalidContent() throws SAXException
    {
        System.out.println("Validating file with invalid content");
        ValidateXML vXml = new ValidateXML();
        Document doc = vXml.validateFile("busInfo\\VTA23.xml");
        assertNull(doc);        
    }

    
//    public void testForMissingElement() throws SAXException
//    {
//        System.out.println("Validating file with missing Element");
//        ValidateXML vXml = new ValidateXML();
//        Document doc = vXml.validateFile("src\\java\\XML\\MissingElement.xml");
//        assertNull(doc);
//    }
//
//    @Test
//    public void testForValidFile() throws SAXException
//    {
//        System.out.println("Validating file with Proper content and Proper Elemtents");
//        ValidateXML vXml = new ValidateXML();
//        Document doc = vXml.validateFile("routecoordinates\\66.xml");
//        assertNotNull(doc);
//    }
}