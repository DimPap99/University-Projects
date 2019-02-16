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

namespace CsharpAlepisMathquiz
{
    public partial class Menu : Form
    {
        public Menu()
        {
            InitializeComponent();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            if(textBox1.Text != "")
            {
                Form1 f1 = new Form1(textBox1.Text);
                f1.Activate();
                f1.Show();
                this.Hide();
            }
            else { MessageBox.Show("Write a username!"); }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Calculator calc = new Calculator();
            calc.Show();
            calc.Activate();
            
        }

        private void Menu_Load(object sender, EventArgs e)
        {
            //if (!Directory.Exists("\\usernames"))
            //{
            //    Directory.CreateDirectory("\\usernames");
            //}

        }

        private void button3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Grapste to username pou thelete na exete kai stin sunexeia patiste start the quiz. ");
        }
    }
}
