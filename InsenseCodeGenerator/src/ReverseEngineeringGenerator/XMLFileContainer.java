package ReverseEngineeringGenerator;

public class XMLFileContainer
{
    private String fileName;
    private String filePath;
    
    public XMLFileContainer()
    {
        this.fileName = null;
        this.filePath = null;
    }
    
    public String getFileName ( )
    {
        return fileName;
    }
    public void setFileName ( String fileName )
    {
        this.fileName = fileName;
    }
    public String getFilePath ( )
    {
        return filePath;
    }
    public void setFilePath ( String filePath )
    {
        this.filePath = filePath;
    }
    
}
