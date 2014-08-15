package GUI;

import javax.swing.*;

import java.awt.*;

import javax.swing.text.*;

import GrammarAndClauses.Grammar;

public class EditorWindow extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    private DefaultStyledDocument doc;
    @SuppressWarnings("serial")
    public EditorWindow(String text)
    {
        setSize(663, 599);
        setLocationRelativeTo(null);
        this.doc = new DefaultStyledDocument()
        {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
            {
                super.insertString(offset, str, a);
                
                String text = getText(0, getLength());
                int before = Grammar.findLastNonWordChar(text, offset);
                if (before < 0)
                    before = 0;
                int after = Grammar.findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;
                
                while (wordR <= after)
                {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W"))
                    {
                        if (text.substring(wordL, wordR).matches(String.format("(\\W)*(%s)",Grammar.computationalUnitsRegEx)))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }
            
            public void remove(int offs, int len) throws BadLocationException
            {
                super.remove(offs, len);
                
                String text = getText(0, getLength());
                int before = Grammar.findLastNonWordChar(text, offs);
                if (before < 0)
                    before = 0;
                int after = Grammar.findFirstNonWordChar(text, offs);
                
                if (text.substring(before, after).matches(String.format("(\\W)*(%s)",Grammar.computationalUnitsRegEx)))
                {
                    setCharacterAttributes(before, after - before, attr, false);
                }
                else
                {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        JTextPane txt = new JTextPane(doc);
        getContentPane().add(new JScrollPane(txt));
        setVisible(true);
    }
}