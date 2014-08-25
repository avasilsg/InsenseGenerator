package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Constructor extends Body
{
    private LinkedList<Field> parameters;

    public LinkedList<Field> getParameters()
    {
        return parameters;
    }

    public void setParameters(LinkedList<Field> parameters)
    {
        this.parameters = parameters;
    }
}
