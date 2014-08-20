package Units;

public class InsenseNode
{
    private String nodeName;
    private int nodeOrderNumber;
    private int totalNodeNumbers;
    private String connectToNode;
    private String type;
    private String direction;
    
    public InsenseNode()
    {
        this.connectToNode = null;
        this.type = null;
        this.nodeOrderNumber = 0;
        this.totalNodeNumbers = 0;
        this.nodeName = null;
        
    }
    
    public String getNodeName ( )
    {
        return nodeName;
    }
    public void setNodeName ( String nodeName )
    {
        this.nodeName = nodeName;
    }
    public int getNodeOrderNumber ( )
    {
        return nodeOrderNumber;
    }
    public void setNodeOrderNumber ( int nodeOrderNumber )
    {
        this.nodeOrderNumber = nodeOrderNumber;
    }
    public int getTotalNodeNumbers ( )
    {
        return totalNodeNumbers;
    }
    public void setTotalNodeNumbers ( int totalNodeNumbers )
    {
        this.totalNodeNumbers = totalNodeNumbers;
    }
    public String getConnectToNode ( )
    {
        return connectToNode;
    }
    public void setConnectToNode ( String connectToNode )
    {
        this.connectToNode = connectToNode;
    }
    public String getInstance ( )
    {
        return type;
    }
    public void setInstance ( String type )
    {
        this.type = type;
    }

    public String getDirection ( )
    {
        return direction;
    }

    public void setDirection ( String direction )
    {
        this.direction = direction;
    }
}
