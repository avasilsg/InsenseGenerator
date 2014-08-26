using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace InsenseGenerator
{
    class Interface
    {
        public LinkedList<String> channels;
        public String name;

        public Interface()
        {
            this.name = null;
            this.channels = new LinkedList<string>();
        }
    }
}
