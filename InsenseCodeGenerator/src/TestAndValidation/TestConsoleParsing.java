package TestAndValidation;

import Generator.XmlParser;

public class TestConsoleParsing
{
    
    public static void main(String[] args)
    {
        XmlParser parser = new XmlParser("/Temp/example2.xml");
        parser.parseXML();
    }
    
}
