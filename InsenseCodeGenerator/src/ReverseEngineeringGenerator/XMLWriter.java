package ReverseEngineeringGenerator;

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Units.Interface;
import Units.Struct;
import Units.BasicUnits.Channel;
import AbstractGenerator.Writer;

public class XMLWriter extends Writer
{
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder        docBuilder;
    private Document               doc;
    private TransformerFactory     transformerFactory;
    private Transformer            transformer;
    private DOMSource              source;
    private Element                rootElement;
    private Element                lastComputationalUnit;
    
    public XMLWriter ()
    {
        docFactory = DocumentBuilderFactory.newInstance ( );
        try
        {
            docBuilder = docFactory.newDocumentBuilder ( );
            doc = docBuilder.newDocument ( );
            rootElement = doc.createElement ( "xsd:schema" );
            rootElement.setAttribute ( "xmlns:xsd",
                    "http://www.w3.org/2001/XMLSchema" );
            doc.appendChild ( rootElement );
            lastComputationalUnit = null;
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace ( );
        }
        super.openAndCreateFile ( ".xml" );
    }
    
    public void writeXML ( String tag, Object object )
    {
        switch ( tag)
        {
            case "interface":
            {
                if ( object instanceof Interface )
                {
                    writeInterface ( (Interface) object );
                }
                break;
            }
            case "channel":
            {
                if ( object instanceof Channel )
                {
                    writeChannels( (Channel) object );
                }
                break;
            }
            case "struct":
            {
                if ( object instanceof Struct )
                {
                    writeStruct( (Struct) object );
                }
                break;
            }
            case "component":
            {
                break;
            }
        }
    }
    
    // <interface>
    // <attribute name="IPrintOutput"/>
    // <channel direction="in" name="input" type="sensorReading"/>
    // </interface>
    private void writeInterface ( final Interface interfs )
    {
        Element element = doc.createElement ( "interface" );
        rootElement.appendChild ( element );
        lastComputationalUnit = element;
        setNameAttribute ( interfs.getName ( ), element );
    }
    
    private void writeChannels ( Channel channel )
    {
        
        Element element = doc.createElement ( "channel" );
        this.lastComputationalUnit.appendChild ( element );
        
        Attr attr = doc.createAttribute ( "direction" );
        attr.setValue ( channel.getDirection ( ) );
        element.setAttributeNode ( attr );
        
        attr = doc.createAttribute ( "name" );
        attr.setValue ( channel.getName ( ) );
        element.setAttributeNode ( attr );
        
        attr = doc.createAttribute ( "type" );
        attr.setValue ( channel.getType ( ) );
        element.setAttributeNode ( attr );
        
    }
    
    private void writeStruct ( final Struct struct )
    {
        Element element = doc.createElement ( "struct" );
        rootElement.appendChild ( element );
        lastComputationalUnit = element;
        setNameAttribute ( struct.getName ( ), element );
    }
    
    private void setNameAttribute ( final String name, Element parentElement )
    {
        Element element = doc.createElement ( "attribute" );
        parentElement.appendChild ( element );
        element.setAttribute ( "name", name );
    }
    
    public void writeXMLToFile ( )
    {
        transformerFactory = TransformerFactory.newInstance ( );
        try
        {
            transformer = transformerFactory.newTransformer ( );
        }
        catch (TransformerConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace ( );
        }
        source = new DOMSource ( doc );
        StreamResult result = new StreamResult ( new File (
                super.getPathToTheNewCreatedFile ( ) ) );
        
        try
        {
            transformer.transform ( source, result );
        }
        catch (TransformerException e)
        {
            e.printStackTrace ( );
        }
    }
}
