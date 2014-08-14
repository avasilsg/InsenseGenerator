package Generator;

import java.io.File;
import java.io.IOException;

public class CompilerCaller
{
    private String insenseFileName;
    private String insenseFilePath;
    private String compilationMessage;
    
    public CompilerCaller()
    {
        this.insenseFileName = null;
        this.insenseFilePath = null;
        this.compilationMessage = null;
    }
    
    public void compileByUnixCompiler()
    {
        try
        {
            if (null == this.insenseFilePath || "".equalsIgnoreCase ( insenseFilePath ))
            {
                
            }
            else
            {
                String path = new File("").getAbsolutePath ( );
                File dir = new File(path + "/" + "solution");
                dir.mkdir ( );
                String insenseFile = String.format ("/home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/IntermediateNotation/%s", this.insenseFilePath);
                String executable = String.format  (dir.getAbsolutePath ( ) + "%s", this.insenseFileName);
                Runtime.getRuntime().exec(
                        String.format("java -jar /home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/GUI/InsenseCompilerUnix.jar %s %s",
                                insenseFile, executable));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getInsenseFileName ( )
    {
        return insenseFileName;
    }
    public void setInsenseFileName ( String insenseFileName )
    {
        this.insenseFileName = insenseFileName;
    }
    public String getInsenseFilePath ( )
    {
        return insenseFilePath;
    }
    public void setInsenseFilePath ( String insenseFilePath )
    {
        this.insenseFilePath = insenseFilePath;
    }
    public String getCompilationMessage ( )
    {
        return compilationMessage;
    }
    public void setCompilationMessage ( String compilationMessage )
    {
        this.compilationMessage = compilationMessage;
    }
}
