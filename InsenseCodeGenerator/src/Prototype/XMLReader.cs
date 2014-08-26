using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace XMLParsing
{
    class XMLReader
    {
        private XmlDocument xmlDoc;
        private CodeGenerator generator;
        private Component component;
        public XMLReader(String filePath, String fileName)
        {
            if ((null != filePath) || (false == "".Equals(filePath)))
            {
                this.xmlDoc = new XmlDocument();
                this.xmlDoc.Load(filePath);
            }
            generator = new CodeGenerator(fileName);
            generator.openAndCreateTheFile();
        }

        public void parseXML()
        {
            if (null != xmlDoc)
            {
                foreach (XmlNode node in xmlDoc.DocumentElement.ChildNodes)
                {
                    string currentTag = node.Name;
                    currentTag.ToLower();

                    switch (currentTag)
                    {
                        case "interface":
                            {
                                Console.WriteLine(node.Name);
                                parseInterface(node);
                            }
                            break;
                        case "struct":
                            {
                                Console.WriteLine(node.Name);
                                parseStruct(node);
                            }
                            break;
                        case "component":
                            {
                                Console.WriteLine(node.Name);
                                parseComponent(node);
                            }
                            break;
                        case "connect":
                            {
                                Console.WriteLine(node.Name);
                                connectionParsing(node);
                            }
                            break;
                    }

                    if (node.HasChildNodes)
                    {
                        foreach (XmlNode childNode in node.ChildNodes)
                        {
                            parseChildNodes(childNode);
                        }
                    }
                }
                generator.closeFile();
            }
        }
        #region ParseInterface
        public void parseInterface(XmlNode node)
        {
            if (null == node)
            {
                throw new NullReferenceException();
            }
            string name = null;
            LinkedList<String> channels = new LinkedList<string>();
            if (true == node.HasChildNodes)
            {
                parseInterfaceContent(node, ref name, channels);
            }
            generator.writeInterface(name, channels);

        }

        public void parseInterfaceContent(XmlNode node, ref String name, LinkedList<String> channels)
        {
            if (node.HasChildNodes)
            {
                foreach (XmlNode child in node.ChildNodes)
                {
                    string chans = null;
                    if ("attribute" == child.Name.ToLower())
                    {
                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if ("name" == att.Name.ToLower())
                            {
                                name = att.Value;
                            }
                        }
                    }
                    if ("channel" == child.Name)
                    {
                        string channelName = null;
                        string type = null;
                        string direction = null;

                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if ("name" == att.Name.ToLower())
                            {
                                channelName = att.Value;
                            }
                            if ("type" == att.Name.ToLower())
                            {
                                type = att.Value;
                            }
                            if ("direction" == att.Name.ToLower())
                            {
                                direction = att.Value;
                            }
                        }
                        chans = direction + "," + type + "," + channelName;
                        channels.AddLast(chans);
                    }
                }
            }
        }
        #endregion
       
        #region  ParseComponent
        private void parseComponent(XmlNode node)
        {
            if (null == node)
            {
                throw new NullReferenceException();
            }
            component = new Component();
            if (true == node.HasChildNodes)
            {
                parseComponentContent(node);
            }
            generator.writeComponent(component);
            component = null;
        }
        
        private void parseComponentContent(XmlNode node)
        {
            if (node.HasChildNodes)
            {
                foreach (XmlNode child in node.ChildNodes)
                {
                    if ("attribute" == child.Name.ToLower())
                    {
                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if ("name" == att.Name.ToLower())
                            {
                                component.name = att.Value;
                            }     
                        }
                    }

                    if ("presents" == child.Name.ToLower())
                    {
                        parsePresents(child, component.presentedInterfaces);
                    }
                    
                    if ("field" == child.Name.ToLower())
                    {
                        parseFields(child, component.fields);
                    }

                    if ("constructor" == child.Name.ToLower())
                    {

                    }

                    if ("behaviour" == child.Name.ToLower())
                    {
                        parseBehavior(child, component.behaviourContent);
                    }
                }
            }
        }
        
        private void parseContructor(XmlNode node, LinkedList<String> fields)
        {
            parseFields(node, fields);
        }

        private void parsePresents(XmlNode node, LinkedList<String> presents)
        {
            foreach (XmlAttribute att in node.Attributes)
            {
                if ("name" == att.Name)
                {
                    presents.AddLast(att.Value);
                }
            }
        }

        private void parseBehavior(XmlNode node, LinkedList<String> behaviour)
        {
            if (true == node.HasChildNodes)
            {
                foreach (XmlNode child in node)
                {
                    if ("send" == child.Name.ToLower())
                    {
                        parseAttributesSendReceive(child.Attributes, child.Name, behaviour);
                    }

                    if ("receive" == child.Name.ToLower())
                    {
                        parseAttributesSendReceive(child.Attributes, child.Name, behaviour);
                    }

                    if ("print" == child.Name.ToLower())
                    {
                        parsePrintAttributes(child.Attributes, child.Name, behaviour);
                    }

                    if ("variable" == child.Name.ToLower())
                    {
                        parseComponentLocalVariableAttributes(child.Attributes, component.variables);
                    }
                }
            }
        }
        //printString("Cycle ")
        //printUnsignedInt(cycle)
        //printString(":\tphoto = ")
        //printInt(reading.photo)

        private void parsePrintAttributes(XmlAttributeCollection attributes, String type, LinkedList<String> behaviour)
        {
            foreach (XmlAttribute att in attributes)
            {
                String content = type;
                if ("variable" == att.Name.ToLower())
                {
                    content = content + "," + att.Value;
                }

                if ("attribute" == att.Name.ToLower())
                {
                    if (false == "".Equals(att.Value))
                    {
                        content = content + "." + att.Value;
                    }
                }

                if ("titleString" == att.Name.ToLower())
                {
                    if (false == "".Equals(att.Value))
                    {
                        content = content + "," + att.Value;
                    }
                }
                behaviour.AddLast(content);
                content = null;
            }
        }
        private void parseAttributesSendReceive(XmlAttributeCollection attributes, String type, LinkedList<String> behaviour)
        {
            String identifier = null;
            String on = null;
            foreach (XmlAttribute att in attributes)
            {

                if ("identifier" == att.Name.ToLower())
                {
                    identifier = att.Value;
                }

                if ("value" == att.Name.ToLower())
                {
                    if (false == att.Value.Equals(""))
                    {
                        identifier = identifier + "(" + att.Value + ")";
                    }
                }

                if ("on" == att.Name.ToLower())
                {
                    on = att.Value;
                    behaviour.AddLast(type + "," + identifier + "," + on);
                }

                if ("from" == att.Name.ToLower())
                {
                    on = att.Value;
                    behaviour.AddLast(type + "," + identifier + "," + on);
                }
            }
        }

//<variable type = "" name = "tempValue " allocation = "static" bindingTo = "tempCelsiusReading()"/>

        private void parseComponentLocalVariableAttributes(XmlAttributeCollection attributes, LinkedList<String> variables)
        {
            String variable = null;
            foreach (XmlAttribute att in attributes)
            {
                if (true == "type".Equals(att.Name.ToLower()))
                {
                    if (false == "".Equals(att.Value))
                    {
                        variable = att.Value;
                    }
                }

                if (true == "name".Equals(att.Name.ToLower()))
                {
                    if (null == variable)
                    {
                        variable = att.Value;
                    }
                    else
                    {
                        variable = variable + " " + att.Value;
                    }
                }

                if (true == "allocation".Equals(att.Name.ToLower()))
                {
                    if ("static".Equals(att.Value.ToLower()))
                    {
                        variable = variable + " = " ;
                    }
                    else if ("dynamic".Equals(att.Value.ToLower()))
                    {
                         variable = variable + " = new" ;
                    }
                }

                if (true == "bindingTo".Equals(att.Name.ToLower()))
                {
                    if (false == "".Equals(att.Value.ToLower()))
                    {
                        variable = variable + att.Value;
                    }
                }
            }

            variables.AddLast(variable);
        }

        #endregion

        #region ConnectionParsing
//        tr = new TempReader()
//        connect tr.tRequestChan to lightHumidTempSensor.tempRequest
//        connect tr.tValueChan to lightHumidTempSensor.tempOutput
//        connect tr.stdoutChan to standardOut
//<connect >
//    <from name = "TempReader" 		 	on = "tRequestChan"/>
//    <to   name = "lightHumidTempSensor" on = "tempRequest"/>
//    <from name = "TempReader"		 	on = "tValueChan" />
//    <to   name = "lightHumidTempSensor" on = "tempOutput"/>
//    <from name = "TempReader" 		 	on = "stdoutChan" />
//    <to   name = "standardOut" 			on = ""/> 
//</connect> 
        public void connectionParsing(XmlNode node)
        {
            if (true == node.HasChildNodes)
            {
                LinkedList<String> connections = new LinkedList<string>();
                string instanceName = null;
                foreach (XmlNode child in node.ChildNodes)
                {
                    if ("from" == child.Name.ToLower())
                    {
                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if (true == "name".Equals(att.Name))
                            {
                                instanceName = att.Value;
                                instanceName = String.Format(Char.ToLowerInvariant(instanceName[0]) + instanceName.Substring(1));
                            }
                            if (true == "on".Equals(att.Name))
                            {
                                if (false == "".Equals(att.Value))
                                {
                                    instanceName = instanceName + "." + att.Value;
                                }
                            }
                        }
                    }

                    if ("to" == child.Name.ToLower())
                    {
                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if (true == "name".Equals(att.Name))
                            {
                                instanceName = instanceName + "," + att.Value;
                            }
                            if (true == "on".Equals(att.Name))
                            {
                                if (false == "".Equals(att.Value))
                                {
                                    instanceName = instanceName + "." + att.Value;
                                }
                            }
                        }
                        connections.AddLast(instanceName);
                        instanceName = null;
                    }
                }
                generator.writeConnections(connections);
            }
        }
        #endregion

        #region ParseStruct
        private void parseStruct(XmlNode node)
        {
            if (null == node)
            {
                throw new NullReferenceException();
            }

            if (true == node.HasChildNodes)
            {
                Structure structs = new Structure(); 
                foreach (XmlNode child in node.ChildNodes)
                {
                    if ("attribute" == child.Name.ToLower())
                    {
                        foreach (XmlAttribute att in child.Attributes)
                        {
                            if ("name" == att.Name.ToLower())
                            {
                                structs.name = att.Value;
                            }
                        }
                    }

                    if ("field" == child.Name.ToLower())
                    {
                        parseFields(child, structs.fields);
                    }
                }
                generator.writeStruct(structs);
                structs = null;
            }
        }
        #endregion

        #region generalParsing
        private void parseFields(XmlNode node, LinkedList<String> fields)
        {
            string field = null;
            foreach (XmlAttribute att in node.Attributes)
            {
                if ("type" == att.Name.ToLower())
                {
                    if (false == "".Equals(att.Value))
                    {
                        field = att.Value;
                    }
                }

                if ("name" == att.Name.ToLower())
                {
                    if (null != field)
                    {
                        field = field + "," + att.Value;
                    }
                    else
                    {
                        field = att.Value;
                    }
                }

                if ("value" == att.Name.ToLower())
                {
                    field = field + " = " + att.Value;
                }
            }
            if (null != field)
            {
                fields.AddLast(field);
                field = null;
            }
        }

        #endregion

        #region Trash
        public void parseChildNodes(XmlNode node)
        {
            if (node.HasChildNodes)
            {
                foreach (XmlNode childNode in node.ChildNodes)
                {
                    parseChildNodes(childNode);
                }
            }
            else
            {
                Console.WriteLine(node.Name);
                getAttributes(node.Attributes);
            }
        }

        private void getAttributes(XmlAttributeCollection attributes)
        {
            if (null != attributes)
            {
                foreach (XmlAttribute att in attributes)
                {
                    Console.WriteLine(att.Name + " " + att.Value);
                }
            }
        }
        #endregion
    }
}
