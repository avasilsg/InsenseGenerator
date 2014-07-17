package Units;

import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Component
{
    private String name;
    private LinkedList<String> presents;
    private LinkedList<Field> fields;
    private LinkedList<Constructor> constructors;
    private LinkedList<Procedure> procedures;
    private Behaviour behaviour;
    
    Component()
    {
        this.setName(null);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LinkedList<String> getPresents()
    {
        return presents;
    }

    public void setPresents(LinkedList<String> presents)
    {
        this.presents = presents;
    }

    public LinkedList<Field> getFields()
    {
        return fields;
    }

    public void setFields(LinkedList<Field> fields)
    {
        this.fields = fields;
    }

    public LinkedList<Constructor> getConstructors()
    {
        return constructors;
    }

    public void setConstructors(LinkedList<Constructor> constructors)
    {
        this.constructors = constructors;
    }

    public Behaviour getBehaviour()
    {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour)
    {
        this.behaviour = behaviour;
    }

    public LinkedList<Procedure> getProcedures()
    {
        return procedures;
    }

    public void setProcedures(LinkedList<Procedure> procedures)
    {
        this.procedures = procedures;
    }
}
