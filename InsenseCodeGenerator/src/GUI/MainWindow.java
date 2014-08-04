package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int         window_Len       = 1024;
    private final int         window_vLen      = 768;
    private JPanel            contentPane;
    private JLabel            lblCompilerLogoJLabel;
    private JMenu             mnOpen;
    private JMenuItem         mntmNewXml;
    private JMenuItem         mntmNewInsenseFile;
    private JMenuItem         mntmOpenXml;
    private JMenuItem         mntmOpenInsenseFile;
    private JMenuItem         mntmSaveXmlFile;
    private JMenuItem         mntmSaveInsenseFile;
    private JMenu             mnValidateFile;
    private JMenuItem         mntmValidateXmlFile;
    private JMenuItem         mntmShowReport;
    private JMenu             mnGenerateAndCompile;
    private JMenuItem         mntmGenerateInsenseCode;
    private JMenuItem         mntmCompile;
    private JMenuItem         mntmExit;
    private JFileChooser      fileChooser;
    
    /**
     * Create main window.
     */
    public MainWindow ()
    {
        init ( );
    }
    
    private void initMenus ( )
    {
        JMenuBar menuBar = new JMenuBar ( );
        setJMenuBar ( menuBar );
        
        mnOpen = new JMenu ( "File menu" );
        menuBar.add ( mnOpen );
        
        mntmNewXml = new JMenuItem ( "New XML file" );
        mntmNewXml.addActionListener ( this );
        mnOpen.add ( mntmNewXml );
        
        mntmNewInsenseFile = new JMenuItem ( "New Insense file" );
        mntmNewInsenseFile.addActionListener ( this );
        mnOpen.add ( mntmNewInsenseFile );
        
        mntmOpenXml = new JMenuItem ( "Open XML" );
        mntmOpenXml.addActionListener ( this );
        mnOpen.add ( mntmOpenXml );
        
        mntmOpenInsenseFile = new JMenuItem ( "Open Insense File" );
        mntmOpenInsenseFile.addActionListener ( this );
        mnOpen.add ( mntmOpenInsenseFile );
        
        mntmSaveXmlFile = new JMenuItem ( "Save XML file" );
        mntmSaveXmlFile.addActionListener ( this );
        mnOpen.add ( mntmSaveXmlFile );
        
        mntmSaveInsenseFile = new JMenuItem ( "Save Insense File" );
        mntmSaveInsenseFile.addActionListener ( this );
        mnOpen.add ( mntmSaveInsenseFile );
        
        mntmExit = new JMenuItem ( "Exit" );
        mntmExit.addActionListener ( this );
        mnOpen.add ( mntmExit );
        
        mnValidateFile = new JMenu ( "Validate file" );
        menuBar.add ( mnValidateFile );
        
        mntmValidateXmlFile = new JMenuItem ( "Validate XML file" );
        mnValidateFile.add ( mntmValidateXmlFile );
        
        mntmShowReport = new JMenuItem ( "Show validation report" );
        mnValidateFile.add ( mntmShowReport );
        
        mnGenerateAndCompile = new JMenu ( "Generate and compile" );
        menuBar.add ( mnGenerateAndCompile );
        
        mntmGenerateInsenseCode = new JMenuItem ( "Generate Insense Code" );
        mnGenerateAndCompile.add ( mntmGenerateInsenseCode );
        
        mntmCompile = new JMenuItem ( "Compile" );
        mnGenerateAndCompile.add ( mntmCompile );
    }
    
    private void init ( )
    {
        setAlwaysOnTop ( true );
        setResizable ( false );
        setTitle ( "Insense Code Generator" );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        setSize ( window_Len, window_vLen );
        contentPane = new JPanel ( );
        contentPane.setBorder ( new EmptyBorder ( 5, 5, 5, 5 ) );
        contentPane.setLayout ( new BorderLayout ( 0, 0 ) );
        setContentPane ( contentPane );
        lblCompilerLogoJLabel = new JLabel ( "Insense Code Generator" );
        lblCompilerLogoJLabel.setFont ( new Font ( "Stencil", Font.PLAIN, 12 ) );
        contentPane.add ( lblCompilerLogoJLabel, BorderLayout.SOUTH );
        initMenus ( );
    }
    
    public void actionPerformed ( ActionEvent e )
    {
        
        JMenuItem myMenu = (JMenuItem) e.getSource ( );
        fileChooser = new JFileChooser ( );
        switch ( myMenu.getText ( ))
        {
            case "Save XML file":
            case "Open XML":
            {
                FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter (
                        "xml files (*.xml)", "xml" );
                fileChooser.addChoosableFileFilter ( xmlFilter );
                fileChooser.setFileFilter ( xmlFilter );
                openDialog ( myMenu.getText ( ));
                break;
            }
            case "Open Insense File":
            case "Save Insense File":
            {
                FileNameExtensionFilter insenseFilter = new FileNameExtensionFilter (
                        "insense files (*.isf)", "isf" );
                fileChooser.addChoosableFileFilter ( insenseFilter );
                fileChooser.setFileFilter ( insenseFilter );
                openDialog (myMenu.getText ( ));
                break;
            }
            case "Exit":
            {
                // this.dispose ( );
                System.exit ( EXIT_ON_CLOSE );
            }
        }
        System.out.println ( "Menu Selected: " + myMenu.getText ( ) );
    }

    private void openDialog (String title)
    {
        if (title == "Open XML" || title == "Open Insense File")
        {
            int returnVal = fileChooser.showOpenDialog ( MainWindow.this );
            if ( returnVal == JFileChooser.APPROVE_OPTION )
            {
                File file = fileChooser.getSelectedFile ( );
                System.out.print ( file.getName ( ) );
            }
        }
        
        if (title == "Save XML file" || title == "Save Insense File")
        {
            int returnVal = fileChooser.showSaveDialog ( MainWindow.this );
            if ( returnVal == JFileChooser.APPROVE_OPTION )
            {
                File file = fileChooser.getSelectedFile ( );
                System.out.print ( file.getName ( ) );
            }
        }
        
    }
    
}
