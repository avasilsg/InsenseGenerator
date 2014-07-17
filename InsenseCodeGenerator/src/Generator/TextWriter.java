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
        // TODO Auto-generated method stub
        
    }
    
}
