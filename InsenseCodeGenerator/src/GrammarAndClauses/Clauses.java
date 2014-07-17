package GrammarAndClauses;

public class Clauses
{
    public static final String openBracket = "(";
    public static final String closeBracket = ")";
    public static final String openCurlyBracket = "{";
    public static final String closeCurlyBracket = "}";
    public static final String comma = ",";
    //       type ITempReader is interface 
//  (
//      out bool tRequestChan ;
//      in real tValueChan ;
//      out any stdoutChan
//  )

   // type NAME is interface 
   public static final String interfaceGrammar = "type %s is interface";
   // direction type name
   public static final String channel = "%s %s %s;";
   
   
   public static final String component =   "component %s presents ";
   public static final String constructor = "constructor";
   public static final String behaviour =   "behaviour";

   public static final String send = "send %s on %s";
   public static final String sendWithValue = "send %s(%s) on %s";
   public static final String receive = "receive %s from %s";
   
   public static final String createInstance = "%s %s = new %s";

   public static final String connectShort = "connect %s to %s";
   public static final String connectExFrom = "connect %s.%s to %s";
   public static final String connectExTo = "connect %s to %s.%s";
   public static final String connectFull = "connect %s.%s to %s.%s";


}

