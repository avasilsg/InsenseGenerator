package Units.BasicUnits;

public class Field
{
    private String type;
    private String name;
    private String value;
    
    public Field()
    {
        this.name = null;
        this.type = null;
        this.value = null;
    }
    
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}

