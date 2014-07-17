package Generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import GrammarAndClauses.Clauses;
import Units.Component;
import Units.Interface;

public class TextWriter
{
    private  PrintWriter writer;
    private  LinkedList<String> componentNames;
    
    public TextWriter()
    {
        writer = null;
        componentNames = new LinkedList<String>();
    }
    
    public void openAndCreateFile() throws FileNotFoundException, UnsupportedEncodingException
    {
        writer = new PrintWriter("/Temp/test.txt", "UTF-8");
    }
    
    public void writeInterface(Interface interfs)
    {      
        writer.println();
        writer.write(String.format(Clauses.interfaceGrammar, interfs.getName()));
        writer.println();
        writer.write(Clauses.openBracket);
        writer.println();
        for(int i = 0; i < interfs.getChannels().size(); i++)
        {
            writer.write("\t" + String.format(Clauses.channel, interfs.getChannels().get(i).getDirection(), interfs.getChannels().get(i).getType(), interfs.getChannels().get(i).getName()));
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

        int count = 0;
        if (null != component.getName())
        {
            this.componentNames.add(component.getName());
        }
        string compTitle = String.Format(Grammar.component, component.name);
        foreach (String str in component.presentedInterfaces)
        {
            if (0 < count)
            {
                compTitle = compTitle + Grammar.comma;
            }
            compTitle = compTitle + str;
            ++count;     
        }
        writer.Write(compTitle);
        writer.Write(Environment.NewLine);
        writer.Write(Grammar.openCurlyBracket);
        writer.Write(Environment.NewLine);
        if (0 < component.fields.Count())
        {
            foreach (String str in component.fields)
            {
                writer.Write("\t" + str);
                writer.Write(Environment.NewLine);
            }
        }
        writer.Write(Environment.NewLine);
        writer.Write("\t" + Grammar.constructor);
        writer.Write(Grammar.openBracket);
        writer.Write(Grammar.closeBracket);
        writer.Write(Environment.NewLine);
        writer.Write("\t" + Grammar.openCurlyBracket);
        writer.Write(Environment.NewLine);
        writer.Write("\t" + Grammar.closeCurlyBracket);
        writer.Write(Environment.NewLine);
        writer.Write("\t" + Grammar.behaviour);
        writer.Write(Grammar.openCurlyBracket);
        foreach (String str in component.behaviourContent)
        {
            String[] array = str.Split(',');
            if (true == "send".Equals(array[0]))
            {
                writer.Write(Environment.NewLine);
                writer.Write("\t\t" + String.Format(Grammar.send, array[1], array[2]));
            }
            
            if (true == "receive".Equals(array[0]))
            {
                writer.Write(Environment.NewLine);
                writer.Write("\t\t" + String.Format(Grammar.receive, array[1], array[2]));
            }

            if (true == "print".Equals(array[0]))
            {
                writer.Write(Environment.NewLine);
                if (3 == array.Length)
                {
                   // writer.Write("\t" 
                }
            }

            if (true == "variable".Equals(array[0]))
            {
                writer.Write(Environment.NewLine);
                foreach (String field in component.fields)
                {
                    string[] fields = field.Split(',');
                    if (2 < array.Length)
                    {
                        continue;
                    }
                    writer.Write(Environment.NewLine);
                    writer.Write("\t" + String.Format(Grammar.field, field[0], field[1]));
                }
            }
        }
        writer.Write(Environment.NewLine);
        writer.Write("\t"+Grammar.closeCurlyBracket);
        writer.Write(Environment.NewLine);
        writer.Write(Grammar.closeCurlyBracket);      
    }
    
}
