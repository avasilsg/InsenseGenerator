using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLParsing
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter the file:");
            string filePath = Console.ReadLine();
            Console.WriteLine("Enter file name:");
            string fileName = Console.ReadLine();
            XMLReader reader = new XMLReader(@filePath, fileName);
            reader.parseXML();
        }
    }
}
