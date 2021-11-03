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
    public partial class Playlist : Form
    {
        static Dictionary<String, Tracks> playlistTracks = new Dictionary<string, Tracks>();
        //periexei key to onoma tou playlist kai value to path tou 
        Dictionary<string, string> playlistDict = new Dictionary<string, string>();
        public Playlist()
        {
            InitializeComponent();
        }
        
        private void button1_Click(object sender, EventArgs e)
        {
            //to dictionary ws classs einai [Serializable] opote sto dictionary playlistTracks tha apothikeuw
            //ta tracks me key to onoma tou track kai value ena tracks objects 
            //otan teleiwnw me to playlist to arxeio pou tha dimiourgeitai tha brisketai mesa ston folder Playlists
            string[] filenames, filepaths;
            openFileDialog1.Filter = "All Supported Audio | *.mp3; *.wma | MP3s | *.mp3 | WMAs | *.wma";
            if(openFileDialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                filepaths = openFileDialog1.FileNames;
                filenames = openFileDialog1.SafeFileNames;
                for(int i = 0; i< filenames.Length; i++)
                {
                    if (!playlistTracks.ContainsKey(filenames[i]))
                    {
                        listBox1.Items.Add(filenames[i]);
                        Tracks track = new Tracks();
                        track.songName = filenames[i];
                        track.path = filepaths[i];
                        track.readMetaData(filepaths[i]);
                        playlistTracks.Add(filenames[i], track);
                    }
                }

            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
           
        }

        private void button5_Click(object sender, EventArgs e)
        {

            
         
            if (!File.Exists(@"Playlists\"+textBox1.Text + ".txt"))
            {
                
                if (textBox1.Text != "" && listBox1.Items.Count > 0 )
                {
                    if (listBox1.Items.Count > 0)
                    {
                        Tracks.serializePlaylist(textBox1.Text, playlistTracks);
                        playlistTracks.Clear();
                    }
                }
                else { MessageBox.Show("Choose a unique playlists name and at least 1 song!"); }
            }
            else { MessageBox.Show("This playlist name already exists!"); }
            foreach (var file in Directory.EnumerateFiles("Playlists"))
            {
                string name = "";
                name = file.TrimStart(@"Playlists".ToCharArray());
                name = name.TrimEnd(".txt".ToCharArray());
                name = name.Remove(0, 1);
                if (!playlistDict.ContainsKey(name))
                {
                    playlistDict.Add(name, file);
                    listBox2.Items.Add(name);
                }

            }
        }

      

        private void button3_Click(object sender, EventArgs e)
        {
            listBox1.Items.Clear();
            playlistTracks.Clear();
        }

        private void Playlist_Load(object sender, EventArgs e)
        {
            foreach (var file in Directory.EnumerateFiles("Playlists"))
            {
                string name = "";
                name = file.TrimStart(@"Playlists".ToCharArray());
                name = name.TrimEnd(".txt".ToCharArray());
                name = name.Remove(0, 1);
                if (!playlistDict.ContainsKey(name))
                {
                    playlistDict.Add(name, file);
                    listBox2.Items.Add(name);
                }

            }
        }
    }
}
