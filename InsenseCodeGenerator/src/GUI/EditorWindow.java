package GUI;

import javax.swing.*;

import java.awt.*;

import javax.swing.text.*;

public class EditorWindow extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EditorWindow(String text)
    {
        setSize(663, 599);
        setLocationRelativeTo(null);
       
        JTextPane txt = new JTextPane();
        txt.setText ( text );
        getContentPane().add(new JScrollPane(txt));
        setVisible(true);
    }
}