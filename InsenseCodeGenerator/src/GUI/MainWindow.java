package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.SwingConstants;

import Generator.CompilerCaller;
import Generator.XmlParser;
import GrammarAndClauses.Grammar;
import ReverseEngineeringGenerator.XMLFileContainer;

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
    //TODO: move them to grammar
    private int               count = 0;
    private String            tabs = "";
    
    /**
     * Create main window.
     */
    public MainWindow()
    {
        isTheCycleCompleted = false;
        isGeneratedInsens = false;
        xmlFile       = new XMLFileContainer();
        compileCaller = new CompilerCaller();
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
        
        menuItem = new JMenuItem("Generate XML");
        mnOpen.add(menuItem);
        
        menuItem = new JMenuItem("Validate XML");
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
        setResizable(false);
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
        getContentPane().add(new JScrollPane(textVisualizerArea));
        panel.setLayout(gl_panel);
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
        System.out.println("Menu Selected: " + myMenu.getText());
    }
    
    private void actionEventView(String eventType)
    {
        try
        {
            if ("View Insense".equals(eventType))
            {
                if (this.isGeneratedInsens)
                {
                    showFileOnTheScreen(this.compileCaller.getInsenseFilePath ( ), ".isf");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Generate insense source code first!");
                }
            }
            else
            {
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
                this.compileCaller.setInsenseFilePath (  new File("").getAbsolutePath() + "/" + "temp" + "/" + "temp" + ".txt");
                this.compileCaller.setInsenseFileName ( "temp" );
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Choose an xml file!");
            }
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
                        System.out.println("xml");
                        String file = fileChooser.getSelectedFile().getAbsolutePath() + ".xml";
                        saveToFile(file);
                        break;
                    }
                    case "isf":
                    {
                        System.out.println("isf");
                        String file = fileChooser.getSelectedFile().getAbsolutePath() + ".isf";
                        this.compileCaller.setInsenseFilePath ( file );
                        this.compileCaller.setInsenseFileName ( fileChooser.getSelectedFile().getName ( ) );
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
            out = new FileOutputStream(fileName, true);
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
            activateTextArea();
            // this.btnViewXml.setEnabled(true);
            showFileOnTheScreen(file.getAbsolutePath(), ".xml");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Choose an xml file!");
        }
        System.out.print(file.getName());
    }
    
    private void activateTextArea()
    {
        textVisualizerArea.setEditable(true);
        textVisualizerArea.setEnabled(true);
    }
    
    private void showFileOnTheScreen(String path, String fileFormat) throws FileNotFoundException, IOException
    {
        textVisualizerArea.setText("");
        DefaultStyledDocument doc = new DefaultStyledDocument();
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
                    parseInsenseToScreen(doc, lineIn);
                }
                else
                {
                    addToDocument(doc, null, lineIn);
                    addToDocument(doc, null, "\n");
                }
                
            }
        }
        textVisualizerArea.setDocument(doc);
    }
    
    private void parseInsenseToScreen(DefaultStyledDocument doc, String lineIn)
    {
        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);

        if (1 < lineIn.length())
        {
            String keyWords[] = lineIn.split("\\s+");
            for (int i = 0; i < keyWords.length; i++)
            {
                keyWords[i].replaceAll("\\s+", "");
                if (!"".equals(keyWords[i]) && (Grammar.findWord(keyWords[i])))
                {
                    if (0 == i)
                    {
                        addToDocument(doc, attr, tabs + keyWords[i] + " ");
                    }
                    else
                    {
                        addToDocument(doc, attr, keyWords[i] + " ");
                    }
                }
                else
                {
                    if (0 == i)
                    {
                        addToDocument(doc, attrBlack, tabs + keyWords[i] + " ");
                    }
                    else
                    {
                        addToDocument(doc, attrBlack, keyWords[i] + " ");
                    }
                }
            }
            addToDocument(doc, attrBlack, "\n");
        }
        else if (!"".equals(lineIn))
        {
            if ("{".equals(lineIn))
            {
                ++count;
                addToDocument(doc, attrBlack, tabs + lineIn + " ");
                for (int j = 0; j < count; j++)
                {
                    tabs += "    ";
                }
            }
            else if ("}".equals(lineIn))
            {
                --count;
                tabs = "";
                for (int j = 0; j < count; j++)
                {
                    tabs += "    ";
                }                
                addToDocument(doc, attrBlack, tabs + lineIn + " ");                                      
            }
            else
            {
                addToDocument(doc, attrBlack, lineIn + " ");
            }

            addToDocument(doc, attrBlack, "\n");
        }
    }
    
    private void addToDocument(DefaultStyledDocument doc, final AttributeSet attr, String keyWords)
    {
        try
        {
            doc.insertString(doc.getLength(), keyWords, attr);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }
    }
}
