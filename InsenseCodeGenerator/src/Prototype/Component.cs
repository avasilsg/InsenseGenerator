using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLParsing
{
    class Component
    {
        public String name;
        public LinkedList<String> presentedInterfaces;
        //multiple contructors
        public LinkedList<String> constructorContent;
        public LinkedList<String> fields;
        public LinkedList<String> behaviourContent;
        public LinkedList<String> variables;
        public LinkedList<String> printContent;
        public short numberOfInstances;
// precodures
        public Component()
        {
            this.name = null;
            this.presentedInterfaces = new LinkedList<string>();
            this.constructorContent = new LinkedList<string>();
            this.fields = new LinkedList<string>();
            this.behaviourContent = new LinkedList<string>();
            this.printContent = new LinkedList<string>();
            this.variables = new LinkedList<string>();
            this.numberOfInstances = 1;
        }
    }
}
