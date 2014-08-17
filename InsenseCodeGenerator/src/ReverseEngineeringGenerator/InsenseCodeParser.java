package ReverseEngineeringGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Units.Interface;
import Units.BasicUnits.Channel;
import GrammarAndClauses.Grammer;

public class InsenseCodeParser
{
    private BufferedReader    reader;
    private XMLWriter         writer;
    private String []         container;
//    private ArrayList<String> blockContainer;
//    
//    private int               openBracketCount;
//    private int               closedBrackCount;
//    private int               openCurclyBracketCount;
//    private int               closedCurlyBracketCount;
//    
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
                if ( null == line  )
                {
                    break;
                }
                if ("".equals ( line ))
                {
                    continue;
                }
                this.container = line.split ( "\\s+" );
                removeEmptyElements();
                findExpressionType ( );
                
            }
            catch (IOException e)
            {
            }
        }
        this.writer.writeXMLToFile ( );
    }
    private void removeEmptyElements()
    {
        List<String> list = new ArrayList<String>();

        for(String s : container) {
           if(s != null && s.length() > 0) {
              list.add(s);
           }
        }

        container = list.toArray(new String[list.size()]);
    }
    // private boolean lineContainer ( String lineIn )
    // {
    //
    // String symbol = Grammer.findOpenDelimeterChars ( lineIn );
    // if ( "".endsWith ( symbol ) )
    // {
    // this.blockContainer.add ( lineIn );
    // bracketCounter ( symbol );
    // }
    // symbol = Grammer.findCloseDelimeterChars ( lineIn );
    // if ( "".endsWith ( symbol ) )
    // {
    // this.blockContainer.add ( lineIn );
    // bracketCounter ( symbol );
    // }
    // if ( 0 == checkForClosedExpression ( ) )
    // {
    // return true;
    // }
    // return false;
    // }
    
    // private void bracketCounter ( String symbol )
    // {
    //
    // switch ( symbol)
    // {
    // case "(":
    // {
    // this.openBracketCount++;
    // }
    // case ")":
    // {
    // this.closedBrackCount++;
    // }
    // case "{":
    // {
    // this.openCurclyBracketCount++;
    // }
    // case "}":
    // {
    // this.closedCurlyBracketCount++;
    // }
    // }
    // }
    //
    // private int checkForClosedExpression ( )
    // {
    // return (openBracketCount - closedBrackCount)
    // + (openCurclyBracketCount - closedCurlyBracketCount);
    // }
    //
    private void findExpressionType ( )
    {
        
        for ( int i = 0; i < container.length; i++ )
        {
            String element = Grammer.findExpression ( container[i] );
            if ( !"".equals ( element ) )
            {
                if (true == parseType ( element ))
                {
                    break;
                }
            }
        }
    }
    
    private String readLine ( ) throws IOException
    {
        String line;
        line = reader.readLine ( );
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
            case "out":
            case "in":
            {
                Channel channel = parseChannel ();
                writer.writeXML ( "channel", channel );
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
    
    private Channel parseChannel ( )
    {
        Channel chan = new Channel ( );
        for ( int i = 0; i < container.length; i++ )
        {
            // out integer output ;
            container[i] = container[i].replaceAll ( "\\[\\{\\(", "" )
                    .replaceAll ( "\\]\\}\\)\\;", "" );
            
            if ( "out".equals ( container[i] )
                    || "in".equals ( container[i] ) )
            {
                chan.setDirection ( container[i] );
            }
            if ( !"".equals ( container[i] ) && (i == 1) )
            {
                chan.setType ( container[i] );
            }
            if ( !"".equals ( container[i] ) && (i == 2) )
            {
                String str = container[i].replace ( ";", "" );
                chan.setName ( str );
            }
        }
        return chan;
    }
    
}
