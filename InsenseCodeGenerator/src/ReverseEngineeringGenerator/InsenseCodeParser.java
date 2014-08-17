package ReverseEngineeringGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Units.Component;
import Units.Interface;
import Units.Struct;
import Units.BasicUnits.Channel;
import Units.BasicUnits.Field;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;
import GrammarAndClauses.Grammer;

public class InsenseCodeParser
{
    private BufferedReader    reader;
    private XMLWriter         writer;
    private String []         container;

    private boolean           isCurlyBracket;
    private boolean           isAfterComponent;
    
    public InsenseCodeParser ( final String filePath)
    {
        try
        {
            isAfterComponent = false;
            isCurlyBracket   = false;
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
                if (Grammer.findOpenDelimeterChars ( line ))
                {
                    this.isCurlyBracket = true;
                    continue;
                }
                else
                {
                    this.container = line.split ( "\\s+" );
                }
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
   //TODO: workaround fields of component
    private void findExpressionType ( )
    {
        boolean isKeyType = false;
        for ( int i = 0; i < container.length; i++ )
        {
            String element = Grammer.findExpression ( container[i] );
            if ( !"".equals ( element ) )
            {
                if (true == parseType ( element ))
                {
                    isKeyType = true;
                    break;
                }
            }
        }
        if ((0 < container.length) && !isKeyType)
        {
            if (this.isAfterComponent && this.isCurlyBracket)
            {
                Field field = parseField();
                writer.writeXML ( "field", field );
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
                this.isAfterComponent = false;
                this.isCurlyBracket = false;
                writer.writeXML ( elementType, interfs );
                return true;
            }
            case "struct":
            {
                Struct struct = parseStruct();
                this.isAfterComponent = false;
                this.isCurlyBracket = false;
                writer.writeXML ( elementType, struct );
                return true;                
            }
            case "out":
            case "in":
            {
                Channel channel = parseChannel ();
                writer.writeXML ( "channel", channel );
                return true;
            }
            case "component":
            {
                Component component = parseComponent();
                this.isAfterComponent = true;
                writer.writeXML ( elementType, component );
                return true;                
            }
            case "behaviour":
            {
                this.isCurlyBracket = false;
                this.isAfterComponent = false;
                writer.writeXML ( elementType, elementType );
                return true;
            }
            case "send":
            {
                Send send = parseSend();
                writer.writeXML ( elementType, send );
                return true;
            }
            case "receive":
            {
                Receive receive = parseReceive();
                writer.writeXML ( elementType, receive );
                return true;
            }
            case "connect":
            {
                writer.writeXML ( elementType, elementType );
                return true;
            }
            case "real":
            case "integer":
            case "bool":
            {
                Field field = parseField();
                writer.writeXML ( "field", field );
                return true;
            }
            
        }
        return false;
        
    }
    private Receive parseReceive ( )
    {
        Receive receive = new Receive();
        boolean isAfterIdentifier = false;
        for (int i = 0; i < container.length; i++)
        {
            if ("receive".equals (container [i]))
            {
                isAfterIdentifier = true;
                continue;
            }
            if ("from".equals ( container [i]))
            {
                isAfterIdentifier = false;
                continue;
            }
            if (!"".equals ( container [i]))
            {
                if (isAfterIdentifier)
                {
                    receive.setInderntifier ( container[i] ); 
                }
                else
                {
                    receive.setFrom ( container[i] );
                }
            }
        }
        return receive;
    }
//  <send identifier="reading" on="printChan"/>
    private Send parseSend ( )
    {
        Send send = new Send();
        boolean isAfterIdentifier = false;
        for (int i = 0; i < container.length; i++)
        {
            if ("send".equals ( container [i]))
            {
                isAfterIdentifier = true;
                continue;
            }
            if ("on".equals ( container [i]))
            {
                isAfterIdentifier = false;
                continue;
            }
            if (!"".equals ( container [i]))
            {
                if (isAfterIdentifier)
                {
                   send.setInderntifier ( container[i] ); 
                }
                else
                {
                    send.setOn ( container[i] );
                }
            }
        }
        return send;
    }

    private Component parseComponent ( )
    {
        Component component = new Component();
        boolean isComponent = false, isPresents = false;
//        component SensorReader presents ISensorReader 
        for ( int i = 0; i < this.container.length; i++ )
        {
            container[i] = container[i].replaceAll ( "[\\{\\(;]", "" );
            if ( container[i].equals ( "component" ) )
            {
                isComponent = true;
                continue;
            }
            else if ( "presents".equals ( container[i] ) )
            {
                isPresents = true;
                continue;
            }
            else if ( !"".equals ( container[i] ) )
            {
                if (true == isComponent)
                {
                    component.setName ( container[i] ); 
                    isComponent = false;
                }
                if (true == isPresents)
                {
                    component.setPresent ( container[i] );
                }
            }
        }
        return component;
    }

    private Struct parseStruct ( )
    {
        // type ITimedTempReader is interface
        Struct struct = new Struct ( );
        for ( int i = 0; i < this.container.length; i++ )
        {
            container[i] = container[i].replaceAll ( "[\\{\\(;]", "" );
            if ( container[i].equals ( "type" ) )
            {
                continue;
            }
            else if ( "is".equals ( container[i] ) )
            {
                continue;
            }
            else if ( "struct".equals ( container[i] ) )
            {
                continue;
            }
            else if ( !"".equals ( container[i] ) )
            {
                struct.setName ( container[i] );
            }
        }
        
        return struct;
    }
    
    private Field parseField()
    {
        boolean isContainsEqual = false;
        Field field = new Field();
        for(int i = 0; i < this.container.length; i++)
        {
            if (container[i].contains ( "=" ))
            {
                isContainsEqual = true;
            }
            container[i] = container[i].replaceAll ( "[\\{\\(;=]", "" );
        }
        removeEmptyElements();
        if (2 == container.length)
        {
            if (!isContainsEqual)
            {
                field.setType ( container[0] );
                field.setName ( container[1] );
            }
            else
            {
                field.setName  ( container[0] );
                field.setValue ( container[1] );
            }
        }
        else
        {
            field.setType ( container[0] );
            field.setName ( container[1] );
            field.setValue( container[2] );
        }
        return field;
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
            container[i] = container[i].replaceAll ( "[\\{\\(;]", "" );
            
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
                chan.setName ( container[i] );
            }
        }
        return chan;
    }
    
}
