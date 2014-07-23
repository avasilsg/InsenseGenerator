package Units.BasicUnits;

import java.util.InputMismatchException;

public class Channel
{
    private final String directionIn = "in";
    private final String directionOut = "out";
    
    private String type;
    private String name;
    private String direction;
    private boolean isArray;
    public Channel()
    {
        type = null;
        name = null;
        direction = null;
        setArray(false);
    }
    
    public String getDirection()
    {
        return direction;
    }
    public void setDirection(String value)
    {
        this.direction = value;
        validateDirection();
    }
    private void validateDirection()
    {
        if (!(this.direction.equals(directionIn) || this.direction.equals(directionOut)))
        {
            throw new InputMismatchException("There is invalid type");
        }
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isArray()
    {
        return isArray;
    }

    public void setArray(boolean isArray)
    {
        this.isArray = isArray;
    }
    
    
    
}
