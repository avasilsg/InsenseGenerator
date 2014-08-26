using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLParsing
{
    class Grammar
    {
        #region GeneralTags

        public static string openBracket = "(";
        public static string closeBracket = ")";
        public static string openCurlyBracket = "{";
        public static string closeCurlyBracket = "}";
        public static string comma = ",";
        public static string createInstance = "{0} = new {1}";
        public static string assignmentOperator = "{0} = {1}";
        public static string field = "{0} {1};";

        #endregion

        #region Print

        public static string printString = "printString";
        public static string printReal  =  "printReal";
        public static string printInt   =  "printInt";

        #endregion

        #region Struct

        public static string structSyntax = "type {0} is struct";

        #endregion

        #region Interface
        //       type ITempReader is interface 
//       (
//           out bool tRequestChan ;
//           in real tValueChan ;
//           out any stdoutChan
//       )

        // type NAME is interface 
        public static string interfaceGrammar = "type {0} is interface";
        // direction type name
        public static string channel = "{0} {1} {2};";

        #endregion 

//component TempReader presents ITempReader {
 
//    constructor() {
//    }
  
//    behaviour {
//        send true on tRequestChan
//        receive temp from tValueChan
//        send any("\nTemp = ") on stdoutChan
//        send any(temp) on stdoutChan
//    }
//} 

        #region Component
       
        public static string component =   "component {0} presents ";
        public static string constructor = "constructor";
        public static string behaviour =   "behaviour";

        public static string send = "send {0} on {1}";
        public static string receive = "receive {0} from {1}";

        #endregion 

        #region Connect
        //        connect tr.tRequestChan to lightHumidTempSensor.tempRequest
        //        connect tr.tValueChan to lightHumidTempSensor.tempOutput
        //        connect tr.stdoutChan to standardOut 
        public static string connect = "connect {0} to {1}";

        #endregion 
    }
}
