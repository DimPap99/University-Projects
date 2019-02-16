using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMemoryGame
{
    public partial class Form1 : Form
    {
        
        static Dictionary<string, int> pictureCheck = new Dictionary<string, int>();
        //key img name value img full path
        static Dictionary<string, string> picturesAndPaths =new Dictionary<string, string>();
        public static Helper helper = new Helper();
        static List<string> images = new List<string>();
        static List<string> checkedImages = new List<string>();
       
        public Form1()
        {
            InitializeComponent();
        }

        //An to radiobutton1 (Default settings) einai checked tote to paixnidi pairnei tis prokathorismenes apo emena fwtografies
        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            if (radioButton1.Checked == true)
            {
                helper.defaultSettings = true;
                picturesAndPaths.Clear();
                pictureCheck.Clear();
            }
            // picturesAndPaths = new Dictionary<string, string>();
            helper.clearCheckBox(checkedListBox1);
            if(radioButton1.Checked == true)
            { 
               string currentFolderPath = Environment.CurrentDirectory;
               images = Helper.GetImages(currentFolderPath+"\\quizphotos");
               foreach(string image in images)
                {
                    if (!picturesAndPaths.ContainsKey(image.ToString()))
                    {
                        picturesAndPaths.Add(image.ToString(), currentFolderPath);
                    }
                    checkedListBox1.Items.Add(image);
              
                }
               for(int i = 0;i<8; i++)
                {
                        checkedListBox1.SetItemCheckState(i, CheckState.Checked);
                    
                }

            }
            
            
        }

        private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        //emfanizei tin  kathe clickarismenh fwtografia
        private void checkedListBox1_Click(object sender, EventArgs e)
        {
            //pairnei to string tou kathe item pou klikarw sto checkbox
            string imageName = checkedListBox1.GetItemText(checkedListBox1.SelectedItem);
            if (radioButton1.Checked != true)
            {
                pictureBox1.ImageLocation = picturesAndPaths[imageName];
            }else if(radioButton1.Checked == true)
            {
                pictureBox1.ImageLocation = "quizphotos\\" + imageName;
            }


        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void radioButton2_Click(object sender, EventArgs e)
        {
            // helper.clearCheckBox(checkedListBox1);
            
            openFileDialog1.Filter = "Image files | *.jpg; *.jpeg; *.jpe; *.jfif; *.png";
            string[] fileNames, filePaths;
            //check an o user anoixe ena file
            if(openFileDialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                //pairnei to onoma tou file
                fileNames = openFileDialog1.SafeFileNames;
                //pairnei to full path tou file
                filePaths = openFileDialog1.FileNames;
                for(int i = 0; i < fileNames.Length; i++)
                {
                   //elegxos wste na min uparxoun idia image files sto checkedlistbox
                    if (!pictureCheck.ContainsKey(fileNames[i])){
                        pictureCheck.Add(fileNames[i], 0);
                        checkedListBox1.Items.Add(fileNames[i]);
                        helper.imageCounter++;
                        picturesAndPaths.Add(fileNames[i], filePaths[i]);
                    }
                    else {
                       
                        MessageBox.Show("You have already chosen the " + fileNames[i] + " Image file.Please Choose another one!"); }
                }
               
            }
           
        }

        private void Form1_Load(object sender, EventArgs e)
        {

            if (!Directory.Exists("scores"))
            {
                Directory.CreateDirectory("scores");
            }

        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            if(radioButton2.Checked == true) {
                helper.defaultSettings = false;
                picturesAndPaths.Clear();
                pictureCheck.Clear();
            }
        }

        private void checkedListBox1_ItemCheck(object sender, ItemCheckEventArgs e)
        {
           
                
        }

        

        private void button1_Click(object sender, EventArgs e)
        {
            helper.checkedpicturesAndPaths = new Dictionary<string, string>();
            helper.username = textBox1.Text;
            int checks = 0;
            int i = 0;
            foreach(var image in checkedListBox1.Items)
            {
                
                if(checkedListBox1.GetItemCheckState(i) == CheckState.Checked)
                {
                    //pernaw ws string sto dictionary me key ta images pou einai checked kai value ta full paths tous
                   
                    if (!helper.checkedpicturesAndPaths.ContainsKey(image.ToString())){
                        helper.checkedpicturesAndPaths.Add(image.ToString(), picturesAndPaths[image.ToString()]); }
                    checks++;
                }
                i++;
            }
            
            if (checks > 8) { MessageBox.Show("You have to uncheck " + (checks - 8).ToString() + " images."); }
            else if(checks!= 8) { MessageBox.Show("You have to check " + (8 - checks).ToString() + " more images."); }
            if(helper.username == ""){ MessageBox.Show("You must write your username!"); }
            if (helper.username != "" && checks == 8) {

                GameForm gf = new GameForm(helper);
                gf.Activate();
                gf.Show();
               // this.Close();
               this.Hide();
            }
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Scores sc = new Scores();
            sc.Activate();
            sc.Show();
            
            this.Hide();
        }
    }
}
