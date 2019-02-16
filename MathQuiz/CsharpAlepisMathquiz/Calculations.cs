using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CsharpAlepisMathquiz
{
    class Calculations
    {
        static char[] calcSymbols = { '+', '-', '*', '/' };
       
        string strToBeCalculated;
        public string Calculate
        {
            get { return strToBeCalculated; }
            set { if (value != "")
                {
                    strToBeCalculated = value;
                }
            }
        }
        //oi arithmoi kai ta sumvola pairniountai se mia lista gia na ta kanoume handle eukolotera
        public float ParseAndCalculate(string strToBeCalculated)
        {
            List<string> numbers = new List<string>();
            Dictionary<char, int> symbols = new Dictionary<char, int>();
            float number;
            string str = "";
            foreach(char character in strToBeCalculated)
            {
                if(character != '+' && character != '-' && character != '*' && character != '/')
                {
                    str += character;
                }
                else {
                    if (symbols.ContainsKey(character))
                    {
                        symbols[character] += 1;
                    }
                    else { symbols.Add(character, 1); }
                    numbers.Add(str);
                    str = "";
                    numbers.Add(character.ToString());
                }
            }
            //add ton teleutaio oro
            numbers.Add(str);
            bool flag = true;
            //an uparxei * 
            if (symbols.ContainsKey('*') || symbols.ContainsKey('/')) { flag = false; }
            while (numbers.Count != 1)
            {
                //deiktis tis listas
                int i = 0;
                //  foreach(string element in numbers)
                int boundary = numbers.Count;
                    while (i < boundary) 
                {


                    //elegxos an uparxoun prwta pollaplasiasmoi/diaireseis an uparxoun ginontai prwta autes
                    

                    if (symbols.ContainsKey(numbers[i][0]) && (numbers[i][0] == '*' || numbers[i][0] == '/'))
                    {
                        
                        switch (numbers[i][0])
                        {
                            //kanw praxeis anamesa ston epomeno kai ton proigoumeno deikth ths listas otan o twrinos deikths  (element) einai eite + eite - eite * eite /
                            //kai meta stin thesi tou index - 1 bazw ton arithmo pou upolgisa enw afairw apo tin lista ton index kai index + 1 apo tin lista 
                            case '*':
                                
                                number = float.Parse(numbers[i - 1]) * float.Parse(numbers[i + 1]);
                                numbers[i - 1] = number.ToString();
                                numbers.RemoveAt(i+1);
                                numbers.RemoveAt(i);
                                i = i - 1;
                                boundary -= 2;
                                symbols['*'] -= 1;
                                //o deikths kathe fora tha sunexizei apo ton deikth pou exei h teleutaia timh pou ekxwrisame stin lista
                                break;
                            case '/':
                                number = float.Parse(numbers[i - 1]) / float.Parse(numbers[i + 1]);
                                numbers[i - 1] = number.ToString();
                                numbers.RemoveAt(i + 1);
                                numbers.RemoveAt(i);
                                i = i - 1;
                                boundary -= 2;

                                symbols['/'] -= 1;
                                break;
                        }
                        //stin sunexeia epeidh den exw kapoia allh praxh pera apo + h - den xreiazomai peraiterw elegxous
                    }
                    
                        //an ta keys * / exoun values 0 paei na pei pws teleiwsa oi polla pollaplasismoi kai oi diaireseis ara gurizoume ton deikth sto 0 wste na arxisoun oi praxeis apo tin arxh
                        if (symbols.ContainsKey('*') && symbols.ContainsKey('/') && (symbols['*'] + symbols['/'] == 0)) { i = 0;
                        //gia na min bainei sunexeia
                        symbols['*']++;
                        flag = true;
                    }
                    if (symbols.ContainsKey('*') && !symbols.ContainsKey('/') && (symbols['*']  == 0)) { i = 0;
                        flag = true;
                        symbols['*']++;
                    }
                    if (!symbols.ContainsKey('*') && symbols.ContainsKey('/') && (symbols['/'] == 0)) { i = 0;
                        flag = true;
                        symbols['/']++;
                    }
                    if (flag == true)
                    {
                        switch (numbers[i][0])
                        {

                            //kanw praxeis anamesa ston epomeno kai ton proigoumeno deikth ths listas otan o twrinos deikths  (element) einai eite + eite - eite * eite /
                            //kai meta stin thesi tou index - 1 bazw ton arithmo pou upolgisa enw afairw apo tin lista ton index kai index + 1 apo tin lista 
                            case '+':
                                number = float.Parse(numbers[i - 1]) + float.Parse(numbers[i + 1]);
                                numbers[i - 1] = number.ToString();
                                numbers.RemoveAt(i + 1);
                                numbers.RemoveAt(i);
                                i = i - 1;
                                boundary -= 2;

                                break;
                            case '-':
                                number = float.Parse(numbers[i - 1]) - float.Parse(numbers[i + 1]);
                                numbers[i - 1] = number.ToString();
                                numbers.RemoveAt(i + 1);
                                numbers.RemoveAt(i);
                                i = i - 1;
                                boundary -= 2;

                                break;
                        }
                    }
                    i++;
                }
            }

            return float.Parse(numbers[0]);
        } 

    }
}
