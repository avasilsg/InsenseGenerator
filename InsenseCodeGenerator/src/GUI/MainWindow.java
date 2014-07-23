package GUI;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int         window_Len = 1024;
    private final int         window_vLen = 768;
    private JPanel            contentPane;
    private JLabel            lblCompilerLogoJLabel;
    private JButton           btnOpenFile;
    private JButton           btnValidateXml;
    private JButton           btnGenerate;
    private JButton           btnCompile;
    /**
     * Create main window.
     */
    public MainWindow()
    {
        
        init();
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        btnOpenFile = new JButton("Open file");
        btnOpenFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
            }
        });
        menuBar.add(btnOpenFile);
        
        btnValidateXml = new JButton("Validate XML");
        btnValidateXml.setEnabled(false);
        menuBar.add(btnValidateXml);
        
        btnGenerate = new JButton("Generate");
        btnGenerate.setEnabled(false);
        menuBar.add(btnGenerate);
        
        btnCompile = new JButton("Compile");
        btnCompile.setEnabled(false);
        menuBar.add(btnCompile);

    }
    
    private void init()
    {
        setAlwaysOnTop(true);
        setResizable(false);
        setTitle("Insense Code Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(window_Len, window_vLen);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        lblCompilerLogoJLabel = new JLabel("Insense Code Generator");
        lblCompilerLogoJLabel.setFont(new Font("Stencil", Font.PLAIN, 12));
        contentPane.add(lblCompilerLogoJLabel, BorderLayout.SOUTH);
    }
    
    
}
