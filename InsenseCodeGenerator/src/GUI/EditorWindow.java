package GUI;

import javax.swing.*;

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
       
        JTextPane txt = new JTextPane();
        txt.setText ( text );
        getContentPane().add(new JScrollPane(txt));
        setVisible(true);
    }
}