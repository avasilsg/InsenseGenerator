package Units;

import java.util.LinkedList;

import Units.BasicUnits.Print;
import Units.BasicUnits.Receive;
import Units.BasicUnits.Send;
import Units.BasicUnits.Variable;

public class Behaviour
{
    private LinkedList<Send> sends;
    private LinkedList<Receive> receives;
    private LinkedList<Variable> variables;
    private LinkedList<Print> prints;
    private LinkedList<String> order;
    private LinkedList<String> returnStatements;
    
    public Behaviour()
    {
        this.setSends(new LinkedList<Send>());
        this.setReceives(new LinkedList<Receive>());
        this.setVariables(new LinkedList<Variable>());
        this.setPrints(new LinkedList<Print>());
        this.setOrder(new LinkedList<String>());
        this.setReturnStatements ( new LinkedList<String>()  );
    }

    public LinkedList<Send> getSends()
    {
        return sends;
    }

    public void setSends(LinkedList<Send> sends)
    {
        this.sends = sends;
    }

    public LinkedList<Receive> getReceives()
    {
        return receives;
    }

    public void setReceives(LinkedList<Receive> receives)
    {
        this.receives = receives;
    }

    public LinkedList<Variable> getVariables()
    {
        return variables;
    }

    public void setVariables(LinkedList<Variable> variables)
    {
        this.variables = variables;
    }

    public LinkedList<Print> getPrints()
    {
        return prints;
    }

    public void setPrints(LinkedList<Print> prints)
    {
        this.prints = prints;
    }
    
    public void setSend(Send send)
    {
        if (null != send)
            this.sends.add(send);
    }
    
    public void setReceive(Receive receive)
    {
        if (null != receive)
            this.receives.add(receive);
    }
    
    public void setVariable(Variable variable)
    {
        if (null != variable)
            this.variables.add(variable);
    }
    
    public void setPrint(Print print)
    {
        if (null != print)
            this.prints.add(print);
    }

    public LinkedList<String> getOrder()
    {
        return order;
    }

    public void setOrder(LinkedList<String> order)
    {
        this.order = order;
    }
    
    public void setOperation(String operation)
    {
        this.order.add(operation);
    }

    public LinkedList<String> getReturnStatements ( )
    {
        return returnStatements;
    }

    public void setReturnStatements ( LinkedList<String> returnStatements )
    {
        this.returnStatements = returnStatements;
    }
}
