package Units;

import java.rmi.UnexpectedException;
import java.util.LinkedList;

import Units.BasicUnits.Operator;

public abstract class Body
{
    private LinkedList<Operator> operators;
    
    public Body()
    {
        this.setOperators(new LinkedList<Operator>());
    }
    
    public LinkedList<Operator> getOperators()
    {
        return operators;
    }
    
    public void setOperators(LinkedList<Operator> operators)
    {
        this.operators = operators;
    }
    
    private Operator getOperator()
    {
        Operator operator = this.operators.get(0);
        this.operators.remove(0);
        return operator;
    }
    public boolean isOperationsEmpty()
    {
        return this.operators.isEmpty();
    }
    public void insertOperator(Operator operator)
    {
        if (operator != null)
        {
            this.operators.add(operator);
        }
    }
    public Operator getCurrentOperator() throws UnexpectedException
    {
        if (null == this.operators || true == this.operators.isEmpty())
        {
          throw new UnexpectedException("Operator list is empty!");
        }
        return this.getOperator();
    }
}
