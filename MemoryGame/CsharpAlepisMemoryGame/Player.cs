using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMemoryGame
{
    class Player
    {
        //metavlites kai xrhsimes methodous
        public int seconds;
        public string username;

        public static List<string> getTopscores()
        {
            List<string> playerName = new List<string>();
            List<int> playerScore = new List<int>();
            //exei ta onomata twn fakelwn
            List<string> fileNames = new List<string>();
            int filecount = 0;
            DirectoryInfo d = new DirectoryInfo("scores");

            FileInfo[] Files = d.GetFiles("*"); //pairnoume gia kathe file p uparxei sto folder pou orisame ena FileInfo object me plirofories gia to kathe file
            foreach (FileInfo file in Files)
            {
                fileNames.Add(file.Name);
                filecount++;
            }

            //gia osous fakelous exoume tha bazoume ta score se lista kai tha tin kanoume sort
            for (int i = 0; i < filecount; i++)
            {
                StreamReader reader = new StreamReader("scores\\" + fileNames[i]);
                while (true)
                {

                    string line = reader.ReadLine();

                    if (line != null)
                    {
                        string[] temp = line.Split(' ');
                        //bazoume se mia list ola ta usernames k s mia allh ola ta score
                       // MessageBox.Show(temp[0].ToString());
                        playerName.Add(temp[0]);
                    //    MessageBox.Show(temp[1].ToString());

                        playerScore.Add(Int32.Parse(temp[1]));



                    }
                    else if (line == null)
                    {
                        reader.Close();
                        break;
                    }
                }
                reader.Close();


            }
            //bubble short kai gia tis 2 listes
            

            int temp1 = 0;
            string temp2 = "";
            for (int i = 0; i < playerScore.Count; i++)
            {
                for (int j = 0; j < playerScore.Count - 1; j++)
                {
                    if (playerScore[j] > playerScore[j + 1])
                    {
                        //gia tin prwti lista
                        temp1 = playerScore[j + 1];
                        playerScore[j + 1] = playerScore[j];
                        playerScore[j] = temp1;
                        //gia tin deuterh
                        temp2 = playerName[j + 1];
                        playerName[j + 1] = playerName[j];
                        playerName[j] = temp2;
                    }
                }
            }
            //pernaw ta periexomena twn playerscore/name se mia allh lista ws string kai tin epistrefw 
            List<string> sortedResults = new List<string>();
            for (int i = 0; i < playerScore.Count; i++)
            {
               
                sortedResults.Add(playerName[i] + " seconds: " + playerScore[i]);
            }
            
            return sortedResults;
        }
    }
}
