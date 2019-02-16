using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMemoryGame
{
   
    public partial class GameForm : Form
    {   //check oti kathe image borei an allaxei mono me ena click
        static Dictionary<PictureBox, bool> fireOnlyOnce = new Dictionary<PictureBox, bool>();
        static List<PictureBox> CopyOfpictureBoxes;
        Random rand;
        static List<string> pictureBoxFullPaths;
        static List<PictureBox> similarPicBoxes = new List<PictureBox>();
        public Helper helper;
        static List<PictureBox> pictureBoxes;
        static Dictionary<int, int> checkNumberOccurence = new Dictionary<int, int>();
        //exei pictureboxes k tuxaious arithmous
        Dictionary<PictureBox, int> picBoxDict;
        Dictionary<PictureBox, int> storePicBoxNumber = new Dictionary<PictureBox, int>();
        //counter for correct guesses
        static int correctCounter = 0;
        static Random r = new Random();
        static int seconds = 0;
        static bool start = false;
        Player player = new Player();
        public GameForm(Helper helper)
        {
            InitializeComponent();
            this.helper = helper;
        }
        int[] numbersArray;
        List<int> numbersList;
        //tha xrisimopoiw 2 dictionary to ena apo auta tha periexei key ena integer apo to 0 ews to 11 kai value to path kathe fwtografias
        //kai to allo tha exei ws key arithmous apo to 0 ews to 11 enw values apo to 0 mexri to 1
        //kata auto ton tropo tha borw an otan anathetw ena image se ena pictureBox na xerw poses fores exw anathesei to sugekrimeno image.Den prepei na xeperasw tis 2 fores
        //ta images tha epilegontai tuxaia me tin boitheia enos function ths Helper.
        public GameForm()
        {
            InitializeComponent();
        }

        private void gameForm_Load(object sender, EventArgs e)
        {
            player.username = helper.username;
            if (!File.Exists("scores\\" + player.username+".txt"))
            {
                File.Create("scores\\" + player.username+".txt");
            }

            picBoxDict = new Dictionary<PictureBox, int>();
            label8.Text += helper.username;
            //12 osa kai ta picturebox
            numbersArray = new int[16];                
            //bazw ola to picturebox se mia lista
            pictureBoxes = new List<PictureBox>();
            pictureBoxes = this.Controls.OfType<PictureBox>().ToList();
            CopyOfpictureBoxes = new List<PictureBox>();
            CopyOfpictureBoxes = this.Controls.OfType<PictureBox>().ToList();
            Helper.flipBackSide(pictureBoxes, "yugi.jpg");
            //dinw stin koubia tin idia eikona stin arxi
            foreach (PictureBox pic in pictureBoxes)
            {
                pic.SizeMode = PictureBoxSizeMode.StretchImage;
                pic.Click += pictureBox1_Click;
                
            }
            pictureBoxFullPaths = new List<string>();
            rand = new Random();
            
            foreach(KeyValuePair<string,string> keyValuePair in helper.checkedpicturesAndPaths)
            {
               
                for (int i = 0; i < 2; i++)
                {
                    if (helper.defaultSettings == true)
                    {
                        pictureBoxFullPaths.Add(keyValuePair.Value + "\\quizphotos\\"+keyValuePair.Key);
                    }else if(helper.defaultSettings == false)
                    {
                        pictureBoxFullPaths.Add(keyValuePair.Value);
                    }
                    
                }
            }
            Dictionary<int, int> dict = new Dictionary<int, int>();
            //vazw sto pictboxDict pictureboxes me tuxaious arithmous 0 - 15
            for(int i= 0; i < 16; i++)
            {
                int randNumber = rand.Next(0, 16);
                while (dict.ContainsKey(randNumber))
                {
                    randNumber = rand.Next(0, 16);
                    
                   
                }
                dict.Add(randNumber, 0);

                numbersArray[i] = randNumber;
                picBoxDict.Add(pictureBoxes[i], numbersArray[i]);
                //vazw sto kathe key picbox  value ena tuxaio arithmo
                storePicBoxNumber.Add(pictureBoxes[i], numbersArray[i]);
            }
           

        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {
            if (start == true)
            {
                PictureBox picBox = (PictureBox)sender;

                //elegxos wste na leitourgei me mono 1 click to event
                if (!fireOnlyOnce.ContainsKey(picBox))
                {
                    fireOnlyOnce.Add(picBox, true);
                }
                //afou allaxoume tin piswpleura tis kartas tote to click event den xanadouleuei
                if (picBox.ImageLocation != "yugi.jpg")
                {
                    fireOnlyOnce[picBox] = false;
                }
                if (fireOnlyOnce[picBox] == true)
                {

                    //to prwto picBox p allazw tha bainei se mia lista
                    //an to 2o picBox exei idio imageLocation me auto p einai sti lista tote katharizw ti lista kai sunexizw 
                    //an den exei katharizw tin lista kai oi eikones xanaginontai pantou idies (flipBackside)
                    //episis katharizw to fireonlyonce Dictionary

                    picBox.ImageLocation = pictureBoxFullPaths[storePicBoxNumber[picBox]];
                   

                    if (similarPicBoxes.Count == 0)
                    {
                        similarPicBoxes.Add(picBox);
                    }
                    else
                    {
                        if (similarPicBoxes[0].ImageLocation == picBox.ImageLocation)
                        {
                            //ta afairw apo to list wste na parameinoun anoixta
                            CopyOfpictureBoxes.Remove(similarPicBoxes[0]);
                            CopyOfpictureBoxes.Remove(picBox);
                            similarPicBoxes.Clear();
                            correctCounter += 2;
                            
                           
                        }
                        else
                        {
                            
                            MessageBox.Show("Wrong picture.Try again!");
                            fireOnlyOnce.Clear();
                            similarPicBoxes.Clear();
                            Helper.flipBackSide(CopyOfpictureBoxes, "yugi.jpg");
                            

                        }

                    }
                }
                if (correctCounter == 16)
                {
                    timer1.Stop();
                    player.seconds = seconds;
                    seconds = 0;
                    MessageBox.Show("You win!");
                    StreamWriter write = new StreamWriter("scores\\" + player.username + ".txt",append:true);
                 //   write.WriteLine("\n");
                    write.WriteLine(player.username + " " + player.seconds);
                    write.Close();
                    correctCounter = 0;
                    Scores sc = new Scores(player.seconds,player.username);
                    sc.Activate();
                    sc.Show();
                    this.Hide();
                    
                }
            }

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            seconds++;
            label1.Text = "Time: " + seconds.ToString();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            timer1.Start();
            start = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Form1 f1 = new Form1();
            f1.Activate();
            f1.Show();
            this.Hide();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Form1 f1 = new Form1();
            f1.Activate();
            f1.Show();
            this.Hide();
        }
    }
}
