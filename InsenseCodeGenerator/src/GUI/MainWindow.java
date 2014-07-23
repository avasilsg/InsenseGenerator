package GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame
{
    private final int window_Len = 1024;
    private final int window_vLen = 768;
    private JPanel            contentPane;
    private JLabel            lblCompilerLogoJLabel;
    /**
     * Create main window.
     */
    public MainWindow()
    {
        
        init();
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

    }
    
    private void init()
    {
        setAlwaysOnTop(true);
        setResizable(false);
        setTitle("Project Proposal Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 447, 283);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        lblCompilerLogoJLabel = new JLabel("Insense Code Generator");
        lblCompilerLogoJLabel.setFont(new Font("Stencil", Font.PLAIN, 12));
        contentPane.add(lblCompilerLogoJLabel, BorderLayout.SOUTH);
    }
    
    
}
