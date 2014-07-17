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
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
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
        writer = new PrintWriter("/Temp/test3.txt", "UTF-8");
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
            }
        }
        
        writer.println();
        writer.write("\t" + Clauses.closeCurlyBracket);
        writer.println();
        writer.write(Clauses.closeCurlyBracket);
    }
    
    private void writeFields(LinkedList<Field> fields)
    {
        if (1 < fields.size())
        {
            for (int i = 0; i < fields.size(); i++)
            {
                writer.println();
                Field field = fields.get(i);
                if (null == field.getType() || "".equals(field.getType()))
                {
                    writer.write(String.format(Clauses.fieldTypeLess, field.getName(), field.getValue()));
                }
                else
                {
                    writer.write(String.format(Clauses.fieldFull, field.getType(), field.getName(), field.getValue()));
                }
            }
        }
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
}
