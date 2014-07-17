package Units;

//tr = new TempReader()
//connect tr.tRequestChan to lightHumidTempSensor.tempRequest
//connect tr.tValueChan to lightHumidTempSensor.tempOutput
//connect tr.stdoutChan to standardOut
//<connect >
//<from name = "TempReader"             on = "tRequestChan"/>
//<to   name = "lightHumidTempSensor" on = "tempRequest"/>
//<from name = "TempReader"         on = "tValueChan" />
//<to   name = "lightHumidTempSensor" on = "tempOutput"/>
//<from name = "TempReader"             on = "stdoutChan" />
//<to   name = "standardOut"            on = ""/> 
//</connect> 
public class Connect
{
    private String fromName;
    private String fromNameOn;
    private String toName;
    private String toNameOn;
    
    public Connect()
    {
        setFromName(null);
        setFromNameOn(null);
        setToName(null);
        setToNameOn(null);
    }

    public String getFromNameOn()
    {
        return fromNameOn;
    }

    public void setFromNameOn(String fromNameOn)
    {
        this.fromNameOn = fromNameOn;
    }

    public String getFromName()
    {
        return fromName;
    }

    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }

    public String getToName()
    {
        return toName;
    }

    public void setToName(String toName)
    {
        this.toName = toName;
    }

    public String getToNameOn()
    {
        return toNameOn;
    }

    public void setToNameOn(String toNameOn)
    {
        this.toNameOn = toNameOn;
    }
}
