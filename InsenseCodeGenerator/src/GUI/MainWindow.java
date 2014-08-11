package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Generator.XmlParser;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.TextArea;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class MainWindow extends JFrame implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final int         window_Len       = 1024;
    private final int         window_vLen      = 768;
    private JPanel            contentPane;
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
    private JMenuItem         mntmCompiles;
    private JMenuItem         mntmExit;
    private JFileChooser      fileChooser;
    private JPanel            panel;
    private boolean           isTheCycleCompleted;
    private JButton           btnViewInsense;
    private JButton           btnViewXml;
    private String            fileNameString;
    private JLabel            lblInsenseCodeGenerator;
    private TextArea          textVisualizerArea;
    
    /**
     * Create main window.
     */
    public MainWindow ()
    {
        isTheCycleCompleted = false;
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
        mntmGenerateInsenseCode.addActionListener ( this );
        mnGenerateAndCompile.add ( mntmGenerateInsenseCode );
        
        mntmCompile = new JMenuItem ( "Compile Unix" );
        mnGenerateAndCompile.add ( mntmCompile );
        
        mntmCompiles = new JMenuItem ( "Compile Unix" );
        mnGenerateAndCompile.add ( mntmCompiles );
        // add(scrollV);
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
        
        panel = new JPanel ( );
        panel.setBorder ( null );
        contentPane.add ( panel, BorderLayout.CENTER );
        
        btnViewXml = new JButton ( "View XML" );
        btnViewXml.addActionListener ( new ActionListener ( )
        {
            public void actionPerformed ( ActionEvent event )
            {
                try
                {
                    showFileOnTheScreen ( fileNameString );
                }
                catch (IOException e)
                {
                    e.printStackTrace ( );
                }
            }
        } );
        btnViewXml.setEnabled ( false );
        
        btnViewInsense = new JButton ( "View Insense" );
        btnViewInsense.addActionListener ( new ActionListener ( )
        {
            public void actionPerformed ( ActionEvent event )
            {
                String filePathString =     new File("").getAbsolutePath ( ) + "/" + "temp" + "/" + "temp" + ".txt";
                try
                {
                    showFileOnTheScreen ( filePathString );
                }
                catch (IOException e)
                {
                    e.printStackTrace ( );
                }
                
            }
        } );
        btnViewInsense.setEnabled ( false );
        
        JPanel innerPanel = new JPanel ( );
        innerPanel.setAlignmentY ( Component.BOTTOM_ALIGNMENT );
        innerPanel
                .setComponentOrientation ( ComponentOrientation.LEFT_TO_RIGHT );
        innerPanel.setAlignmentX ( Component.RIGHT_ALIGNMENT );
        GroupLayout gl_panel = new GroupLayout ( panel );
        gl_panel.setHorizontalGroup ( gl_panel
                .createParallelGroup ( Alignment.LEADING )
                .addGroup (
                        gl_panel.createSequentialGroup ( )
                                .addGroup (
                                        gl_panel.createParallelGroup (
                                                Alignment.LEADING )
                                                .addGroup (
                                                        gl_panel.createSequentialGroup ( )
                                                                .addComponent (
                                                                        btnViewXml )
                                                                .addPreferredGap (
                                                                        ComponentPlacement.RELATED )
                                                                .addComponent (
                                                                        btnViewInsense ) )
                                                .addComponent (
                                                        innerPanel,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        1010,
                                                        GroupLayout.PREFERRED_SIZE ) )
                                .addContainerGap ( GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE ) ) );
        gl_panel.setVerticalGroup ( gl_panel.createParallelGroup (
                Alignment.LEADING ).addGroup (
                gl_panel.createSequentialGroup ( )
                        .addGroup (
                                gl_panel.createParallelGroup (
                                        Alignment.BASELINE )
                                        .addComponent ( btnViewXml )
                                        .addComponent ( btnViewInsense ) )
                        .addPreferredGap ( ComponentPlacement.RELATED )
                        .addComponent ( innerPanel, GroupLayout.DEFAULT_SIZE,
                                649, Short.MAX_VALUE ).addContainerGap ( ) ) );
        
        textVisualizerArea = new TextArea ( );
        textVisualizerArea.setSize ( innerPanel.getSize ( ) );
        textVisualizerArea.setEnabled ( false );
        textVisualizerArea.setEditable ( false );
        textVisualizerArea
                .setComponentOrientation ( ComponentOrientation.LEFT_TO_RIGHT );
        GroupLayout gl_innerPanel = new GroupLayout ( innerPanel );
        gl_innerPanel
                .setHorizontalGroup ( gl_innerPanel.createParallelGroup (
                        Alignment.LEADING ).addGroup (
                        Alignment.TRAILING,
                        gl_innerPanel
                                .createSequentialGroup ( )
                                .addComponent ( textVisualizerArea,
                                        GroupLayout.DEFAULT_SIZE, 1010,
                                        Short.MAX_VALUE ).addContainerGap ( ) ) );
        gl_innerPanel.setVerticalGroup ( gl_innerPanel.createParallelGroup (
                Alignment.LEADING )
                .addGroup (
                        gl_innerPanel
                                .createSequentialGroup ( )
                                .addGap ( 3 )
                                .addComponent ( textVisualizerArea,
                                        GroupLayout.DEFAULT_SIZE, 646,
                                        Short.MAX_VALUE ) ) );
        innerPanel.setLayout ( gl_innerPanel );
        panel.setLayout ( gl_panel );
        lblInsenseCodeGenerator = new JLabel ( "Insense Code Generator" );
        contentPane.add ( lblInsenseCodeGenerator, BorderLayout.SOUTH );
        lblInsenseCodeGenerator.setFont ( new Font ( "Copperplate Gothic Bold",
                Font.PLAIN, 17 ) );
        initMenus ( );
    }
    
    public void actionPerformed ( ActionEvent event )
    {
        
        JMenuItem myMenu = (JMenuItem) event.getSource ( );
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
                openDialog ( myMenu.getText ( ) );
                break;
            }
            case "Open Insense File":
            case "Save Insense File":
            {
                FileNameExtensionFilter insenseFilter = new FileNameExtensionFilter (
                        "insense files (*.isf)", "isf" );
                fileChooser.addChoosableFileFilter ( insenseFilter );
                fileChooser.setFileFilter ( insenseFilter );
                openDialog ( myMenu.getText ( ) );
                break;
            }
            case "Generate Insense Code":
            {
                openDialog ( myMenu.getText ( ) );
                break;
            }
            case "New XML file":
            {
                activateTextArea ( );
                this.textVisualizerArea.setText ( "" );
                this.btnViewXml.setEnabled ( false );
                break;
            }
            case "New Insense file":
            {
                activateTextArea ( );
                this.textVisualizerArea.setText ( "" );
                this.btnViewInsense.setEnabled ( false );
                break;
            }
            case "Compile":
            {
                try
                {
                    String Insensefile = "/home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/IntermediateNotation/test1.isf";
                    String executable = "/home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/IntermediateNotation/test";
                    Runtime.getRuntime ( )
                            .exec ( String
                                    .format (
                                            "java -jar /home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/GUI/InsenseCompilerUnix.jar %s %s",
                                            Insensefile, executable ) );
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace ( );
                }
            }
            case "Exit":
            {
                System.exit ( EXIT_ON_CLOSE );
            }
        }
        System.out.println ( "Menu Selected: " + myMenu.getText ( ) );
    }
    
    private void parseXML ( String filePath )
    {
        XmlParser parser = new XmlParser ( filePath );
        parser.parseXML ( );
    }
    
    private void openDialog ( String title ) 
    {
        if ( title == "Open XML" || title == "Open Insense File" )
        {
            int returnVal = fileChooser.showOpenDialog ( MainWindow.this );
            if ( returnVal == JFileChooser.APPROVE_OPTION )
            {
                try
                {
                    fileNameString = fileChooser.getSelectedFile ( )
                            .getAbsolutePath ( );
                    isTheCycleCompleted = true;
                    validateFileFormat ( );
                }
                catch (IOException e)
                {
                    e.printStackTrace ( );
                }
            }
        }
        if ( title == "Generate Insense Code" )
        {
            if ( this.isTheCycleCompleted )
            {
                parseXML ( fileNameString );
                this.btnViewInsense.setEnabled ( true );
            }
            else
            {
                JOptionPane.showMessageDialog ( null, "Choose an xml file!" );
            }
        }
        if ( title == "Save XML file" || title == "Save Insense File" )
        {
            int returnVal = fileChooser.showSaveDialog ( MainWindow.this );
            if ( returnVal == JFileChooser.APPROVE_OPTION )
            {
                String file = fileChooser.getSelectedFile ( ).getAbsolutePath ( ) + fileChooser.getFileFilter ( );
                saveToFile(file);
                System.out.print ( file );
            }
        }
        
    }
    void saveToFile(String fileName) 
    {
        FileOutputStream out;
        try
        {
            out = new FileOutputStream(fileName, true);
            out.write(this.textVisualizerArea.getText().getBytes());
            out.close ( );
        }
        catch (FileNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     } 
    
    private void validateFileFormat ( ) throws IOException
    {
        File file = fileChooser.getSelectedFile ( );
        String fileName = file
                .getName ( )
                .substring ( file.getName ( ).lastIndexOf ( "." ) + 1,
                        file.getName ( ).length ( ) ).toLowerCase ( );
        if ( true == fileName.equals ( "xml" ) )
        {
            activateTextArea ( );
            this.btnViewXml.setEnabled ( true );
            showFileOnTheScreen ( file.getAbsolutePath ( ) );
        }
        else
        {
            JOptionPane.showMessageDialog ( null, "Choose an xml file!" );
        }
        System.out.print ( file.getName ( ) );
    }
    
    private void activateTextArea ( )
    {
        textVisualizerArea.setFont ( new Font ( "Monospaced", Font.BOLD, 12 ) );
        textVisualizerArea.setForeground ( Color.BLUE );
        textVisualizerArea.setEditable ( true );
        textVisualizerArea.setEnabled ( true );
    }
    
    private void showFileOnTheScreen ( String path )
            throws FileNotFoundException, IOException
    {
        textVisualizerArea.setText ( "" );
        FileReader fileReader = new FileReader ( path );
        @SuppressWarnings ("resource")
        BufferedReader bufferedReader = new BufferedReader ( fileReader );
        boolean eof = false;
        
        while (!eof)
        {
            String lineIn = bufferedReader.readLine ( );
            if ( lineIn == null )
            {
                eof = true;
            }
            else
            {
                textVisualizerArea.append ( lineIn
                        + System.getProperty ( "line.separator" ) );
            }
        }
    }
}
