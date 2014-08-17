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
        word = word.replaceAll("\\[\\{\\(", "").replaceAll("\\]\\}\\)\\;","");
        for ( int i = 0; i < computationalUnits.length; i++ )
        {
            if ( word.equals ( computationalUnits[i] ) )
            {
                return computationalUnits[i];
            }
        }
        return "";
    }
       
    public static boolean findOpenDelimeterChars ( String word )
    {
        if(word.contains("{"))
        {
            return true;
        }
//        if (word.contains("("))
//        {
//            return "(";
//        }
        return false;
    }
    
    public static String findCloseDelimeterChars ( String word  )
    {
        if(word.contains("}"))
        {
            return "}";
        }
        if (word.contains(")"))
        {
            return ")";
        }
        if (word.contains(";"))
        {
            return ";";
        }
        return "";
    }
}
