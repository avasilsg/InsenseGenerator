package Units;

import Units.BasicUnits.Operator;
import Units.BasicUnits.Print;

public class Template
{
    private String expression;
    private String type;
    private String packetKey;
    private String packetValue;
    private Print  print;
    private Operator operator;
    public String getExpression ( )
    {
        return expression;
    }
    public void setExpression ( String expression )
    {
        this.expression = expression;
    }
    public String getType ( )
    {
        return type;
    }
    public void setType ( String type )
    {
        this.type = type;
    }
    public Print getPrint ( )
    {
        return print;
    }
    public void setPrint ( Print print )
    {
        this.print = print;
    }
    public String getPacketValue ( )
    {
        return packetValue;
    }
    public void setPacketValue ( String packetValue )
    {
        this.packetValue = packetValue;
    }
    public String getPacketKey ( )
    {
        return packetKey;
    }
    public void setPacketKey ( String packetKey )
    {
        this.packetKey = packetKey;
    }
    public Operator getOperator ( )
    {
        return operator;
    }
    public void setObject ( Operator operator )
    {
        this.operator = operator;
    }
}
