package Units;

import java.util.LinkedList;

import Units.BasicUnits.Channel;

public class Interface
{
    private String name;
    private LinkedList<Channel> channels;
    
    public Interface()
    {
        this.name = null;
        this.channels = new LinkedList<Channel>();
    }
    
    public LinkedList<Channel> getChannels()
    {
        return channels;
    }
    public void setChannels(LinkedList<Channel> channels)
    {
        this.channels = channels;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setChannel(Channel chan)
    {
        if (null != chan)
        {
            this.channels.add(chan);
        }
    }
}
