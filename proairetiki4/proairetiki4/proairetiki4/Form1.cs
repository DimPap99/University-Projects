using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace proairetiki4
{
    public partial class Form1 : Form
    {
         contact conc = new contact();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                if (textBox1.Text != "" && textBox2.Text != "" && textBox3.Text != "" && textBox4.Text != "" && textBox5.Text != "")
                {
                    
                    conc.addOnoma(textBox1.Text);
                    conc.addEpitheto(textBox2.Text);
                    conc.addKatoikia(textBox3.Text);
                    conc.addThlefwno(textBox5.Text);
                    conc.addEmail(textBox4.Text);
                    conc.addDate(dateTimePicker1.Text.ToString());
               

                BinaryFormatter bf = new BinaryFormatter();
                Stream st = new FileStream("contacts\\" + conc.onoma+" "+conc.epitheto + ".txt", FileMode.OpenOrCreate);
                bf.Serialize(st, conc);
                st.Close();
                    
                    MessageBox.Show("Epituxhs prosthiki!");
                }
                else { MessageBox.Show("Gemiste ola ta pedia!"); }
            }
            catch (ArithmeticException exc)
            {
                MessageBox.Show(exc.ToString() + "\n Wrong input!");
            }
        
}

        private void button2_Click(object sender, EventArgs e)
        {
            
        }

        private void button2_Click_1(object sender, EventArgs e)
        {
            PLOIGISI p = new PLOIGISI();
            p.Activate();
            p.Show();
            this.Hide();
        }
    }
}
