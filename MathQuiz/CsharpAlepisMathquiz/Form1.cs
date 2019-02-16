using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CsharpAlepisMathquiz
{
    public partial class Form1 : Form
    {
        List<Label> wrongAsnwers = new List<Label>();
        List<Label> quizLabels;
        List<string> parastaseis;
        List<TextBox> quizTextBoxes;
        //boolean gia na elegxei an o xreiasteis xreiazetai extra xrono 
        bool need_extra_time = false;
        static int seconds = 9;
        static int extra_time = 0;
        private string username;
        static Calculations calc = new Calculations();
        bool start_pressed_once = false;
        Dictionary<Label, TextBox> labelsntxtboxes = new Dictionary<Label, TextBox>();
        public Form1()
        {
            InitializeComponent();
        }
        public Form1(string user)
        {
            username = user;
            InitializeComponent();

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            label16.Text = "WELCOME: " + username;
            
            quizLabels = new List<Label>();
            //bazw ola ta label p uparxoun sto panel mesa se lista
            quizLabels = panel1.Controls.OfType<Label>().ToList();
            //bazw ta textbox se lista.H lista me ta textbox k ta label exei idious  deiktes dld Label1 --- textBox1 etc
            quizTextBoxes = panel1.Controls.OfType<TextBox>().ToList();
            QuizNumbers generator = new QuizNumbers();
            #region filling the labelsntxtboxes dictionary
            labelsntxtboxes.Add(label1, textBox1);
            labelsntxtboxes.Add(label2, textBox2);
            labelsntxtboxes.Add(label3, textBox3);
            labelsntxtboxes.Add(label4, textBox4);
            labelsntxtboxes.Add(label5, textBox5);
            labelsntxtboxes.Add(label6, textBox6);
            #endregion
            //exw toses parastaseis osa kai ta label tou panel
            generator.NumberOfquestions = quizLabels.Count;
            //oi arithmitikes parastaseis
            parastaseis = generator.generateRandomList().ToList();
            int i = 0;
            //bazoume sta label tis arithmitikes parastaseis
            foreach(Label lab in quizLabels)
            {
                lab.Text = parastaseis[i];
                i++;
            }
           foreach(TextBox txt in quizTextBoxes)
            {
                txt.Hide();
            }

        }





        private void label9_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (start_pressed_once == false)
            {
                foreach(TextBox txt in quizTextBoxes)
                {
                    txt.Show();
                }
                timer1.Interval = 1000;
                timer1.Start();
                seconds = 90;
                extra_time = 0;
                
                start_pressed_once = true;
            }
            
            
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if(seconds == 0) { timer1.Stop();
                need_extra_time = true;
                timer2.Enabled = true;
                timer2.Interval = 1000;
               // label17.BackColor = Color.Gray;
            }
            label7.Text = "Time Left: " + seconds.ToString();
            if (seconds > 60)
            {
                label7.BackColor = Color.Green;
            }else if( seconds >= 30 && seconds <60) { label7.BackColor = Color.Orange; }
            else if(seconds < 30) { label7.BackColor = Color.Red; }
            seconds--;
            
           
            
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (start_pressed_once == true)
            {
                //elegxos oti den emeinan kena
                int counter = 0;
                float num;
                bool invalid_input = false;
                int correct_answers = 0;
                //elegxos gia swsto input
                foreach (TextBox txt in quizTextBoxes)
                {
                    if (txt.Text == "") { counter++; }
                    if (float.TryParse(txt.Text, out num) == false)
                    {

                        invalid_input = true;
                    }

                }

                if (counter != 0 || invalid_input == true)
                {
                    MessageBox.Show("Wrong input!");
                }
                else if (invalid_input == true) { MessageBox.Show("Wrong input!"); }
                else
                {
                    timer1.Stop();
                    need_extra_time = false;
                    timer2.Stop();
                    //vriskoume an ta apotelesmata pou dwsame einai swsta
                    foreach (Label lab in quizLabels)
                    {
                        

                        if (calc.ParseAndCalculate(lab.Text) == float.Parse(labelsntxtboxes[lab].Text))
                        {
                            
                            correct_answers++;
                        }
                        else
                        {
                            wrongAsnwers.Add(lab);
                        }
                        
                    }
                    
                    start_pressed_once = false;
                    Statistics st = new Statistics(username, correct_answers, 90 - seconds, extra_time,wrongAsnwers);
                    st.Show();
                    st.Activate();
                    this.Hide();
                }

            }
        }

        private void button3_Click(object sender, EventArgs e)
        { 
                Calculator calc = new Calculator();
                calc.Show();
                calc.Activate();
                
        }

        private void timer2_Tick(object sender, EventArgs e)
        {
            if (timer1.Enabled == false && need_extra_time == true){
                
                if(extra_time % 2 == 1) { label17.BackColor = Color.Green; }
                else { label17.BackColor = Color.GreenYellow; }
                label17.Text = "Extra Time: " + extra_time.ToString();
                extra_time++;
            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            Menu menu = new Menu();
            menu.Show();
            menu.Activate();
            this.Hide();
        }

        private void button5_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Symlpirwste ola ta kena pedia me ta swsta apotelesmata twn parastasewn.Epitrepetai h xrhsh mono arithmwn!");
            MessageBox.Show("Boreite na xrhsimopoihsete to calculator gia tis praxeis.");
        }
    }
}
