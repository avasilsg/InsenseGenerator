package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Procedure
{
    private String name;
    private String type;
    private Behaviour body;
    private LinkedList<Field> parameters;
    
    public Procedure()
    {
        name = null;
        type = null;
        parameters = new LinkedList<>();
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public LinkedList<Field> getParameters()
    {
        return parameters;
    }
    public void setParameters(LinkedList<Field> parameters)
    {
        this.parameters = parameters;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public Behaviour getBody()
    {
        return body;
    }

    public void setBody(Behaviour body)
    {
        this.body = body;
    }
     
}
