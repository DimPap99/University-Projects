using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMathquiz
{
    public partial class Statistics : Form
    {
        int time;
        int xtime=0;
        string username;
        int correct_answers;
        List<Label> wrongAnswers;
        Calculations calc = new Calculations();
        public Statistics(string user, int answ, int t,int t2,List<Label> labels)
        {
            xtime = t2;
            time = t;
            username = user;
            correct_answers = answ;
            wrongAnswers = labels.ToList();
            InitializeComponent();
        }

        private void Statistics_Load(object sender, EventArgs e)
        {
            label6.Hide();
            label1.Text += " "+ username;
            label2.Text += " " + correct_answers.ToString();
            label3.Text += " " + (6 - correct_answers).ToString();
            label4.Text += ((correct_answers / 6.0 )*100).ToString() + "%";
            label5.Text += (time + xtime - 1).ToString() + " seconds";
            
            if(xtime > 0) {
                label6.Show();
                label6.Text = "Extra Time Needed:" + xtime.ToString() + " seconds"; }
            //emfanish swston apantisewn pou eginan prin lathos
            if(wrongAnswers.Count > 0)
            {
                int i = 220;
                foreach(Label label in wrongAnswers)
                {
                    label.Location = new Point(39, i);
                    label.Width = 306;
                    label.BackColor = Color.Green;
                    label.Text += " = " + calc.ParseAndCalculate(label.Text).ToString();
                    Controls.Add(label);
                    i += 30;
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Form1 f1 = new Form1(username);
            f1.Show();
            f1.Activate();
            this.Hide();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Menu men = new Menu();
            men.Show();
            men.Activate();
            this.Hide();
        }
    }
}
