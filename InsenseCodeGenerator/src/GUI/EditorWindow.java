package GUI;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import GrammarAndClauses.Grammer;

public class EditorWindow extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EditorWindow(String text)
    {
        setSize(600, 400);
        setLocationRelativeTo(null);
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = Grammer.findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = Grammer.findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(Program|Compiles|error)"))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }
        };
        JTextPane txt = new JTextPane(doc);
        txt.setText ( text );
        txt.setEditable ( false );
        getContentPane().add(new JScrollPane(txt));
        setVisible(true);
    }
}