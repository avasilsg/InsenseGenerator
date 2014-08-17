package ReverseEngineeringGenerator;

import java.io.File;

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

import Units.Component;
import Units.Interface;
import Units.Struct;
import Units.BasicUnits.Channel;
import Units.BasicUnits.Field;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;
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
    private Element                lastSubElement;
    
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
            case "field":
            {
                if ( object instanceof Field )
                {
                    writeField((Field)object);
                }
                break;
            }
            case "component":
            {
                if ( object instanceof Component )
                {
                    writeComponent((Component)object);
                }
                break;
            }
            case "behaviour":
            {
                writeBehaviour();
                break;
            }
            case "send":
            {
                if ( object instanceof Send )
                {
                    writeSend((Send)object);
                }
                break;
            }
            case "receive":
            {
                if ( object instanceof Receive )
                {
                    writeReceive((Receive)object);
                }
                break;
            }
        }
    }
//    <send identifier="reading" on="printChan"/>
    private void writeSend ( Send send )
    {
        Element element = doc.createElement ( "send" );
        this.lastSubElement.appendChild ( element );
        
        Attr attr = doc.createAttribute ( "on" );
        attr.setValue ( send.getOn ( ));
        element.setAttributeNode ( attr );
        
        attr = doc.createAttribute ( "identifier" );
        attr.setValue ( send.getInderntifier ( ) );
        element.setAttributeNode ( attr );        
    }
//    <receive from="input" identifier="reading"/>
    private void writeReceive ( Receive receive )
    {
        Element element = doc.createElement ( "receive" );
        this.lastSubElement.appendChild ( element );
                
        Attr attr = doc.createAttribute ( "identifier" );
        attr.setValue ( receive.getInderntifier ( ) );
        element.setAttributeNode ( attr );
        
        attr = doc.createAttribute ( "from" );
        attr.setValue ( receive.getFrom ( ));
        element.setAttributeNode ( attr );
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
    
    private void writeComponent ( final Component component )
    {
        Element element = doc.createElement ( "component" );
        rootElement.appendChild ( element );
        lastComputationalUnit = element;
        setNameAttribute ( component.getName ( ), element );
        for (int i = 0; i < component.getPresents ( ).size ( ); i++)
        {
            Element elAttr = doc.createElement ( "presents" );
            element.appendChild ( elAttr );
            elAttr.setAttribute ( "name", component.getPresents ( ).get ( i ) );
        }
    }
    private void writeBehaviour ()
    {
        Element element = doc.createElement ( "behaviour" );
        this.lastSubElement = element;
        this.lastComputationalUnit.appendChild ( element );
        
    }
//    <field  type = "integer" name = "solar"/>

    private void writeField ( Field field )
    {
        Element element = doc.createElement ( "field" );
        this.lastComputationalUnit.appendChild ( element );
        Attr attr = doc.createAttribute ( "type" );

        if (null != field.getType ( ))
        {
            attr.setValue ( field.getType() );
            element.setAttributeNode ( attr );
        }
        
        if (null != field.getName ( ))
        {
            attr = doc.createAttribute ( "name" );
            attr.setValue ( field.getName ( ) );
            element.setAttributeNode ( attr );
        }
        if (null != field.getValue ( ))
        {
            attr = doc.createAttribute ( "value" );
            attr.setValue ( field.getValue ( ) );
            element.setAttributeNode ( attr );
        }
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
