package GrammarAndClauses;

import javax.swing.text.DefaultStyledDocument;

public class Grammer extends DefaultStyledDocument
{
    /**
     * 
     */
    private static final long serialVersionUID        = 1L;
    /**
     * 
     */
    
    public static String      computationalUnits[]    = { "interface",
            "component", "type", "behaviour", "send", "receive", "proc",
            "struct", "constructor", "integer", "real", "bool", "connect",
            "in", "out", "new", "presents", "return", "on", "to", "from" };
    public static String      computationalUnitsRegEx = "inteface|component|behaviour|send|receive|proc|struct|constructor|integer|real|boolean|new|presents|return|on|to|from";
    
    public static int findLastNonWordChar ( String text, int index )
    {
        while (--index >= 0)
        {
            if ( String.valueOf ( text.charAt ( index ) ).matches ( "\\W" ) )
            {
                break;
            }
        }
        return index;
    }
    
    public static int findFirstNonWordChar ( String text, int index )
    {
        while (index < text.length ( ))
        {
            if ( String.valueOf ( text.charAt ( index ) ).matches ( "\\W" ) )
            {
                break;
            }
            index++;
        }
        return index;
    }
    
    public static boolean findWord ( String word )
    {
        for ( int i = 0; i < computationalUnits.length; i++ )
        {
            if ( word.equals ( computationalUnits[i] ) )
            {
                return true;
            }
        }
        return false;
    }
    
    public static String findExpression ( String word )
    {
        for ( int i = 0; i < computationalUnits.length; i++ )
        {
            if ( word.equals ( computationalUnits[i] ) )
            {
                return computationalUnits[i];
            }
        }
        return "";
    }
    
    public boolean findOpenDelimeterChars ( String [] expression )
    {
        for ( int i = 0; i < expression.length; i++ )
        {
            switch ( expression[i])
            {
                case "{":
                case "(":
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean findCloseDelimeterChars ( String [] expression )
    {
        for ( int i = 0; i < expression.length; i++ )
        {
            switch ( expression[i])
            {
                case "}":
                case ";":
                case ")":
                {
                    return true;
                }
            }
        }
        return false;
    }
}
