package ConformanceChecking;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

public class xmlValidator
{
    private Source xmlFile;
    private Schema schema;
    private SchemaFactory schemaFactory;
    
    xmlValidator(Source file) throws SAXException, MalformedURLException
    {
        URL schemaFile = new URL("http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd");
        xmlFile = new StreamSource(new File("web.xml"));
        schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schema = schemaFactory.newSchema(schemaFile);
    }
    
    public boolean validateXMLFile() throws SAXException, IOException
    {
        Validator validator = schema.newValidator();
        try
        {
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        }
        catch (SAXException e)
        {
            System.out.println(xmlFile.getSystemId() + " is NOT valid");
            System.out.println("Reason: " + e.getLocalizedMessage());
            return false;
        }
        
        return true;
    }
}
