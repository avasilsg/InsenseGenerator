package ReverseEngineeringGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import AbstractGenerator.Writer;

public class XMLWriter extends Writer
{
    private BufferedReader     reader;

    public XMLWriter(final String filePath)
    {
        if (null != filePath)
        {
            try
            {
                reader = new BufferedReader(new FileReader(filePath));
                super.openAndCreateFile ( ".xml" );
                
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void readTheFile()
    {
            String line ="";
            while (line != null) 
            {
                try
                {
                    line = reader.readLine();
                    String array[] = line.split("\\s+");
                    
                }
                catch (IOException e)
                {
                }
            }        
    }
    
}
