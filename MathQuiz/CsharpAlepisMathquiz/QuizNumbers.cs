using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMathquiz
{
    //class stin opoia ginontai generate oi tuxaioi kai parastaseis tis opoies tha klithei na apantisei o xristis
    public class QuizNumbers
    {
        static char[] calcSymbols = { '+', '-', '*', '/' };
        private int numberOfQuestions;
        public int NumberOfquestions
        {
            get
            {
                return numberOfQuestions;
            }

            set
            {
                if (value > 0)
                {
                    numberOfQuestions = value;
                }
            }

        }

        #region METHODS
        //i methodos auth analoga me to poses erwthseis uparxoun dimourgei tuxaies arithmitikes parastaseis pou periexoun tuxaious arithmous apo to 1 - 1000 kai tuxaia sumbola :  + - / *
        public List<string> generateRandomList()
        {
            List<string> parastaseis = new List<string>();
            Random randNum = new Random();
            Random randSym = new Random();
            
            
           
            for(int i = 0; i < this.numberOfQuestions; i++)
            {
                int numCount = randSym.Next(2, 6);
                string parastash = "";
                //i arithmoi einai 2plasioi twn symbolwn
               
                for(int j = 0; j < numCount; j++)

                {//epilogh tuxaiou arithmou
                    //oi arithmoi einai plithos  sumbolwn  + 1

                    int number = randNum.Next(1, 150);
                    //epilogi tuxaiou symbolou
                    int symbolIndex = randSym.Next(0, 3);
                    parastash += number.ToString();
                    //bazw prosimo endiamesa ektos apo to telos
                    if(j != numCount - 1)
                    {
                        parastash += calcSymbols[symbolIndex].ToString();
                    }
                }
               
                parastaseis.Add(parastash);


            }
            return parastaseis;
        }
        #endregion
    }
}
