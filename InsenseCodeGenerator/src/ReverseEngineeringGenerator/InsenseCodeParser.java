package ReverseEngineeringGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import Units.Interface;
import Units.BasicUnits.Channel;
import GrammarAndClauses.Grammer;

public class InsenseCodeParser
{
    private BufferedReader reader;
    private String []      container;
    private XMLWriter      writer;
    
    public InsenseCodeParser ( final String filePath)
    {
        try
        {
            writer = new XMLWriter ( );
            reader = new BufferedReader ( new FileReader ( filePath ) );
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace ( );
        }
    }
    
    public void parseInsense ( )
    {
        String line = "";
        while (line != null)
        {
            try
            {
                line = readLine ( );
                for ( int i = 0; i < container.length; i++ )
                {
                    String element = Grammer.findExpression ( container[i] );
                    if ( !"".equals ( element ) )
                    {
                        parseType ( element );
                    }
                }
            }
            catch (IOException e)
            {
            }
        }
        this.writer.writeXMLToFile ( );
    }
    
    private String readLine ( ) throws IOException
    {
        String line;
        line = reader.readLine ( );
        if ( null != line ) container = line.split ( "\\s+" );
        return line;
    }
    
    private boolean parseType ( String elementType )
    {
        switch ( elementType)
        {
            case "interface":
            {
                Interface interfs = parseInterface ( );
                writer.writeXML ( elementType, interfs );
                return true;
            }
        }
        
        return false;
        
    }
    
    private Interface parseInterface ( )
    {
        // type ITimedTempReader is interface
        Interface interfs = new Interface ( );
        for ( int i = 0; i < this.container.length; i++ )
        {
            if ( container[i].equals ( "type" ) )
            {
                continue;
            }
            else if ( "is".equals ( container[i] ) )
            {
                continue;
            }
            else if ( "interface".equals ( container[i] ) )
            {
                continue;
            }
            else if ( !"".equals ( container[i] ) )
            {
                interfs.setName ( container[i] );
            }
        }
        
        return interfs;
    }
    
    private LinkedList<Channel> parseChannels ( )
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
