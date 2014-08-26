using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XMLParsing
{
    class CodeGenerator
    {
        private string fileName;
        private StreamWriter writer;
        private LinkedList<String> componentContainer;

        public CodeGenerator(String fileName)
        {
            if (null == fileName)
            {
                throw new NullReferenceException();
            }
            this.fileName = fileName;
            this.writer = null;
            this.componentContainer = new LinkedList<string>();
        }

        public void openAndCreateTheFile()
        {
            this.writer = new StreamWriter(fileName);
            if (null == writer)
            {
                throw new NullReferenceException();
            }
        }

        #region writeInterfaces
        public void writeInterface(String interfaceName, LinkedList<String> interfaceContent)
        {
            writer.Write(Environment.NewLine);
            writer.Write(Environment.NewLine);
            writer.Write(String.Format(Grammar.interfaceGrammar, interfaceName));
            writer.Write(Environment.NewLine);
            writer.Write(Grammar.openBracket);
            writer.Write(Environment.NewLine);
            foreach (String getChannel in interfaceContent)
            {
                String[] content = getChannel.Split(',');
                if (3 == content.Length)
                {
                    writer.Write("\t" + String.Format(Grammar.channel, content[0], content[1], content[2]));
                    writer.Write(Environment.NewLine);
                }
            }
            writer.Write(Grammar.closeBracket);
        }
        #endregion

        #region writeComponent
        public void writeComponent(Component component)
        {
            if (null == component.name)
            {
                throw new NullReferenceException();
            }
            writer.Write(Environment.NewLine);

            int count = 0;
            if (null != component.name)
            {
                this.componentContainer.AddLast(component.name);
            }
            string compTitle = String.Format(Grammar.component, component.name);
            foreach (String str in component.presentedInterfaces)
            {
                if (0 < count)
                {
                    compTitle = compTitle + Grammar.comma;
                }
                compTitle = compTitle + str;
                ++count;     
            }
            writer.Write(compTitle);
            writer.Write(Environment.NewLine);
            writer.Write(Grammar.openCurlyBracket);
            writer.Write(Environment.NewLine);
            if (0 < component.fields.Count())
            {
                foreach (String str in component.fields)
                {
                    writer.Write("\t" + str);
                    writer.Write(Environment.NewLine);
                }
            }
            writer.Write(Environment.NewLine);
            writer.Write("\t" + Grammar.constructor);
            writer.Write(Grammar.openBracket);
            writer.Write(Grammar.closeBracket);
            writer.Write(Environment.NewLine);
            writer.Write("\t" + Grammar.openCurlyBracket);
            writer.Write(Environment.NewLine);
            writer.Write("\t" + Grammar.closeCurlyBracket);
            writer.Write(Environment.NewLine);
            writer.Write("\t" + Grammar.behaviour);
            writer.Write(Grammar.openCurlyBracket);
            foreach (String str in component.behaviourContent)
            {
                String[] array = str.Split(',');
                if (true == "send".Equals(array[0]))
                {
                    writer.Write(Environment.NewLine);
                    writer.Write("\t\t" + String.Format(Grammar.send, array[1], array[2]));
                }
                
                if (true == "receive".Equals(array[0]))
                {
                    writer.Write(Environment.NewLine);
                    writer.Write("\t\t" + String.Format(Grammar.receive, array[1], array[2]));
                }

                if (true == "print".Equals(array[0]))
                {
                    writer.Write(Environment.NewLine);
                    if (3 == array.Length)
                    {
                       // writer.Write("\t" 
                    }
                }

                if (true == "variable".Equals(array[0]))
                {
                    writer.Write(Environment.NewLine);
                    foreach (String field in component.fields)
                    {
                        string[] fields = field.Split(',');
                        if (2 < array.Length)
                        {
                            continue;
                        }
                        writer.Write(Environment.NewLine);
                        writer.Write("\t" + String.Format(Grammar.field, field[0], field[1]));
                    }
                }
            }
            writer.Write(Environment.NewLine);
            writer.Write("\t"+Grammar.closeCurlyBracket);
            writer.Write(Environment.NewLine);
            writer.Write(Grammar.closeCurlyBracket);
        }
        #endregion

        #region ParseConnections
        public void writeConnections(LinkedList<String> connections)
        {
            writer.Write(Environment.NewLine);
            foreach (String str in componentContainer)
            {
                writer.Write(Grammar.createInstance, String.Format(Char.ToLowerInvariant(str[0]) + str.Substring(1)), str + Grammar.openBracket + Grammar.closeBracket);
                writer.Write(Environment.NewLine);
            }

            foreach (String str in connections)
            {
                string[] array = str.Split(',');
                writer.Write(Environment.NewLine);
                writer.Write(Grammar.connect, array[0], array[1]);
            }
        }
        #endregion


        #region ParseStruct
        public void writeStruct(Structure structure)
        {
            if (null == structure.name)
            {
                throw new NullReferenceException();
            }
            writer.Write(Environment.NewLine);
            writer.Write(String.Format(Grammar.structSyntax, structure.name));
            writer.Write(Grammar.openBracket);
            foreach (String str in structure.fields)
            {
                string[] array = str.Split(',');
                if (2 < array.Length)
                {
                    continue;
                }
                writer.Write(Environment.NewLine);
                writer.Write("\t" + String.Format(Grammar.field, array[0],array[1]));
            }
            writer.Write(Grammar.closeBracket);
            writer.Write(Environment.NewLine);
        }
        #endregion

        public void closeFile()
        {
            this.writer.Close();
        }
    }
}
