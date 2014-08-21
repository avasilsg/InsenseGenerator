package GrammarAndClauses;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
    public static String      computationalUnitsRegEx = "(connect|type|is|any|interface|component|behaviour|send|receive|proc|struct|constructor|integer|real|bool|new|presents|return|on|to|from|in|out)";
    final  static StyleContext cont = StyleContext.getDefaultStyleContext();
    final  public static AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    final  public static AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);     
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
    
    public static boolean findCloseDelimeterChars ( String word  )
    {
        if(word.contains("}"))
        {
            return true;
        }
//        if (word.contains(")"))
//        {
//            return ")";
//        }
//        if (word.contains(";"))
//        {
//            return ";";
//        }
        return false;
    }
    
    public static int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    public static int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }
}
