using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLParsing
{
    class Structure
    {
        public String name;
        public LinkedList<String> fields;

        public Structure()
        {
            name = null;
            fields = new LinkedList<string>();
        }
    }
}
