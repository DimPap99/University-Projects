using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using TagLib;
namespace AlepisCsharpMp3Player
{
    class Helper
    {
        public static string findSongfullPath(string songName , string dirPath)
        {
            string path="None";
            DirectoryInfo di = new DirectoryInfo(dirPath);
            if (di.Exists)
            {
                FileInfo[] file = di.GetFiles(songName);
                foreach(FileInfo f in file)
                {
                    path = f.FullName;
                }
            }
        
            return path;
        }
        public static Image ConvertIpicToImg(IPicture[] pictures)
        {

            //convert Ipicture to Image
           
                MemoryStream ms = new MemoryStream(pictures[0].Data.Data);

                Image img = Image.FromStream(ms);


                return img;
           
        }

        public static List<int> shuffledList (int N)
        {
            List<int> aList = new List<int>();
            List<int> shuffledL = new List<int>();
            int range = N;
            for(int i = 0; i < N; i++)
            {
                aList.Add(i);
            }
            Random rand = new Random();
            while (aList.Count > 0)
            {
                int index = rand.Next(0, range);
                shuffledL.Add(aList[index]);
                aList.RemoveAt(index);
                range = aList.Count;
            }
            return shuffledL;
        }
    }
}
