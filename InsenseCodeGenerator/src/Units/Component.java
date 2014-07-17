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
    
    public Component()
    {
        this.setName(null);
        this.behaviour = new Behaviour();
        this.constructors = new LinkedList<>();
        this.fields = new LinkedList<>();
        this.presents = new LinkedList<>();
        this.procedures = new LinkedList<>();
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

    public void setPresent(String present)
    {
        if (null != present)
        {
            presents.add(present);
        }
    }
    
    public void setField(Field field)
    {
        if (null != field)
        {
            fields.add(field);
        }
    }
    
    public void setContructor(Constructor constructor)
    {
        if (null != constructor)
        {
            constructors.add(constructor);
        }
    }
    
    public void setProcedure(Procedure procedure)
    {
        if (null != procedure)
        {
            procedures.add(procedure);
        }
    }
}
