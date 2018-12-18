using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Reflection;

namespace proairetiki4
{
    public partial class PLOIGISI : Form
    {
        static List<TextBox> textBoxes = new List<TextBox>();
        static List<DateTimePicker> dateTimePickers = new List<DateTimePicker>();
        static bool buttonsExist = false;
        public PLOIGISI()
        {
            InitializeComponent();
        }

        private void PLOIGISI_Load(object sender, EventArgs e)
        {
            if (!Directory.Exists("\\contacts"))
            {
                Directory.CreateDirectory("contacts");
            }
            String dir = Application.StartupPath + "\\contacts";
            String[] myFiles = Directory.GetFiles(dir, "*.txt");
            foreach (String s in myFiles)
                listBox1.Items.Add(Path.GetFileName(s).Replace(".txt", "") + Environment.NewLine);

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Form1 f1 = new Form1();
            f1.Activate();
            f1.Show();
            this.Hide();
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void listBox1_Click(object sender, EventArgs e)
        {
            string contactName = listBox1.GetItemText(listBox1.SelectedItem);
            if (contactName != "")
            {
                BinaryFormatter bf = new BinaryFormatter();
                Stream st = new FileStream("contacts\\" + contactName.Replace("\r\n", "") + ".txt", FileMode.OpenOrCreate);
                contact c = (contact)bf.Deserialize(st);
                PropertyInfo[] pInfoArray = c.GetType().GetProperties();
                String s = "";

                foreach (var v in pInfoArray)
                {
                    if (v.Name.ToString() != "date")
                    {
                        s += v.GetValue(c).ToString() + "-";
                    }
                    else if (v.Name.ToString() == "date")
                    {
                        s += v.GetValue(c).ToString();
                    }

                }


                st.Close();
                label2.BackColor = Color.Crimson;
                label2.Text = s;
                Button DeleteButton = new Button();
                DeleteButton.Text = "Contact Delete";
                DeleteButton.Location = new Point(138, 50);
                DeleteButton.Height = 40;
                DeleteButton.Width = 300;
                DeleteButton.BackColor = Color.Chocolate;
                label2.BackColor = Color.Crimson;
                label2.Text = s;
                DeleteButton.Click += new EventHandler(DeleteButton_Click);

                Button EditButton = new Button();
                EditButton.Text = "Edit Contact";
                EditButton.Location = new Point(476, 50);
                EditButton.Height = 40;
                EditButton.Width = 300;
                EditButton.BackColor = Color.Chocolate;
                EditButton.Click += new EventHandler(EditButton_Click);
                Controls.Add(DeleteButton);
                Controls.Add(EditButton);
                
            }
        }
        private void DeleteButton_Click(object sender,EventArgs e)
        {
            string contactName = listBox1.GetItemText(listBox1.SelectedItem);
            File.Delete("contacts\\" + contactName.Replace("\r\n", "") + ".txt");
            MessageBox.Show(contactName + " Deleted Succesfully!");

            PLOIGISI pl = new PLOIGISI();
            pl.Activate();
            pl.Show();
            this.Hide();
            
        }
        
        private void saveButton_Click(object sender ,EventArgs e) {
            
                contact editContact = new contact();
                string contactName = listBox1.GetItemText(listBox1.SelectedItem);
            MessageBox.Show(textBoxes[0].Text);

            editContact.addOnoma(textBoxes[0].Text);
                editContact.addEpitheto(textBoxes[1].Text);
                editContact.addKatoikia(textBoxes[4].Text);
                editContact.addThlefwno(textBoxes[3].Text);
                editContact.addEmail(textBoxes[2].Text);
                editContact.addDate(dateTimePickers[0].Text);
                File.Delete("contacts\\" + contactName.Replace("\r\n", "") + ".txt");
                MessageBox.Show(editContact.onoma + " " + editContact.epitheto);
                BinaryFormatter bf2 = new BinaryFormatter();
                Stream st2 = new FileStream("contacts\\" + editContact.onoma + " " + editContact.epitheto + ".txt", FileMode.OpenOrCreate);
                bf2.Serialize(st2, editContact);
                st2.Close();
                MessageBox.Show("EPITYXES EDIT THS EPAFHS!");
                PLOIGISI pl = new PLOIGISI();
                pl.Activate();
                pl.Show();
           
                this.Hide();
            
        }
        private void EditButton_Click(object sender,EventArgs e) {
            buttonsExist = true;
            Label onomaLabel = new Label();
            onomaLabel.Text = "ONOMA:";
            onomaLabel.BackColor = Color.LightSlateGray;
            onomaLabel.Location = new Point(168, 100);
            onomaLabel.Height = 20;
            onomaLabel.Width = 51;
            Controls.Add(onomaLabel);

            TextBox onomaTextbox = new TextBox();
            onomaTextbox.Location = new Point(250, 100);
            onomaTextbox.Height = 20;
            onomaTextbox.Width = 209;
            Controls.Add(onomaTextbox);

            Label epithetoLabel = new Label();
            epithetoLabel.Text = "EPITHETO:";
            epithetoLabel.BackColor = Color.LightSlateGray;
            epithetoLabel.Location = new Point(168, 127);
            epithetoLabel.Height = 20;
            epithetoLabel.Width = 61;
            Controls.Add(epithetoLabel);

            TextBox epithetoTextbox = new TextBox();
            epithetoTextbox.Location = new Point(250, 127);
            epithetoTextbox.Height = 20;
            epithetoTextbox.Width = 209;
            Controls.Add(epithetoTextbox);

            Label tilefwnoLabel = new Label();
            tilefwnoLabel.Text = "THLEFWNO:";
            tilefwnoLabel.BackColor = Color.LightSlateGray;
            tilefwnoLabel.Location = new Point(168, 154);
            tilefwnoLabel.Height = 20;
            tilefwnoLabel.Width = 70;
            Controls.Add(tilefwnoLabel);

            TextBox tilefwnoTextbox = new TextBox();
            tilefwnoTextbox.Location = new Point(250, 154);
            tilefwnoTextbox.Height = 20;
            tilefwnoTextbox.Width = 209;
            Controls.Add(tilefwnoTextbox);

            Label emailLabel = new Label();
            emailLabel.Text = "EMAIL:";
            emailLabel.BackColor = Color.LightSlateGray;
            emailLabel.Location = new Point(168, 181);
            emailLabel.Height = 20;
            emailLabel.Width = 51;
            Controls.Add(emailLabel);

            TextBox emailTextbox = new TextBox();
            emailTextbox.Location = new Point(250, 181);
            emailTextbox.Height = 20;
            emailTextbox.Width = 209;
            Controls.Add(emailTextbox);

            Label katoikiaLabel = new Label();
            katoikiaLabel.Text = "KATOIKIA:";
            katoikiaLabel.BackColor = Color.LightSlateGray;
            katoikiaLabel.Location = new Point(168, 208);
            katoikiaLabel.Height = 20;
            katoikiaLabel.Width = 70;
            Controls.Add(katoikiaLabel);

            TextBox katoikiaTextbox = new TextBox();
            katoikiaTextbox.Location = new Point(250, 208);
            katoikiaTextbox.Height = 20;
            katoikiaTextbox.Width = 209;
            Controls.Add(katoikiaTextbox);

            Label dateLabel = new Label();
            dateLabel.Text = "HMEROMINIA GENNHSHS:";
            dateLabel.BackColor = Color.LightSlateGray;
            dateLabel.Location = new Point(168, 235);
            dateLabel.Height = 20;
            dateLabel.Width = 100;
            Controls.Add(dateLabel);

            DateTimePicker dateTimePicker = new DateTimePicker();
            dateTimePicker.MaxDate = DateTime.MaxValue;
            dateTimePicker.MinDate = DateTime.MinValue;
            dateTimePicker.Location = new Point(280, 235);
            dateTimePicker.Height = 20;
            dateTimePicker.Width = 209;
            dateTimePicker.Value = DateTime.Today;
            Controls.Add(dateTimePicker);


            Button SaveButton = new Button();
            SaveButton.Text = "SAVE THE NEW CREDENTIALS";
            SaveButton.Location = new Point(490, 170);
            SaveButton.Height = 70;
            SaveButton.Width = 200;
            SaveButton.BackColor = Color.Chocolate;
            SaveButton.Click += new EventHandler(saveButton_Click);
            Controls.Add(SaveButton);




            string contactName = listBox1.GetItemText(listBox1.SelectedItem);

                BinaryFormatter bf = new BinaryFormatter();
                Stream st = new FileStream("contacts\\" + contactName.Replace("\r\n", "") + ".txt", FileMode.OpenOrCreate);
                contact c = (contact)bf.Deserialize(st);
                PropertyInfo[] pInfoArray = c.GetType().GetProperties();
                String s = "";
                onomaTextbox.Text = c.onoma;
                epithetoTextbox.Text = c.epitheto;
                tilefwnoTextbox.Text = c.thlefwno;
                emailTextbox.Text = c.email;
                katoikiaTextbox.Text = c.katoikia;
                dateTimePicker.Value = DateTime.Parse(c.date);
            st.Close();
            textBoxes.Add(onomaTextbox);
            textBoxes.Add(epithetoTextbox);
            textBoxes.Add(tilefwnoTextbox);
            textBoxes.Add(emailTextbox);
            textBoxes.Add(katoikiaTextbox);
            dateTimePickers.Add(dateTimePicker);

        }
    }
    }


