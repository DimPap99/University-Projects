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

    public partial class Scores : Form
    {
       
        public Scores()
        {
            InitializeComponent();
        }
        string username;
        int score;
        public Scores(int score,string username)
        {
            InitializeComponent();
            this.username = username;
            this.score = score;
            
        }
        //public string s;
        //Scores(string s) { this.s = s; }
        private void Scores_Load(object sender, EventArgs e)
        {
            if (username != null && score != null)
            {
                label2.Text = username + "'s score was " + score.ToString() + " seconds!";
            }
            //getTopscores();
            //List<string> results = new List<string>();
            //results = Player.getTopscores();
            int i = 0;
            foreach (string player in Player.getTopscores())
            {
                
                listBox1.Items.Add((i+1).ToString()+". "+player);
                i++;
                if (i > 9) { break; }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Form1 f1 = new Form1();
            f1.Activate();
            f1.Show();
            this.Hide();
        }
    }
}
