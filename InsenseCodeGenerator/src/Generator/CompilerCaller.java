package Generator;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CompilerCaller
{
    private String insenseFileName;
    private String insenseFilePath;
    private String compilationMessage;
    private String insenseCompiler;
    
    public CompilerCaller ()
    {
        this.insenseFileName = null;
        this.insenseFilePath = null;
        this.compilationMessage = null;
        this.insenseCompiler = null;
    }
    
    public void compileByVersion(String type)
    {
        switch(type)
        {
            case "Compile for Contiki":
            {
                compileByInsenseCompilerContiki();
                break;
            }
            case "Compile for UnixOS":
            {
                compileByUnixCompiler();
                break;
            }
            case "Compile for InsenseOS":
            {
                compileByInsenseOSCompiler();
                break;
            }
        }
    }
    
    private void compileByInsenseOSCompiler ( )
    {
        insenseCompiler = "InsenseCompilerInceOS.jar";
        compileFile ( );
    }
    
    private void compileByInsenseCompilerContiki ( )
    {
        insenseCompiler = "InsenseCompilerContiki.jar";
        compileFile ( );
    }
    
    private void compileByUnixCompiler ( )
    {
        insenseCompiler = "InsenseCompilerUnix.jar";
        compileFile ( );
    }
    
    private void compileFile ( )
    {
        try
        {
            if ( null == this.insenseFilePath
                    || "".equalsIgnoreCase ( insenseFilePath ) )
            {
                JOptionPane.showMessageDialog ( null,
                        "Generate insense file first!" );
            }
            else
            {
                String path = new File ( "" ).getAbsolutePath ( );
                File dir = new File ( path + "/" + "solution" );
                dir.mkdir ( );
                
                String executable = String.format ( dir.getAbsolutePath ( )
                        + "%s", this.insenseFileName );
                Runtime.getRuntime ( )
                        .exec ( String
                                .format (
                                        "java -jar /home/stephan/git/InsenseGenerator/InsenseCodeGenerator/src/GUI/%s %s %s",
                                        insenseCompiler, insenseFilePath,
                                        executable ) );
            }
        }
        catch (IOException e)
        {
            e.printStackTrace ( );
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
