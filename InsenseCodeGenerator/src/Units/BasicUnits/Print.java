package Units.BasicUnits;

//<print variable = "reading" attribute = "solar" titleString = "Solar"/>
public class Print
{
    private String variable;
    private String type;
    private String attribute;
    
    public Print()
    {
        this.setAttribute(null);
        this.setType(null);
        this.setVariable(null);
    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getVariable()
    {
        return variable;
    }

    public void setVariable(String variable)
    {
        this.variable = variable;
    }
}
