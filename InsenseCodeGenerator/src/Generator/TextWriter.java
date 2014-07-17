package Generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import GrammarAndClauses.Clauses;
import Units.Behaviour;
import Units.Component;
import Units.Connect;
import Units.Interface;
import Units.Struct;
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
import Units.BasicUnits.Print;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;

public class TextWriter
{
    private PrintWriter        writer;
    private LinkedList<String> componentNames;
    
    public TextWriter()
    {
        writer = null;
        componentNames = new LinkedList<String>();
    }
    
    public void openAndCreateFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        writer = new PrintWriter("/Temp/test4.txt", "UTF-8");
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
            writer.write("\t"
                    + String.format(Clauses.channel, interfs.getChannels().get(i).getDirection(), interfs.getChannels().get(i).getType(), interfs.getChannels()
                            .get(i).getName()));
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
        for (int i = 0; i < component.getPresents().size(); i++)
        {
            if (1 < component.getPresents().size())
            {
                compTitle = compTitle + Clauses.comma;
            }
            compTitle = compTitle + component.getPresents().get(i);
        }
        writer.write(compTitle);
        writer.println();
        writer.write(Clauses.openCurlyBracket);
        writer.println();
        writeFields(component.getFields());
        writer.write("\t" + Clauses.constructor);
        writer.write(Clauses.openBracket);
        // TODO: contructors body
        writer.write(Clauses.closeBracket);
        writer.println();
        writer.write("\t" + Clauses.openCurlyBracket);
        writer.println();
        writer.write("\t" + Clauses.closeCurlyBracket);
        writer.println();
        writer.write("\t" + Clauses.behaviour);
        writer.write(Clauses.openCurlyBracket);
        Behaviour behaviour = component.getBehaviour();
        if (behaviour == null)
        {
            throw new NullPointerException();
        }
        
        for (int i = 0; i < behaviour.getOrder().size(); i++)
        {
            switch (behaviour.getOrder().get(i))
            {
                case "send":
                {
                    writeSend(behaviour);
                    break;
                }
                case "receive":
                {
                    writeReceive(behaviour);
                    break;
                }
                case "print":
                {
                    writePrint(behaviour);
                    break;
                }
            }
        }
        
        writer.println();
        writer.write("\t" + Clauses.closeCurlyBracket);
        writer.println();
        writer.write(Clauses.closeCurlyBracket);
    }
    
    private void writeFields(LinkedList<Field> fields)
    {
        if (1 <= fields.size())
        {
            for (int i = 0; i < fields.size(); i++)
            {
                writer.println();
                Field field = fields.get(i);
                
                boolean flagType = (null == field.getType() || "".equals(field.getType()));
                boolean flagValue = (null == field.getValue() || "".equals(field.getValue()));
                boolean flagName = (null == field.getName() || "".equals(field.getName()));
                
                if (flagType && !flagName && !flagValue)
                {
                    writer.write("\t" + String.format(Clauses.fieldTypeLess, field.getName(), field.getValue()));
                }
                else if (flagValue &&  !flagType && !flagName)
                {
                    writer.write("\t" + String.format(Clauses.fieldEmpty, field.getType(), field.getName()));
                }
                else if (!flagValue &&  !flagType && !flagName)
                {
                    writer.write("\t" + String.format(Clauses.fieldFull, field.getType(), field.getName(), field.getValue()));
                }
            }
        }
        writer.println();
    }
    
    private void writeReceive(Behaviour behaviour)
    {
        Receive receive = behaviour.getReceives().get(0);
        
        writer.println();
        writer.write("\t\t" + String.format(Clauses.receive, receive.getInderntifier(), receive.getFrom()));
        behaviour.getReceives().remove(0);
    }
    
    private void writeSend(Behaviour behaviour)
    {
        Send send = behaviour.getSends().get(0);
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
        behaviour.getSends().remove(0);
    }
    
    private void writePrint(Behaviour behaviour)
    {
        Print print = behaviour.getPrints().get(0);
        boolean flagAttribute = false;
        writer.println();
        if ((null != print.getTitle()) && !"".equals(print.getTitle()))
        {
            writer.write("\t\t" + String.format(Clauses.printString, Clauses.openBracket + "\"" + print.getTitle() + "\"" + Clauses.closeBracket));
        }
        else
        {
            if (null != print.getAttribute() && !"".equals(print.getAttribute()))
            {
                writer.write("\t\t" + String.format(Clauses.printString, Clauses.openBracket + "\"" + print.getAttribute() + "\"" + Clauses.closeBracket));
                flagAttribute = true;
            }
            else
            {
                writer.write("\t\t" + String.format(Clauses.printString, Clauses.openBracket + "\"" + print.getVariable() + "\"" + Clauses.closeBracket));
            }
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
        switch (print.getType())
        {
            case "Real":
            {
                writer.write("\t\t" + String.format(Clauses.printReal, expression));
                break;
            }
            case "Int":
            {
                writer.write("\t\t" + String.format(Clauses.printInt, expression));
                break;
            }
            case "UnsignedInt":
            {
                writer.write("\t\t" + String.format(Clauses.printUnsignedInt, expression));
                break;
            }
        }
        behaviour.getPrints().remove(0);
    }
    
    public void writeInstance(Instance instance)
    {
        writer.println();
        writer.write(String.format(Clauses.createInstance, instance.getType(), instance.getName(), instance.getType()));
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
        writer.write(Clauses.closeBracket);
        writer.println();
    }
}
