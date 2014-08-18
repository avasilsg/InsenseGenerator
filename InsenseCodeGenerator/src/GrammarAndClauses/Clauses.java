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
   public static final String arrayChannel = "%s %s[] %s;";

     
   public static final String component =   "component %s presents ";
   public static final String constructor = "constructor ";
   public static final String behaviour =   "behaviour ";

   public static final String send = "send %s on %s";
   public static final String sendWithValue = "send %s ( %s ) on %s";
   public static final String receive = "receive %s from %s";
   
   public static final String createInstance = "%s = new %s";

   public static final String connectShort = "connect %s to %s";
   public static final String connectExFrom = "connect %s.%s to %s";
   public static final String connectExTo = "connect %s to %s.%s";
   public static final String connectFull = "connect %s.%s to %s.%s";

   public static final String fieldFull = "%s %s = %s";
   public static final String fieldTypeLess = "%s = %s";
   public static final String fieldShort = "%s"; 
   public static final String fieldEmpty = "%s %s;";   
   public static final String variableNew = "%s = new %s";
   public static final String variableNotNew = "%s = %s";

   public static final String printString = "printString%s";
   public static final String printReal  =  "printReal%s";
   public static final String printInt   =  "printInt%s";
   public static final String printUnsignedInt = "printUnsignedInt%s";
//   ints = new integer[5] of 0
   public static final String arraySyntax = "%s = new %s[%d] of %d";
   public static final String structSyntax = "type %s is struct ";
//   proc tempIntToCelsiusReal(integer reading) : real {
   public static final String procedureSyntax = "proc %s ( ";
   public static final String closeProcedureDeclaration =  " ) : %s";

   //Templates || Send
   {
       {
//           public static String interfaceRadio = interfaceGrammar + "\n" +
//                   openBracket + channel + 
//           type IRadioOutChannel is interface ( in integer input ; out RadioPacket unicast )
//
//           component RadioOutChannel presents IRadioOutChannel {
//
//              constructor(){
//              }
//
//              behaviour {
//                 receive value from input
//                 packet = new RadioPacket(NodeB, any(value))
//                 send packet on unicast
//              }
//           }


       }
       {
           
       }
   }
   //Templates || Receive
   {
       {
           
       }
       {
           
       }
   }
}

