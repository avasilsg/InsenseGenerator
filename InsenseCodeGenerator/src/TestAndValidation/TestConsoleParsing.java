package TestAndValidation;

import GUI.MainWindow;
import Generator.XmlParser;

public class TestConsoleParsing
{
    
    public static void main(String[] args)
    {
        MainWindow window = new MainWindow();
        
        XmlParser parser = new XmlParser("/Temp/example.xml");
        parser.parseXML();
    }
    
}
