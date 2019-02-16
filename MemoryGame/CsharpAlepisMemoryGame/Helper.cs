using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMemoryGame
{
    //to class helper periexei boithitikes methodous kai metavlites aparaitites gia tn ergasia
    public class Helper
    {
        public int time = 0;
        public string username;
        public int imageCounter = 0;
        public Dictionary<string,string> checkedpicturesAndPaths;
        public bool defaultSettings = false;
        //method gia na pairnw ta full path twn eikwnwn enws directory
       public static List<string> GetImages(string folderPath)
        {
            List<string> images = new List<string>();

            DirectoryInfo d = new DirectoryInfo(folderPath);

            FileInfo[] Files = d.GetFiles("*"); //pairnoume gia kathe file p uparxei sto folder pou orisame ena FileInfo object me plirofories gia to kathe file
          
            foreach (FileInfo file in Files)
            {
                images.Add(file.Name);
            }

           

            
          
          
            return images;
        }

        public void clearCheckBox(CheckedListBox checkedListBox)
        {
            //xrhsimopoioume to OfType<string>() gia na boresoume na kalesoume thn .ToList().H .ToList() dimiourgei mia kainourgia lista kata 
            //auto ton tropo ginontai dinates allages panw sto checkedListBox  kathws o enumarator pleon kanei enumerate tin nea lista mas. 
            foreach (var item in checkedListBox.Items.OfType<string>().ToList())
            {
                checkedListBox.Items.Remove(item);
            }
        }

        public void ShuffleArray(int n , List<int> myList , int[] array)
        {
            Random rand = new Random();
            int arrayIndex = 0;
            while (true)
            {
                //to range einai apo 0 ews n - arrayIndex epeidh sto sto telos 
                //afairoume kathe fora ena stoixeio apo tn lista 
                int randomIndex = rand.Next(0, n - arrayIndex);
                //pairnoume ena tuxaio arithmo apo tin lista mas
                int number = myList[randomIndex];
               
                array[arrayIndex] = number;
                arrayIndex++;
                //Gia na apofugw tin dipli emfanisi enws arithmou epidh einai pithano na tuxoume ston idio deikti
                //afairw ton arithmo number ( o tuxaios pou brika) apo tin lista.Auto simainei oti kai to count tis listas 
                //exei meiwthei kata ena.Kai twra sto idio index tis lista pou diagrapsame (an auto den itan to megalutero)
                //brisketai kapoios allos arithmos
                myList.Remove(number);
                //an to arrayIndex einai >=n auto shmainei oti xeperasame to sunolo ton arithmwn poy edwse o
                //xristis  opote kanoume break gia na spasei to loop
                if (arrayIndex >= n) { break; }
            }
        }

        public static void flipBackSide(List<PictureBox> pictureBoxes,string path) {
            foreach (PictureBox pic in pictureBoxes)
            {
                
                pic.ImageLocation = path;
            }
        }

    }
}
