package Units.BasicUnits;

public class Send extends Operator
{
    private String inderntifier;
    private String value;
    private String on;
    
    public Send()
    {
        this.setInderntifier(null);
        this.setOn(null);
    }

    public String getInderntifier()
    {
        return inderntifier;
    }

    public void setInderntifier(String inderntifier)
    {
        this.inderntifier = inderntifier;
    }

    public String getOn()
    {
        return on;
    }

    public void setOn(String on)
    {
        this.on = on;
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
