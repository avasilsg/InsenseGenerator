package Units.BasicUnits;

import java.util.LinkedList;

public class Instance
{
    private String type;
    private String name;
    private LinkedList<String> param;
    
    public Instance()
    {
        this.setName(null);
        this.setType(null);
        this.setParam(new LinkedList<String>());
    }

    public LinkedList<String> getParam()
    {
        return param;
    }

    public void setParam(LinkedList<String> param)
    {
        this.param = param;
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
}
