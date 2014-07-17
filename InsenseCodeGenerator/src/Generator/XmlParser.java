package Generator;

import javax.management.openmbean.OpenDataException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import Units.Behaviour;
import Units.Component;
import Units.Connect;
import Units.Interface;
import Units.Struct;
import Units.BasicUnits.Channel;
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
import Units.BasicUnits.Print;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class XmlParser
{
    private String          fileName;
    private File            file;
    private DocumentBuilder dBuilder;
    private Document        doc;
    private TextWriter      codeGenerator;
    
    public XmlParser(final String fXmlFile)
    {
        if (null == fXmlFile)
        {
            throw new NullPointerException();
        }
        codeGenerator = new TextWriter();
        try
        {
            codeGenerator.openAndCreateFile();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setFileName(fXmlFile);
        file = null;
    }
    
    private void openFile() throws OpenDataException
    {
        try
        {
            file = new File(fileName);
            dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (Exception e)
        {
            throw new OpenDataException("The file cannot be open");
        }
        try
        {
            doc = dBuilder.parse(file);
        }
        catch (SAXException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public void parseXML()
    {
        try
        {
            openFile();
        }
        catch (OpenDataException e)
        {
            e.printStackTrace();
        }
        
        if (doc.hasChildNodes())
        {
            printNote(doc.getChildNodes());
            NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
            
            for (int count = 0; count < nodeList.getLength(); count++)
            {
                if (nodeList.item(count).getNodeType() == Node.ELEMENT_NODE)
                {
                    Node currentNode = nodeList.item(count);
                    String currentTag = currentNode.getNodeName();
                    switch (currentTag)
                    {
                        case "interface":
                        {
                            parseInterface(currentNode);
                            break;
                        }
                        case "struct":
                        {
                            parseStruct(currentNode);
                            break;
                        }
                        case "component":
                        {
                            parseComponent(currentNode);
                            break;
                        }
                        case "instance":
                        {
                            instanceParsing(currentNode);
                            break;
                        }
                        case "connect":
                        {
                            connectionParsing(currentNode);
                            break;
                        }
                    }
                }
            }
        }
        codeGenerator.closeFile();
    }
    
    // <instance component="TempReader" name="tr" />
    private void instanceParsing(Node currentNode)
    {
        Instance instance = new Instance();
        for (int i = 0; i < currentNode.getAttributes().getLength(); i++)
        {
            if ("component".equals(currentNode.getAttributes().item(i).getNodeName()))
            {
                if (null != currentNode.getAttributes().item(i).getNodeValue())
                {
                    instance.setType(currentNode.getAttributes().item(i).getNodeValue());
                }
            }
            if ("name".equals(currentNode.getAttributes().item(i).getNodeName()))
            {
                if (null != currentNode.getAttributes().item(i).getNodeValue())
                {
                    instance.setName(currentNode.getAttributes().item(i).getNodeValue());
                }
            }
        }
        // TODO: params
        codeGenerator.writeInstance(instance);
        instance = null;
    }
    
    private void connectionParsing(Node currentNode)
    {
        if (true == currentNode.hasChildNodes())
        {
            parseConnection(currentNode.getChildNodes());
        }
    }
    
    // <from name = "TempReader" on = "tRequestChan"/>
    // <to name = "lightHumidTempSensor" on = "tempRequest"/>
    private void parseConnection(NodeList childNodes)
    {
        Connect connect = new Connect();
        boolean flagOpen = false;
        boolean flagClosed = false;
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("from".equals(node.getNodeName().toLowerCase()))
            {
                for (int i = 0; i < node.getAttributes().getLength(); i++)
                {
                    if ("name".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        connect.setFromName(node.getAttributes().item(i).getNodeValue());
                    }
                    if ("on".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        connect.setFromNameOn(node.getAttributes().item(i).getNodeValue());
                    }
                }
                flagOpen = true;
            }
            
            if ("to".equals(node.getNodeName().toLowerCase()))
            {
                for (int i = 0; i < node.getAttributes().getLength(); i++)
                {
                    if ("name".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        connect.setToName(node.getAttributes().item(i).getNodeValue());
                    }
                    if ("on".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        connect.setToNameOn(node.getAttributes().item(i).getNodeValue());
                    }
                }
                flagClosed = true;
            }
            if (flagOpen && flagClosed)
            {
                codeGenerator.writeConnection(connect);
                flagClosed = flagOpen = false;
            }
            
        }
    }
    
    private void parseComponent(Node currentNode)
    {
        if (true == currentNode.hasChildNodes())
        {
            Component component = parseComponentContent(currentNode.getChildNodes());
            codeGenerator.writeComponent(component);
            component = null;
        }
    }
    
    private Component parseComponentContent(NodeList childNodes)
    {
        Component component = new Component();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("attribute".equals(node.getNodeName().toLowerCase()))
            {
                if (null != node.getAttributes())
                {
                    for (int i = 0; i < node.getAttributes().getLength(); i++)
                    {
                        if ("name".equals(node.getAttributes().item(i).getNodeName().toLowerCase()))
                        {
                            component.setName(node.getAttributes().item(i).getNodeValue());
                        }
                    }
                }
            }
            
            if ("presents".equals(node.getNodeName().toLowerCase()))
            {
                if (null != node.getAttributes())
                {
                    for (int i = 0; i < node.getAttributes().getLength(); i++)
                    {
                        if ("name".equals(node.getAttributes().item(i).getNodeName()))
                        {
                            component.setPresent(node.getAttributes().item(i).getNodeValue());
                        }
                    }
                }
            }
            // <field type = "" name = "avgTemp" value ="0.0"/>
            if ("field".equals(node.getNodeName().toLowerCase()))
            {
                Field field = parseField(node);
                component.setField(field);
            }
            
            if ("constructor".equals(node.getNodeName().toLowerCase()))
            {
                
            }
            
            if ("behaviour".equals(node.getNodeName().toLowerCase()))
            {
                Behaviour behaviour = parseBehaviour(node.getChildNodes());
                component.setBehaviour(behaviour);
                behaviour = null;
            }
        }
        return component;
    }
    
    private Field parseField(Node node)
    {
        Field field = new Field();
        if (null != node.getAttributes())
        {
            for (int i = 0; i < node.getAttributes().getLength(); i++)
            {
                if ("type".equals(node.getAttributes().item(i).getNodeName()))
                {
                    field.setType(node.getAttributes().item(i).getNodeValue());
                }
                if ("name".equals(node.getAttributes().item(i).getNodeName()))
                {
                    field.setName(node.getAttributes().item(i).getNodeValue());
                }
                if ("value".equals(node.getAttributes().item(i).getNodeName()))
                {
                    field.setValue(node.getAttributes().item(i).getNodeValue());
                }
            }
        }
        return field;
    }
    
    private Behaviour parseBehaviour(NodeList childNodes)
    {
        Behaviour behaviour = new Behaviour();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("send".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Send send = parseAttributesSend(node.getAttributes());
                behaviour.setSend(send);
                send = null;
            }
            
            if ("receive".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Receive receive = parseAttributesReceive(node.getAttributes());
                behaviour.setReceive(receive);
                receive = null;
            }
            
            if ("print".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Print print = parsePrintAttributes(node.getAttributes());
                behaviour.setPrint(print);
                print = null;
                // parsePrintAttributes(child.Attributes, child.Name,
                // behaviour);
            }
            if ("variable".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                // parseComponentLocalVariableAttributes(child.Attributes,
                // component.variables);
            }
        }
        return behaviour;
    }
    
    // <print variable = "cycle" type = "Int" titleString =""/>
    // <print variable = "reading" type = "Real" attribute = "photo" titleString
    // = "Photo"/>
    private Print parsePrintAttributes(NamedNodeMap attributes)
    {
        Print print = new Print();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            if ("variable".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                print.setVariable(attributes.item(i).getNodeValue());
            }
            if ("type".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                print.setType(attributes.item(i).getNodeValue());
            }
            if ("attribute".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                print.setAttribute(attributes.item(i).getNodeValue());
            }
            if ("titleString".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                print.setTitle(attributes.item(i).getNodeValue());
            }
        }
        return print;
    }
    
    private Receive parseAttributesReceive(NamedNodeMap attributes)
    {
        Receive receive = new Receive();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            if ("identifier".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                receive.setInderntifier(attributes.item(i).getNodeValue());
            }
            if ("value".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                // receive.(attributes.item(i).getNodeValue());
            }
            if ("from".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                receive.setFrom(attributes.item(i).getNodeValue());
            }
        }
        return receive;
    }
    
    private Send parseAttributesSend(NamedNodeMap attributes)
    {
        Send send = new Send();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            if ("identifier".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                send.setInderntifier(attributes.item(i).getNodeValue());
            }
            if ("value".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                send.setValue(attributes.item(i).getNodeValue());
            }
            if ("on".equals(attributes.item(i).getNodeName().toLowerCase()))
            {
                send.setOn(attributes.item(i).getNodeValue());
            }
        }
        return send;
    }
    
    private void parseStruct(Node currentNode)
    {
        if (true == currentNode.hasChildNodes())
        {
            Struct struct = parseStructAttributes(currentNode.getChildNodes());
            codeGenerator.writeStruct(struct);
            struct = null;
        }
    }
    
    private Struct parseStructAttributes(NodeList childNodes)
    {
        Struct struct = new Struct();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("attribute".equals(node.getNodeName().toLowerCase()))
            {
                if (null != node.getAttributes())
                {
                    for (int i = 0; i < node.getAttributes().getLength(); i++)
                    {
                        if ("name".equals(node.getAttributes().item(i).getNodeName()))
                        {
                            struct.setName(node.getAttributes().item(i).getNodeValue());
                        }
                    }
                }
            }
            if ("field".equals(node.getNodeName().toLowerCase()))
            {
                Field field = parseField(node);
                struct.setField(field);
            }
        }
        return struct;
    }
    
    // region Interface
    private void parseInterface(Node currentNode)
    {
        if (null == currentNode)
        {
            throw new NullPointerException();
        }
        
        if (true == currentNode.hasChildNodes())
        {
            Interface interfaceObj = parseInterfaceContent(currentNode.getChildNodes());
            codeGenerator.writeInterface(interfaceObj);
            interfaceObj = null;
        }
    }
    
    private Interface parseInterfaceContent(NodeList childNodes)
    {
        Interface interfaceObj = new Interface();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("attribute".equals(node.getNodeName().toLowerCase()))
            {
                if (null != node.getAttributes())
                {
                    for (int i = 0; i < node.getAttributes().getLength(); i++)
                    {
                        if ("name".equals(node.getAttributes().item(i).getNodeName()))
                        {
                            interfaceObj.setName(node.getAttributes().item(i).getNodeValue());
                        }
                    }
                }
            }
            if ("channel".equals(node.getNodeName().toLowerCase()))
            {
                Channel chan = new Channel();
                for (int i = 0; i < node.getAttributes().getLength(); i++)
                {
                    if ("name".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        chan.setName(node.getAttributes().item(i).getNodeValue());
                    }
                    if ("type".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        chan.setType(node.getAttributes().item(i).getNodeValue());
                    }
                    if ("direction".equals(node.getAttributes().item(i).getNodeName()))
                    {
                        chan.setDirection(node.getAttributes().item(i).getNodeValue());
                    }
                }
                interfaceObj.setChannel(chan);
            }
        }
        
        return interfaceObj;
    }
    
    // #endregion
    private static void printNote(NodeList nodeList)
    {
        
        for (int count = 0; count < nodeList.getLength(); count++)
        {
            
            Node tempNode = nodeList.item(count);
            
            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE)
            {
                
                // get node name and value
                System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
                System.out.println("Node Value =" + tempNode.getTextContent());
                
                if (tempNode.hasAttributes())
                {
                    
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    
                    for (int i = 0; i < nodeMap.getLength(); i++)
                    {
                        
                        Node node = nodeMap.item(i);
                        System.out.println("attr name : " + node.getNodeName());
                        System.out.println("attr value : " + node.getNodeValue());
                        
                    }
                    
                }
                
                if (tempNode.hasChildNodes())
                {
                    
                    // loop again if has child nodes
                    printNote(tempNode.getChildNodes());
                    
                }
                
                System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
                
            }
            
        }
        
    }
}
