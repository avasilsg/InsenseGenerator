package Units;

public class Behaviour extends Body
{
//    private LinkedList<Send> sends;
//    private LinkedList<Receive> receives;
//    private LinkedList<Variable> variables;
//    private LinkedList<Print> prints;
    private Template template;
    
    public Behaviour()
    {
    }
    
    public boolean isTemplateEmpty()
    {
        return null == template;
    }
    
    public Template getTemplate ( )
    {
        return template;
    }

    public void setTemplate ( Template template )
    {
        this.template = template;
    }
}
