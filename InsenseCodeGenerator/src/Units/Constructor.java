package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Constructor
{
    private Body body;
    private LinkedList<Field> parameters;

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
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
