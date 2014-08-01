package Units.BasicUnits;

//<variable name = "printOutput" new = "true"/>
//<variable type = "" name = "photoValue" new = "true" bindingTo =
//"photoReading()"/>
public class Variable
{
    private String  name;
    private boolean newOp;
    private String type;
    private String bindingTo;
    
    public Variable()
    {
        name = null;
        newOp = false;
        type = null;
        bindingTo = null;        
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public boolean isNewOp()
    {
        return newOp;
    }
    public void setNewOp(boolean newOp)
    {
        this.newOp = newOp;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public String getBindingTo()
    {
        return bindingTo;
    }
    public void setBindingTo(String bindingTo)
    {
        this.bindingTo = bindingTo;
    }
}
