package Units;

import java.beans.Expression;
import java.util.LinkedList;

public class Body
{
    private LinkedList<Expression> expressions;

    public Body()
    {
        expressions = new LinkedList<>();
    }
    
    public LinkedList<Expression> getExpressions()
    {
        return expressions;
    }
    public void setExpressions(LinkedList<Expression> expressions)
    {
        this.expressions = expressions;
    }

}
