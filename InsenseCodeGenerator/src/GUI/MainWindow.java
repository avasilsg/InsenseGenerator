package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.ComponentOrientation;
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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import GrammarAndClauses.Grammer;
import InsenseGenerator.CompilerCaller;
import InsenseGenerator.XmlParser;
import XMLGenerator.InsenseCodeParser;
import XMLGenerator.XMLFileContainer;

public class MainWindow extends JFrame implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private final int         window_Len       = 1024;
    private final int         window_vLen      = 768;
    private JPanel            contentPane;
    private JMenu             mnOpen;
    private JMenuItem         menuItem;
    private JFileChooser      fileChooser;
    private JPanel            panel;
    private boolean           isTheCycleCompleted;
    private boolean           isGeneratedInsens;
    private XMLFileContainer  xmlFile;
    private CompilerCaller    compileCaller;
    private JLabel            lblInsenseCodeGenerator;
    private JMenuBar          menuBar;
    private JTextPane         textVisualizerArea;
    private DefaultStyledDocument documentPresentationLayer;
    private String            fileFormat;
    private WordSuggester     test;
    /**
     * Create main window.
     */
    public MainWindow()
    {
        isTheCycleCompleted  = false;
        isGeneratedInsens    = false;
        fileFormat           = "";
        xmlFile              = new XMLFileContainer();
        compileCaller        = new CompilerCaller();
        init();
    }
    
    private void initMenus()
    {
        menuBar = new JMenuBar();
        
        mnOpen = new JMenu("File menu");
        mnOpen.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnOpen);
        
        menuItem = new JMenuItem("New file");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Open file");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
               
        menuItem = new JMenuItem("Save file");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        setJMenuBar(menuBar);
        
        mnOpen = new JMenu("XML notation");
        mnOpen.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnOpen);
        
        menuItem = new JMenuItem("Generate XML Code");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("View XML");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        mnOpen = new JMenu("Insense code");
        mnOpen.setHorizontalAlignment(SwingConstants.CENTER);
        menuBar.add(mnOpen);
        
        menuItem = new JMenuItem("Generate Insense Code");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("View Insense");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Compile for UnixOS");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Compile for InsenseOS");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Compile for Contiki");
        menuItem.addActionListener(this);
        mnOpen.add(menuItem);
        
        lblInsenseCodeGenerator = new JLabel("                                                                                     Insense Code Generator");
        menuBar.add(lblInsenseCodeGenerator);
        lblInsenseCodeGenerator.setFont(new Font("URW Bookman L", Font.PLAIN, 17));
    }
    
    private void init()
    {
        setAlwaysOnTop(true);
        setResizable(true);
        setTitle("Insense Code Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(window_Len, window_vLen);
        initMenus();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(5, 5));
        setContentPane(contentPane);
        
        panel = new JPanel();
        panel.setBorder(null);
        contentPane.add(panel, BorderLayout.CENTER);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 1010, Short.MAX_VALUE));
        gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGap(0, 684, Short.MAX_VALUE));
        
        textVisualizerArea = new JTextPane();
        textVisualizerArea.setBackground(UIManager.getColor("Button.background"));
        textVisualizerArea.setEnabled(false);
        textVisualizerArea.setEditable(false);
        textVisualizerArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setupDocumentLayer ( );
        textVisualizerArea.setDocument ( documentPresentationLayer );
        getContentPane().add(new JScrollPane(textVisualizerArea));
        test = new WordSuggester(textVisualizerArea);
        test.initUI();
        panel.setLayout(gl_panel);
    }

    private void setupDocumentLayer ( )
    {
        documentPresentationLayer = new DefaultStyledDocument() 
        {
            private static final long serialVersionUID = 1L;
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = Grammer.findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = Grammer.findFirstNonWordChar(text, offset + str.length());
                int wordL = before, wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) 
                    {
                        
                        if (text.substring(wordL, wordR).matches("(\\W)*" + Grammer.computationalUnitsRegEx) && "isf" == fileFormat)
                            setCharacterAttributes(wordL, wordR - wordL, Grammer.attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, Grammer.attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = Grammer.findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = Grammer.findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*" + Grammer.computationalUnitsRegEx)) {
                    setCharacterAttributes(before, after - before, Grammer.attr, false);
                } else {
                    setCharacterAttributes(before, after - before, Grammer.attrBlack, false);
                }
            }
            
        };
    }
    
    public void actionPerformed(ActionEvent event)
    {
        JMenuItem myMenu = (JMenuItem) event.getSource();
        fileChooser = new JFileChooser();
        switch (myMenu.getText())
        {
            case "Open file":
            case "Save file":
            {
                FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
                FileNameExtensionFilter insenseFilter = new FileNameExtensionFilter("insense files (*.isf)", "isf");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(xmlFilter);
                fileChooser.setFileFilter(insenseFilter);
                fileChooser.setFileFilter(xmlFilter);
                openDialog(myMenu.getText());
                break;
            }
            case "Generate XML Code":
            case "Generate Insense Code":
            {
                openDialog(myMenu.getText());
                break;
            }
            case "New file":
            {
                activateTextArea();
                this.textVisualizerArea.setText("");
                this.compileCaller.setInsenseFilePath ( "" );
                break;
            }
            case "Compile for Contiki":
            case "Compile for InsenseOS":
            case "Compile for UnixOS":
            {
                this.compileCaller.compileByVersion ( myMenu.getText() );
                break;
            }
            case "View XML":
            case "View Insense":
            {
                if (null == this.xmlFile.getFilePath ( ) || "".equals ( this.xmlFile.getFilePath ( )))
                {
                    JOptionPane.showMessageDialog(null, "Open xml or insense file first!");
                }
                else
                {
                    actionEventView(myMenu.getText());
                }
                break;
            }
            case "Exit":
            {
                System.exit(EXIT_ON_CLOSE);
            }
        }
    }
    
    private void actionEventView(String eventType)
    {
        try
        {
            if ("View Insense".equals(eventType))
            {
                if (this.isGeneratedInsens)
                {
                    fileFormat = "isf";
                    showFileOnTheScreen(this.compileCaller.getInsenseFilePath ( ), ".isf");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Generate insense source code first!");
                }
            }
            else
            {
                fileFormat = "xml";
                showFileOnTheScreen(this.xmlFile.getFilePath ( ), ".xml");
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void parseXML(String filePath)
    {
        XmlParser parser = new XmlParser(filePath);
        parser.parseXML();
    }
    
    private void parseInsense(String filePath)
    {
        InsenseCodeParser parser = new InsenseCodeParser(filePath);
        this.xmlFile.setFilePath ( parser.parseInsense() );
    }
    
    private void openDialog(String title)
    {
        if (title == "Open file")
        {
            int returnVal = fileChooser.showOpenDialog(MainWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    this.xmlFile.setFilePath ( fileChooser.getSelectedFile().getAbsolutePath() );
                    isTheCycleCompleted = true;
                    validateFileFormat();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if (title == "Generate Insense Code")
        {
            if (this.isTheCycleCompleted)
            {
                parseXML(this.xmlFile.getFilePath ( ));
                isGeneratedInsens = true;
                this.compileCaller.setInsenseFilePath (  new File("").getAbsolutePath() + "/" + "temp" + "/" + "temp" + ".isf");
                this.compileCaller.setInsenseFileName ( "temp" );
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Choose an xml file!");
            }
        }
        if (title == "Generate XML Code")
        {
            parseInsense(this.compileCaller.getInsenseFilePath ( ));
        }
        if (title == "Save file")
        {
            int returnVal = fileChooser.showSaveDialog(MainWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                String ext = ((FileNameExtensionFilter) fileChooser.getFileFilter()).getExtensions()[0];
                switch (ext)
                {
                    case "xml":
                    {
                        String file = fileChooser.getSelectedFile().getAbsolutePath() + ".xml";
                        saveToFile(file);
                        break;
                    }
                    case "isf":
                    {
                        String file = fileChooser.getSelectedFile().getAbsolutePath() + ".isf";
                        saveToFile(file);
                        break;
                    }
                }
            }
        }
    }
    
    void saveToFile(String fileName)
    {
        FileOutputStream out;
        try
        {
            //TODO: check
            out = new FileOutputStream(fileName, false);
            this.compileCaller.setInsenseFilePath ( fileName );
            this.compileCaller.setInsenseFileName ( fileChooser.getSelectedFile().getName ( ) );
            out.write(this.textVisualizerArea.getText().getBytes());
            out.close();
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void validateFileFormat() throws IOException
    {
        File file = fileChooser.getSelectedFile();
        String fileName = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        if (true == fileName.equals("xml"))
        {
            fileFormat = "xml";
            activateTextArea();
            showFileOnTheScreen(file.getAbsolutePath(), ".xml");
        }
        else if (true == fileName.equals ( "isf" ))
        {
            fileFormat = "isf";
            activateTextArea();
            showFileOnTheScreen(file.getAbsolutePath(), ".isf");
            this.compileCaller.setInsenseFilePath ( file.getAbsolutePath ( ) );
            this.compileCaller.setInsenseFileName ( file.getName ( ).indexOf(".") > 0 ? file.getName ( ).substring(0, file.getName ( ).lastIndexOf(".")) : file.getName ( ) );
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Choose an xml file!");
        }
    }
    
    private void activateTextArea()
    {
        textVisualizerArea.setEditable(true);
        textVisualizerArea.setEnabled(true);
    }
    
    private void showFileOnTheScreen(String path, String fileFormat) throws FileNotFoundException, IOException
    {
        textVisualizerArea.setText("");
        FileReader fileReader = new FileReader(path);
        @SuppressWarnings("resource")
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        boolean eof = false;
        while (!eof)
        {
            String lineIn = bufferedReader.readLine();
            if (lineIn == null)
            {
                eof = true;
            }
            else
            {
                if (".isf".equals(fileFormat))
                {
                    addToDocument(documentPresentationLayer, lineIn);                    
                    addToDocument(documentPresentationLayer, "\n");
                }
                else
                {
                    addToDocument(documentPresentationLayer, lineIn);
                    addToDocument(documentPresentationLayer, "\n");
                }
                
            }
        }
    } 
    private void addToDocument(DefaultStyledDocument doc,  String keyWords)
    {
        try
        {
            doc.insertString(doc.getLength(), keyWords, null);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }
}
