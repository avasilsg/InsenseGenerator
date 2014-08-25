package Units.BasicUnits;

public class Return extends Operator
{
    private String expression;
    
    public Return()
    {
      this.setExpression(null);
    }

    public String getExpression()
    {
        return expression;
    }

    public void setExpression(String expression)
    {
        this.expression = expression;
    }
    
}
