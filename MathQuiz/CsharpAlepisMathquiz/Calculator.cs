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
    public partial class Calculator : Form
    {
        int dotCounter = 0;
        bool operatorClicked = false;
        static Calculations calculator = new Calculations();
        public Calculator()
        {
            InitializeComponent();
        }

        private void button13_Click(object sender, EventArgs e)
        {
            Button b = (Button)sender;
            if (label1.Text == "0")
            {
                label1.Text = "";
            }
            if (operatorClicked == false)
            {
                label1.Text += b.Text;
            }
            else
            {
                label1.Text = b.Text;
                
            }
            operatorClicked = false;




        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        

        private void operators_Click(object sender, EventArgs e)
        {
            operatorClicked = true;
            Button b = (Button)sender;
            label2.Text += label1.Text + b.Text;
        }

        private void dot_Click(object sender, EventArgs e)
        {
            Button b = (Button)sender;
            if(dotCounter == 0) { label1.Text += b.Text;
                dotCounter++;
            }
        }

        private void button16_Click(object sender, EventArgs e)
        {
            label1.Text = "0";
            label2.Text = "";
        }

        private void button18_Click(object sender, EventArgs e)
        {
            //remove last char
           label1.Text =  label1.Text.Remove(label1.Text.Length - 1);
            if(label1.Text == "") { label1.Text = "0"; }
                    
        }

        private void equals_Click(object sender, EventArgs e)
        {//elegxos an o teletaios xarakthras tou label2 einai operator
            
            if(label2.Text[label2.Text.Length - 1] == '+' | label2.Text[label2.Text.Length - 1] == '-' | label2.Text[label2.Text.Length - 1] == '*' | label2.Text[label2.Text.Length - 1] == '/')
            {

                label1.Text = calculator.ParseAndCalculate(label2.Text+label1.Text).ToString();
                label2.Text = "";
            }
        }

        private void Calculator_Load(object sender, EventArgs e)
        {
            
        }
    }
}
