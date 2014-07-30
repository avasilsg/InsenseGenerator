package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Constructor
{
    private Behaviour body;
    private LinkedList<Field> parameters;

    public Behaviour getBody()
    {
        return body;
    }

    public void setBody(Behaviour body)
    {
        this.body = body;
    }

    public LinkedList<Field> getParameters()
    {
        return parameters;
    }

    public void setParameters(LinkedList<Field> parameters)
    {
        this.parameters = parameters;
    }
}
