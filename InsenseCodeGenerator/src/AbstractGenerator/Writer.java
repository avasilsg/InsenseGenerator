package AbstractGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class Writer
{
    protected PrintWriter writer;
    protected File        dir;
    private   String      extention;
    protected Writer ()
    {
        this.dir = null;
        this.writer = null;
    }
    
    public void openAndCreateFile(String fileFormat)
    {
        extention = fileFormat;
        try
        {
            openAndCreateInsenseFile();
        }
        catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void openAndCreateInsenseFile ( ) throws FileNotFoundException,
            UnsupportedEncodingException
    {
        String path = new File ( "" ).getAbsolutePath ( );
        dir = new File ( path + "/" + "temp" );
        dir.mkdir ( );
        writer = new PrintWriter ( dir.getAbsoluteFile ( ) + "/" + "temp"
                + extention, "UTF-8" );
    }
}
