package GrammarAndClauses;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Test extends JFrame {
    public static String computationalUnits = "(inteface|component|behaviour|send|receive|proc|struct|constructor|integer|real|boolean|new|presents|return|on|to|from)";

    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    public Test () {

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(private|public|protected)"))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(private|public|protected)")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        JTextPane txt = new JTextPane(doc);
        txt.setText("public class Hi {}");
        add(new JScrollPane(txt));
        setVisible(true);
        setSize(400,400);
    }

    public static void main (String args[]) {
        new Test();
    }
}
public class Grammar extends DefaultStyledDocument 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static String computationalUnits = "(inteface|component|behaviour|send|receive|proc|struct|constructor|integer|real|boolean|new|presents|return|on|to|from)";
    
    final StyleContext        cont             = StyleContext
                                                       .getDefaultStyleContext ( );
    final AttributeSet        attr             = cont.addAttribute (
                                                       cont.getEmptySet ( ),
                                                       StyleConstants.Foreground,
                                                       Color.RED );
    final AttributeSet        attrBlack        = cont.addAttribute (
                                                       cont.getEmptySet ( ),
                                                       StyleConstants.Foreground,
                                                       Color.BLACK );
    
    public int findLastNonWordChar ( String text, int index )
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
    
    public int findFirstNonWordChar ( String text, int index )
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
    public void insertString (int offset, String text, AttributeSet a) throws BadLocationException {

        int before = findLastNonWordChar(text, offset);
        if (before < 0) before = 0;
        int after = findFirstNonWordChar(text, offset + text.length());
        int wordL = before;
        int wordR = before;

        while (wordR <= after) {
            if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                if (text.substring(wordL, wordR).matches("(\\W)*(private|public|protected)"))
                    setCharacterAttributes(wordL, wordR - wordL, attr, false);
                else
                    setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                wordL = wordR;
            }
            wordR++;
        }
    }
    public void remove ( String txt, int offs, int len ) throws BadLocationException
    {
        
        String text = txt;
        int before = findLastNonWordChar ( text, offs );
        if ( before < 0 ) before = 0;
        int after =  findFirstNonWordChar ( text, offs );
        
        if ( text.substring ( before, after ).matches (
                String.format ( "(\\W)*%s", Grammar.computationalUnits ) ) )
        {
            setCharacterAttributes ( before, after - before, attr, false );
        }
        else
        {
            setCharacterAttributes ( before, after - before, attrBlack, false );
        }
    }
}
