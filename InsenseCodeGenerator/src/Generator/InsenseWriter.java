package Generator;

import java.io.File;
import java.util.LinkedList;

import AbstractGenerator.Writer;
import GrammarAndClauses.Clauses;
import Units.Behaviour;
import Units.Component;
import Units.Connect;
import Units.Constructor;
import Units.Interface;
import Units.Procedure;
import Units.Struct;
import Units.BasicUnits.Field;
import Units.BasicUnits.Instance;
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
        compTitle = writeComponentPresents ( component, compTitle );
        writer.write(compTitle);
        writer.println();
        writer.write(Clauses.openCurlyBracket);
        writer.println();
        writeFields(component.getFields());
        writer.println();
        writeContructor (component.getConstructors ( ) );
        writeProcedures(component.getProcedures());
        writer.println();
        writer.write("\t" + Clauses.behaviour);
        writer.println();
        writer.write("\t" + Clauses.openCurlyBracket);
        Behaviour behaviour = component.getBehaviour();
        if (null != behaviour)
        {
            writeBehaviour ( behaviour );
        }        
        writer.println();
        writer.write("\t" + Clauses.closeCurlyBracket);
        writer.println();
        writer.write(Clauses.closeCurlyBracket);
    }

    private String writeComponentPresents ( Component component,
            String compTitle )
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

    private void writeContructor (LinkedList<Constructor> constructors)
    {
        for(int i = 0; i < constructors.size ( ); i++)
        {
            Constructor constructor = constructors.get ( i );
            writer.write("\t" + Clauses.constructor);
            writer.write(Clauses.openBracket);
            if (null != constructor.getParameters ( ))
            {
                extractParameters(constructor.getParameters ( ));
            }
            writer.write(Clauses.closeBracket);
            writer.println();
            writer.write("\t" + Clauses.openCurlyBracket);
            writer.println();
            if (null != constructor.getBody ( ))
            {
                writeBehaviour(constructor.getBody ( ));
            }
            writer.write("\t" + Clauses.closeCurlyBracket);
            writer.println();
        }
        constructors = null;
    }

    private void writeBehaviour ( Behaviour behaviour )
    {
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
                case "variable":
                {
                    writeVariable(behaviour);
                    break;
                }
                case "return":
                {
                    writer.write("\t\t" + "return " + behaviour.getReturnStatements ( ).get ( 0 ));
                    behaviour.getReturnStatements ( ).remove ( 0 );
                    break;
                }
            }
        }
    }
      
    private void writeVariable(Behaviour behaviour)
    {
        Variable variable = behaviour.getVariables().get(0);
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
            behaviour.getVariables().remove(0);
        }
    }
    
    private void writeProcedures(LinkedList<Procedure> procedures)
    {
        for (int i = 0; i < procedures.size(); i++)
        {
            writer.println();            
            Procedure procedure = procedures.get(i);
            writer.write("\t" + String.format(Clauses.procedureSyntax, procedure.getName()));
            extractParameters(procedure.getParameters ( ));
            writer.write(String.format(Clauses.closeProcedureDeclaration, procedure.getType()));
            writer.println();
            writer.write("\t" + Clauses.openCurlyBracket);
            writer.println();
            writeBehaviour(procedure.getBody ( ));
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
        behaviour.getPrints().remove(0);
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
        File tempFile = new File(dir.getAbsolutePath ( ));
        tempFile.delete();
    }
}
