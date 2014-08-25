package InsenseGenerator;

import java.io.File;
import java.rmi.UnexpectedException;
import java.util.LinkedList;

import AbstractGenerator.Writer;
import GrammarAndClauses.Clauses;
import Units.Behaviour;
import Units.Body;
import Units.Component;
import Units.Connect;
import Units.Constructor;
import Units.InsenseNode;
import Units.Interface;
import Units.Procedure;
import Units.Struct;
import Units.Template;
import Units.BasicUnits.Channel;
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
import Units.BasicUnits.Operator;
import Units.BasicUnits.Print;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;
import Units.BasicUnits.Variable;

public class InsenseWriter extends Writer
{
    private LinkedList<String> componentNames;
    
    public InsenseWriter()
    {
        super();
        componentNames = new LinkedList<String>();
    }
    
    public void writeInterface(Interface interfs)
    {
        writer.println();
        writer.write(String.format(Clauses.interfaceGrammar, interfs.getName()));
        writer.println();
        writer.write(Clauses.openBracket);
        writer.println();
        for (int i = 0; i < interfs.getChannels().size(); i++)
        {
            if (false == interfs.getChannels().get(i).isArray())
            {
                writer.write("\t"
                        + String.format(Clauses.channel, interfs.getChannels().get(i).getDirection(), interfs.getChannels().get(i).getType(), interfs
                                .getChannels().get(i).getName()));
            }
            else
            {
                writer.write("\t"
                        + String.format(Clauses.arrayChannel, interfs.getChannels().get(i).getDirection(), interfs.getChannels().get(i).getType(), interfs
                                .getChannels().get(i).getName()));
            }
            writer.println();
        }
        writer.write(Clauses.closeBracket);
    }
    
    public void closeFile()
    {
        this.writer.close();
    }
    
    public LinkedList<String> getComponentNames()
    {
        return componentNames;
    }
    
    public void setComponentNames(LinkedList<String> componentNames)
    {
        this.componentNames = componentNames;
    }
    
    public void writeComponent(Component component)
    {
        if (null == component)
        {
            throw new NullPointerException();
        }
        writer.println();
        if (null != component.getName())
        {
            this.componentNames.add(component.getName());
        }
        String compTitle = String.format(Clauses.component, component.getName());
        compTitle = writeComponentPresents(component, compTitle);
        writer.write(compTitle);
        writer.println();
        writer.write(Clauses.openCurlyBracket);
        writer.println();
        writeFields(component.getFields());
        writer.println();
        writeContructor(component.getConstructors());
        writeProcedures(component.getProcedures());
        writer.println();
        writer.write("\t" + Clauses.behaviour);
        writer.println();
        writer.write("\t" + Clauses.openCurlyBracket);
        Behaviour behaviour = component.getBehaviour();
        if (null != behaviour)
        {
            writeBehaviour(behaviour);
        }
        writer.println();
        writer.write("\t" + Clauses.closeCurlyBracket);
        writer.println();
        writer.write(Clauses.closeCurlyBracket);
    }
    
    private String writeComponentPresents(Component component, String compTitle)
    {
        for (int i = 0; i < component.getPresents().size(); i++)
        {
            if (1 < component.getPresents().size())
            {
                compTitle = compTitle + Clauses.comma;
            }
            compTitle = compTitle + component.getPresents().get(i);
        }
        return compTitle;
    }
    
    private void writeContructor(LinkedList<Constructor> constructors)
    {
        for (int i = 0; i < constructors.size(); i++)
        {
            Constructor constructor = constructors.get(i);
            writer.write("\t" + Clauses.constructor);
            writer.write(Clauses.openBracket);
            if (null != constructor.getParameters())
            {
                extractParameters(constructor.getParameters());
            }
            writer.write(Clauses.closeBracket);
            writer.println();
            writer.write("\t" + Clauses.openCurlyBracket);
            writer.println();
            writeBody(constructor);
            writer.write("\t" + Clauses.closeCurlyBracket);
            writer.println();
        }
        constructors = null;
    }
    
    private void writeBehaviour(Behaviour behaviour)
    {
        writeBody(behaviour);
        
        if (!behaviour.isTemplateEmpty())
        {
            writeTemplate(behaviour.getTemplate());
        }
    }

    private void writeBody(Body body)
    {
        while (!body.isOperationsEmpty())
        {
            Operator operator;
            try
            {
                operator = body.getCurrentOperator();
                if (operator instanceof Send)
                {
                    writeSend((Send) operator);
                }
                
                if (operator instanceof Receive)
                {
                    writeReceive((Receive) operator);
                }
                
                if (operator instanceof Print)
                {
                    writePrint((Print) operator);
                }
                
                if (operator instanceof Variable)
                {
                    writeVariable((Variable) operator);
                }
            }
            catch (UnexpectedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    private void writeTemplate(Template template)
    {
        writer.println();
        writer.write("\t\t" + template.getExpression() + "\n");
        writer.write("\t\t" + template.getType() + ": {");
        Operator operator = template.getOperator();
        if (operator instanceof Send)
        {
            Send send = (Send) operator;
            writer.write(" " + String.format(Clauses.send, send.getInderntifier(), send.getOn()) + "  }");
            
        }
        else
        {
            Receive receive = (Receive) operator;
            writer.write("\t\t" + String.format(Clauses.receive, receive.getInderntifier(), receive.getFrom()));
        }
        writer.println();
        String expression = Clauses.openBracket + template.getPrint().getVariable() + Clauses.closeBracket;
        writer.write("\t\tdefault: {  " + String.format(Clauses.printString, expression) + "  }");
    }
    
    private void writeVariable(Variable variable)
    {
        if (null != variable.getName())
        {
            writer.println();
            if (true == variable.isNewOp())
            {
                writer.write("\t\t" + String.format(Clauses.variableNew, variable.getName(), variable.getBindingTo(), ""));
            }
            else
            {
                writer.write("\t\t" + String.format(Clauses.variableNotNew, variable.getName(), variable.getBindingTo(), ""));
            }
            writer.println();
        }
    }
    
    private void writeProcedures(LinkedList<Procedure> procedures)
    {
        for (int i = 0; i < procedures.size(); i++)
        {
            writer.println();
            Procedure procedure = procedures.get(i);
            writer.write("\t" + String.format(Clauses.procedureSyntax, procedure.getName()));
            extractParameters(procedure.getParameters());
            writer.write(String.format(Clauses.closeProcedureDeclaration, procedure.getType()));
            writer.println();
            writer.write("\t" + Clauses.openCurlyBracket);
            writer.println();
            writeBody(procedure);
            writer.println();
            writer.write("\t" + Clauses.closeCurlyBracket);
        }
    }
    
    private void extractParameters(LinkedList<Field> params)
    {
        for (int j = 0; j < params.size(); j++)
        {
            writeField(params.get(j));
        }
    }
    
    private void writeFields(LinkedList<Field> fields)
    {
        for (int i = 0; i < fields.size(); i++)
        {
            writer.println();
            Field field = fields.get(i);
            writeField(field);
        }
    }
    
    private void writeField(Field field)
    {
        boolean flagType = (null == field.getType() || "".equals(field.getType()));
        boolean flagValue = (null == field.getValue() || "".equals(field.getValue()));
        boolean flagName = (null == field.getName() || "".equals(field.getName()));
        
        if (true == field.getIsArray())
        {
            if (flagType && !flagName)
            {
                writer.write("\t" + String.format(Clauses.fieldTypeLess, field.getName()));
            }
        }
        else
        {
            if (flagType && !flagName && !flagValue)
            {
                writer.write("\t" + String.format(Clauses.fieldTypeLess, field.getName(), field.getValue()));
            }
            else if (flagValue && !flagType && !flagName)
            {
                writer.write("\t" + String.format(Clauses.fieldEmpty, field.getType(), field.getName()));
            }
            else if (!flagValue && !flagType && !flagName)
            {
                writer.write("\t" + String.format(Clauses.fieldFull, field.getType(), field.getName(), field.getValue()));
            }
        }
    }
    
    private void writeReceive(Receive receive)
    {
        writer.println();
        writer.write("\t\t" + String.format(Clauses.receive, receive.getInderntifier(), receive.getFrom()));
    }
    
    private void writeSend(Send send)
    {
        if ((null == send.getValue()) && (false == "any".equals(send.getInderntifier())))
        {
            writer.println();
            writer.write("\t\t" + String.format(Clauses.send, send.getInderntifier(), send.getOn()));
        }
        else
        {
            writer.println();
            writer.write("\t\t" + String.format(Clauses.sendWithValue, send.getInderntifier(), send.getValue(), send.getOn()));
        }
    }
    
    private void writePrint(Print print)
    {
        boolean flagAttribute = false;
        writer.println();
        if (null != print.getAttribute() && !"".equals(print.getAttribute()))
        {
            flagAttribute = true;
        }
        writer.println();
        String expression = null;
        if (flagAttribute)
        {
            expression = Clauses.openBracket + print.getVariable() + "." + print.getAttribute() + Clauses.closeBracket;
        }
        else
        {
            expression = Clauses.openBracket + print.getVariable() + Clauses.closeBracket;
        }
        switch (print.getType().toLowerCase())
        {
            case "string":
            {
                writer.write("\t\t" + String.format(Clauses.printString, expression));
                break;
            }
            case "real":
            {
                writer.write("\t\t" + String.format(Clauses.printReal, expression));
                break;
            }
            case "integer":
            {
                writer.write("\t\t" + String.format(Clauses.printInt, expression));
                break;
            }
            case "unsignedint":
            {
                writer.write("\t\t" + String.format(Clauses.printUnsignedInt, expression));
                break;
            }
        }
    }
    
    public void writeInstance(Instance instance)
    {
        writer.println();
        writer.write(String.format(Clauses.createInstance, instance.getName(), instance.getType()));
        writer.write(Clauses.openBracket);
        writer.write(Clauses.closeBracket);
    }
    
    public void writeConnection(Connect connect)
    {
        writer.println();
        
        if ((null == connect.getFromName() && null == connect.getToName()) || ("".equals(connect.getToName()) && "".equals(connect.getFromName())))
        {
            writer.write(String.format(Clauses.connectShort, connect.getFromNameOn(), connect.getToNameOn()));
        }
        else if (null == connect.getFromName() || "".equals(connect.getFromName()))
        {
            writer.write(String.format(Clauses.connectExTo, connect.getFromNameOn(), connect.getToName(), connect.getToNameOn()));
        }
        else if (null == connect.getToName() || "".equals(connect.getToName()))
        {
            writer.write(String.format(Clauses.connectExFrom, connect.getFromName(), connect.getFromNameOn(), connect.getToNameOn()));
        }
        else
        {
            writer.write(String.format(Clauses.connectFull, connect.getFromName(), connect.getFromNameOn(), connect.getToName(), connect.getToNameOn()));
        }
    }
    
    public void writeStruct(Struct struct)
    {
        writer.println();
        writer.write(String.format(Clauses.structSyntax, struct.getName()));
        writer.write(Clauses.openBracket);
        writeFields(struct.getFields());
        writer.println();
        writer.write(Clauses.closeBracket);
        writer.println();
    }
    
    public void deleteDefaultFolder()
    {
        File tempFile = new File(dir.getAbsolutePath());
        tempFile.delete();
    }
    
    public void writeNodePattern(InsenseNode nodeInfo)
    {
        Interface interfs = new Interface();
        Component component = new Component();
        Connect connect = new Connect();
        
        switch (nodeInfo.getDirection())
        {
            case "receive":
            {
                String interfaceRadioName = String.format(Clauses.IRadioSensorReceive, Integer.toString(nodeInfo.getNodeOrderNumber()));
                interfs.setName(interfaceRadioName);
                fulfilNodeChannelReceive(nodeInfo, interfs);
                component = new Component();
                component.setName(String.format(Clauses.RadioSensorReceive, Integer.toString(nodeInfo.getNodeOrderNumber())));
                component.setPresent(interfs.getName());
                component.setConstructor(new Constructor());
                Behaviour beh = new Behaviour();
                Receive receive = new Receive();
                receive.setFrom("received");
                receive.setInderntifier("packet");
                beh.insertOperator(receive);
                Send send = new Send();
                send.setInderntifier("value");
                send.setOn("output");
                Template template = new Template();
                template.setExpression("project packet.payload as value onto");
                template.setType(nodeInfo.getInstance());
                template.setObject(send);
                Print print = new Print();
                print.setType("String");
                print.setVariable("\"unknown type???\"");
                template.setPrint(print);
                beh.setTemplate(template);
                component.setBehaviour(beh);
                connect.setFromNameOn("received");
                connect.setToName("radio");
                connect.setToNameOn("received");
                break;
            }
            case "send":
            {
                String interfaceRadioName = String.format(Clauses.IRadioSensorSend, Integer.toString(nodeInfo.getNodeOrderNumber()));
                interfs.setName(interfaceRadioName);
                fulfilNodeChannelSend(nodeInfo, interfs);
                component.setName(String.format(Clauses.RadioSensorSend, Integer.toString(nodeInfo.getNodeOrderNumber())));
                component.setPresent(interfs.getName());
                component.setConstructor(new Constructor());
                Behaviour beh = new Behaviour();
                Receive receive = new Receive();
                receive.setFrom("input");
                receive.setInderntifier("value");
                beh.insertOperator(receive);
                Variable var = new Variable();
                var.setBindingTo(String.format("RadioPacket(%s, any(value))", nodeInfo.getConnectToNode()));
                var.setNewOp(true);
                var.setName("packet");
                beh.insertOperator(var);
                Send send = new Send();
                send.setInderntifier("packet");
                send.setOn("unicast");
                beh.insertOperator(send);
                component.setBehaviour(beh);
                connect.setFromNameOn("unicast");
                connect.setToName("radio");
                connect.setToNameOn("unicastSend");
                break;
            }
        }
        Instance instance = writeTemplateInfo(nodeInfo, interfs, component);
        connect.setFromName(instance.getName());
        writeConnection(connect);
        if ("send".equals(nodeInfo.getDirection()))
        {
            connect.setFromNameOn(nodeInfo.getConnect().getFromNameOn());
            connect.setFromName(nodeInfo.getConnect().getFromName());
            connect.setToName(instance.getName());
            connect.setToNameOn("input");
            writeConnection(connect);
        }
        else
        {
            connect.setFromNameOn("output");
            connect.setFromName(instance.getName());
            connect.setToName(nodeInfo.getConnect().getToName());
            connect.setToNameOn(nodeInfo.getConnect().getToNameOn());
            writeConnection(connect);
        }
    }
    
    private void writeInstances(InsenseNode nodeInfo)
    {
        for (int i = 0; i < nodeInfo.getInstances().size(); i++)
        {
            this.writeInstance(nodeInfo.getInstances().get(i));
        }
    }
    
    private Instance writeTemplateInfo(InsenseNode nodeInfo, Interface interfs, Component component)
    {
        Instance instance = new Instance();
        writeInterface(interfs);
        writeComponent(component);
        writeInstances(nodeInfo);
        instance.setType(component.getName());
        instance.setName(Character.toLowerCase(component.getName().charAt(0)) + component.getName().substring(1));
        writeInstance(instance);
        return instance;
    }
    
    private void fulfilNodeChannelSend(InsenseNode nodeInfo, Interface interfs)
    {
        Channel chan = new Channel();
        chan.setDirection("in");
        chan.setType(nodeInfo.getInstance());
        chan.setName("input");
        interfs.setChannel(chan);
        chan = new Channel();
        chan.setDirection("out");
        chan.setType("RadioPacket");
        chan.setName("unicast");
        interfs.setChannel(chan);
    }
    
    private void fulfilNodeChannelReceive(InsenseNode nodeInfo, Interface interfs)
    {
        Channel chan = new Channel();
        chan.setDirection("out");
        chan.setType(nodeInfo.getInstance());
        chan.setName("output");
        interfs.setChannel(chan);
        chan = new Channel();
        chan.setDirection("in");
        chan.setType("RadioPacket");
        chan.setName("received");
        interfs.setChannel(chan);
    }
}
