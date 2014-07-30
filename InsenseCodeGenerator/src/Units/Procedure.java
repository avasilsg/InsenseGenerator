package Units;

import java.beans.Expression;
import java.util.LinkedList;

import Units.BasicUnits.Field;

public class Procedure
{
    private String name;
    private String type;
    private LinkedList<Field> parameters;
    private LinkedList<Expression> expressions;
    private String returnStatement;
    public Procedure()
    {
        name = null;
        type = null;
        parameters = new LinkedList<>();
        expressions = new LinkedList<>();
        setReturnStatement(null);
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public LinkedList<Field> getParameters()
    {
        return parameters;
    }
    public void setParameters(LinkedList<Field> parameters)
    {
        this.parameters = parameters;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public LinkedList<Expression> getExpressions()
    {
        return expressions;
    }
    public void setExpressions(LinkedList<Expression> expressions)
    {
        this.expressions = expressions;
    }

    public String getReturnStatement()
    {
        return returnStatement;
    }

    public void setReturnStatement(String returnStatement)
    {
        this.returnStatement = returnStatement;
    }
     
}
