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
import Units.Procedure;
import Units.Struct;
import Units.BasicUnits.Channel;
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
import Units.BasicUnits.Print;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;
import Units.BasicUnits.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

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
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
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
            e.printStackTrace();
        }
        catch (IOException e)
        {
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
            NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
            
            for (int count = 0; count < nodeList.getLength(); count++)
            {
                if (nodeList.item(count).getNodeType() == Node.ELEMENT_NODE)
                {
                    Node currentNode = nodeList.item(count);
                    parseComputationalUnit(currentNode);
                }
            }
        }
        codeGenerator.closeFile();
        
    }

    private void parseComputationalUnit(Node currentNode)
    {
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
                extractFromAttributes(connect, node);
                flagOpen = true;
                continue;
            }
            
            if ("to".equals(node.getNodeName().toLowerCase()))
            {
                extractToAttributes(connect, node);
                flagClosed = true;
                continue;
            }
            
            if (flagOpen && flagClosed)
            {
                codeGenerator.writeConnection(connect);
                flagClosed = flagOpen = false;
                continue;
            }
        }
    }
    
    private void extractToAttributes(Connect connect, Node node)
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
    }
    
    private void extractFromAttributes(Connect connect, Node node)
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
                component.setName(parseNameAttribute(node));
                continue;
            }
            
            if ("presents".equals(node.getNodeName().toLowerCase()))
            {
                component.setPresent(parseNameAttribute(node));
                continue;
            }
            
            if ("field".equals(node.getNodeName().toLowerCase()))
            {
                Field field = parseField(node);
                component.setField(field);
                continue;
            }
            
            if ("constructor".equals(node.getNodeName().toLowerCase()))
            {
                if (true == node.hasChildNodes())
                    parseProcedure(node.getChildNodes());
                continue;
            }
            
            if ("procedure".equals(node.getNodeName().toLowerCase()))
            {
                Procedure procedure = new Procedure();
                
                if (true == node.hasChildNodes())
                    procedure = parseProcedure(node.getChildNodes());
                
                procedure.setType(parseProcedureAttributes(node));
                component.setProcedure(procedure);
                continue;
            }
            
            if ("behaviour".equals(node.getNodeName().toLowerCase()))
            {
                Behaviour behaviour = parseBehaviour(node.getChildNodes());
                component.setBehaviour(behaviour);
                behaviour = null;
                continue;
            }
        }
        return component;
    }
    
    private String parseProcedureAttributes(Node node)
    {
        String type = null;
        if (null != node.getAttributes())
        {
            for (int i = 0; i < node.getAttributes().getLength(); i++)
            {
                if ("type".equals(node.getAttributes().item(i).getNodeName()))
                {
                    type = node.getAttributes().item(i).getNodeValue();
                }
            }
        }
        return type;
    }

    private Procedure parseProcedure(NodeList childNodes)
    {
        Procedure procedure = new Procedure();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("attribute".equals(node.getNodeName().toLowerCase()))
            {
                if (null == procedure.getName())
                {
                    procedure.setName(parseNameAttribute(node));
                }
                else
                {
                    procedure.setParameters(parseParameters(node));
                }
            }
            if ("body".equals(node.getNodeName().toLowerCase()))
            {
                
            }
            if ("return".equals(node.getNodeName().toLowerCase()))
            {
                procedure.setReturnStatement(parseReturn(node.getChildNodes()));
            }
        }
        return procedure;
    }

    private String parseReturn(NodeList childNodes)
    {
            String value = null;
            for (int i = 0; i < childNodes.getLength(); i++)
            {
                if ("expression".equals(childNodes.item(i).getNodeName()))
                {
                    value = childNodes.item(i).getAttributes().item(0).getNodeValue();
                }
            }
            return value;
    }

    private LinkedList<Field> parseParameters(Node node)
    {
        LinkedList<Field> fields = new LinkedList<Field>();
        if (null != node.getAttributes())
        {
            if ("name".equals(node.getAttributes().item(0).getNodeName().toLowerCase()))
            {
                String valueString = node.getAttributes().item(0).getNodeValue();
                if ("parameters".equals(valueString))
                {
                    parseProcedureParams(node, fields);
                }
            }
        }
        return fields;
    }

    private void parseProcedureParams(Node node, LinkedList<Field> fields)
    {
        for (int i = 0; i < node.getChildNodes().getLength(); i++)
        {
            if ("field".equals(node.getChildNodes().item(i).getNodeName()))
            {
                fields.add(parseField(node.getChildNodes().item(i)));
            }
        }
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
                continue;
            }
            
            if ("receive".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Receive receive = parseAttributesReceive(node.getAttributes());
                behaviour.setReceive(receive);
                receive = null;
                continue;
            }
            
            if ("print".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Print print = parsePrintAttributes(node.getAttributes());
                behaviour.setPrint(print);
                print = null;
                continue;
            }
            if ("variable".equals(node.getNodeName().toLowerCase()))
            {
                behaviour.setOperation(node.getNodeName().toLowerCase());
                Variable variable = parseVariableAttributes(node.getAttributes());
                behaviour.setVariable(variable);
                variable = null;
                continue;
            }
        }
        return behaviour;
    }
    
    private Variable parseVariableAttributes(NamedNodeMap attributes)
    {
        Variable variable = new Variable();
        for (int i = 0; i < attributes.getLength(); i++)
        {
            if ("name".equals(attributes.item(i).getNodeName()))
            {
                variable.setName(attributes.item(i).getNodeValue());
                continue;
            }
            if ("type".equals(attributes.item(i).getNodeName()))
            {
                variable.setType(attributes.item(i).getNodeValue());
                continue;
            }
            if ("bindingTo".equals(attributes.item(i).getNodeName()))
            {
                variable.setBindingTo(attributes.item(i).getNodeValue());
                continue;
            }
            if ("new".equals(attributes.item(i).getNodeName()))
            {
                boolean value = "true".equals(attributes.item(i).getNodeValue().toLowerCase());
                variable.setNewOp(value);
                continue;
            }
        }
        return variable;
    }

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
                struct.setName(parseNameAttribute(node));
            }
            if ("field".equals(node.getNodeName().toLowerCase()))
            {
                Field field = parseField(node);
                struct.setField(field);
            }
        }
        return struct;
    }
    
    private void parseInterface(Node currentNode)
    {
        if (null == currentNode)
        {
            throw new NullPointerException();
        }
        
        if (true == currentNode.hasChildNodes())
        {
            Interface interFace = parseInterfaceContent(currentNode.getChildNodes());
            codeGenerator.writeInterface(interFace);
            interFace = null;
        }
    }
    
    private Interface parseInterfaceContent(NodeList childNodes)
    {
        Interface interFace = new Interface();
        for (int count = 0; count < childNodes.getLength(); count++)
        {
            Node node = childNodes.item(count);
            if ("attribute".equals(node.getNodeName().toLowerCase()))
            {
                interFace.setName(parseNameAttribute(node));
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
                interFace.setChannel(chan);
            }
        }
        
        return interFace;
    }
    
    private String parseNameAttribute(Node node)
    {
        String valueString = null;
        if (null != node.getAttributes())
        {
            for (int i = 0; i < node.getAttributes().getLength(); i++)
            {
                if ("name".equals(node.getAttributes().item(i).getNodeName().toLowerCase()))
                {
                    valueString = node.getAttributes().item(i).getNodeValue();
                    return valueString;
                }
            }
        }
        return valueString;
    }
}
