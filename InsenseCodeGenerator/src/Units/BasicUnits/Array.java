package Units.BasicUnits;

public class Array extends Operator
{
    private String elementType;
    private int size;
    private int defaultValue;
    
    Array()
    {
        this.defaultValue = 0;
        this.size = 0;
        this.elementType = null;
    }

    public int getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(int defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getElementType()
    {
        return elementType;
    }

    public void setElementType(String elementType)
    {
        this.elementType = elementType;
    }
}
