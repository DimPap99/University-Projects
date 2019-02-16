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

namespace AlepisCsharpMp3Player
{
    public partial class EditTrack : Form
    {
        private Tracks track;
        //boolean me skopo na xeroume an to tragoudi pou tha ginei edit , einai proepilegmeno apo tin proigoumeni forma , apo ton xristi
        private bool predeterminedSong = false ;
        public EditTrack()
        {
            InitializeComponent();
        }
        public EditTrack(Tracks t,bool pre)
        {
            track = new Tracks();
            track = t;
            predeterminedSong = pre;
            
            InitializeComponent();
        }

        private void EditTrack_Load(object sender, EventArgs e)
        {
            if (predeterminedSong = true)
            {
                title.Text = "Title: " + track.title;
                publishedYear.Text = "Published Year: " + track.publishedYear;
                artist.Text = "Artist: " + track.artistName;
                album.Text = "Album: " + track.album;
                duration.Text = "Duration: " + track.duration;
                genre.Text = "Genre: " + track.musicGenre;
                pictureBox1.Image = track.pictures;
            }
        }

        private void button12_Click(object sender, EventArgs e)
        {
            //write new metadata 
            bool edit = false;
            var tfile = TagLib.File.Create(track.path);
            if (textBoxImage.Text != "" && File.Exists(textBoxImage.Text))
            {
                //convert image to ipic
                tfile.Tag.Pictures = new TagLib.IPicture[]
                {

                    new TagLib.Picture(new TagLib.ByteVector((byte[])new System.Drawing.ImageConverter().ConvertTo(Image.FromFile(textBoxImage.Text), typeof(byte[]))))
                    
                };

                tfile.Save();
                edit = true;
            }else if (textBoxImage.Text != "" && !File.Exists(textBoxImage.Text))
            {
                MessageBox.Show("File doesnt exist!");
            }

                if (textBoxTitle.Text != "")
            {
               

                tfile.Tag.Title = textBoxTitle.Text;
                tfile.Save();
                track.title = textBoxTitle.Text;
                edit = true;
            }
            if (textBoxYear.Text != "")
            {
                uint num = 0;
                uint.TryParse(textBoxYear.Text, out num);
                if (num > 0)
                {
                    tfile.Tag.Year = num;
                    tfile.Save();
                    track.publishedYear = num;
                    edit = true;
                }
            }
            if (textBoxArtist.Text != "")
            {
                string[] artist = new string[1];
                artist[0] = textBoxArtist.Text;
                tfile.Tag.Performers = artist;
                tfile.Save();
                track.artistName = textBoxArtist.Text;
                         
                edit = true;
            }
            if (textBoxAlbum.Text != "")
            {
                tfile.Tag.Album = textBoxAlbum.Text;
                tfile.Save();
                track.album = textBoxAlbum.Text;
                edit = true;
            }
            if (textBoxGenre.Text != "")
            {
                string[] genre = new string[1];
                genre[0] = textBoxGenre.Text;
                tfile.Tag.Genres = genre;
                tfile.Save();
                track.musicGenre = textBoxGenre.Text;
                edit = true;
            }
            if (edit == true)
            {
               
                MessageBox.Show("Song edited succesfully!");
            }
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
           
        }
    }
}
