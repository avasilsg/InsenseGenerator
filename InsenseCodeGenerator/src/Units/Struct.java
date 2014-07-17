package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Struct
{
    private String name;
    private LinkedList<Field> fields;

    public Struct()
    {
        setName(null);
        setFields(new LinkedList<Field>());
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LinkedList<Field> getFields()
    {
        return fields;
    }

    public void setFields(LinkedList<Field> fields)
    {
        this.fields = fields;
    }
}
