using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using WMPLib;

namespace AlepisCsharpMp3Player
{
    public partial class Form1 : Form
    {
        //lista me tracks obj
        List<Tracks> listofTracks;
        //key to onoma tou tragoudiou value i suxnotita p to xrhsimopoiei o xristis
        static Dictionary<string, int> trackFrequency = new Dictionary<string, int>();
        static bool shuffle = false;
        List<int> shuffledIndexes = new List<int>();
        static bool loadedFromPlaylist = false;
        static bool clickonce = false;
        static int flag = 0;
        //static dictionary me keys to onoma tou tragoudiou kai value to full path tou
        Dictionary<string, string> SongsNPaths = new Dictionary<string, string>();
        //static dictionary me key to onoma enos tragoudiou kai value to object track
        Dictionary<string, Tracks> TracksDict = new Dictionary<string, Tracks>();
        //periexei key to onoma tou playlists kai value to path tou 
        Dictionary<string, string> playlistDict = new Dictionary<string, string>();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            if (!Directory.Exists("\\Songs"))
            {
                Directory.CreateDirectory("Songs");
            }
            if (!Directory.Exists("\\Playlists"))
            {
                Directory.CreateDirectory("Playlists");
            }
            if (!Directory.Exists("\\Playlists"))
            {
                Directory.CreateDirectory("Tracks");
            }
            foreach (var file in Directory.EnumerateFiles("Playlists"))
            {
                string name = "";
                name = file.TrimStart("Playlists".ToCharArray());
                name = name.TrimEnd(".txt".ToCharArray());
                name = name.Remove(0,1);
                if (!playlistDict.ContainsKey(name))
                {
                    playlistDict.Add(name, file);
                    listBox2.Items.Add(name);
                }

            }
           

        }

        private void button1_Click(object sender, EventArgs e)
        {
            string[] filenames, filepaths;
            openFileDialog1.Filter = "All Media Files|*.wav;*.aac;*.wma;*.wmv;*.avi;*.mpg;*.mpeg;*.m1v;*.mp2;*.mp3;*.mpa;*.mpe;*.m3u;*.mp4;*.mov;*.3g2;*.3gp2;*.3gp;*.3gpp;*.m4a;*.cda;*.aif;*.aifc;*.aiff;*.mid;*.midi;*.rmi;*.mkv;*.WAV;*.AAC;*.WMA;*.WMV;*.AVI;*.MPG;*.MPEG;*.M1V;*.MP2;*.MP3;*.MPA;*.MPE;*.M3U;*.MP4;*.MOV;*.3G2;*.3GP2;*.3GP;*.3GPP;*.M4A;*.CDA;*.AIF;*.AIFC;*.AIFF;*.MID;*.MIDI;*.RMI;*.MKV";
            //set default path for OFD

            if (Directory.Exists("Songs")) {
                openFileDialog1.InitialDirectory = "Songs";
            }
            
            if (openFileDialog1.ShowDialog() == System.Windows.Forms.DialogResult.OK)
            {
                filenames = openFileDialog1.SafeFileNames;
                filepaths = openFileDialog1.FileNames;
                for (int i = 0; i < filenames.Length; i++)
                {
                    //elegxos wste na min uparxoun idia image files sto checkedlistbox
                    if (!SongsNPaths.ContainsKey(filenames[i]))
                    {//pairname ta names sto listbox kai names&paths sto dictionary songsnpaths
                        listBox1.Items.Add(filenames[i]);
                        SongsNPaths.Add(filenames[i], filepaths[i]);
                        //gia kathe audio file dimiourgw ena tracks obj diabazw ta metadata tou kai to apothikeuw se ena dictionary me key to onoma tou audio file
                        Tracks track = new Tracks();
                        track.path = filepaths[i];
                        track.songName = filenames[i];
                        track.readMetaData(filepaths[i]);
                        TracksDict.Add(filenames[i], track);
                        track.playingFrequency++;
                        
                        //gia to frequency pou akouei o xrhsths ena tragoudi
                        //to obj ginetai serialize mono tin prwti fora 
                        //tis alles fores aplws auxanetai to frequency
                        if (!File.Exists(@"Tracks\" + track.songName + ".txt"))
                        {
                            
                            Tracks.serializeTrack(track);
                        }
                        else
                        {
                            Tracks tr = Tracks.DeserializeTrack(track.songName);
                            
                            tr.playingFrequency++;
                            Tracks.serializeTrack(tr);
                        }

                    }

                }

            }
        }



        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            //gia kathe tragoudi p clikarw apo tin lista pairnw ta metadata kai allazw ta label text tis form1

            if (listBox1.SelectedItem != null)
            {
                title.Text = "Title: " + TracksDict[listBox1.SelectedItem.ToString()].title;
                publishedYear.Text = "Published Year: " + TracksDict[listBox1.SelectedItem.ToString()].publishedYear;           
                artist.Text = "Artist: " + TracksDict[listBox1.SelectedItem.ToString()].artistName;              
                album.Text = "Album: " + TracksDict[listBox1.SelectedItem.ToString()].album;
                duration.Text = "Duration: " + TracksDict[listBox1.SelectedItem.ToString()].duration;
                genre.Text = "Genre: " + TracksDict[listBox1.SelectedItem.ToString()].musicGenre;
                //elegxos an uparxei artwork se ena tragoudi
                pictureBox1.Image = TracksDict[listBox1.SelectedItem.ToString()].pictures;
                //dinw to path sto WMP
                clickonce = false;
            }
        }
        #region MusicButtons
        private void button2_Click(object sender, EventArgs e)
        {
            //elexgei an exei klikaristei idi mia fora to button wste na min dinei sunexws to path kai to tragoudi na xekinaei apo tin arxi

            if (listBox1.SelectedItem != null)
            {
                timer1.Start();
                axWindowsMediaPlayer1.Ctlcontrols.play();
            }
        }
        private void button7_Click(object sender, EventArgs e)
        {
            if (listBox1.SelectedItem != null)
            {
                timer1.Stop();
                label3.Text = "00:00";
                
                axWindowsMediaPlayer1.Ctlcontrols.stop();
            }
        }

        private void button10_Click(object sender, EventArgs e)
        {//elegxei tin thesi ton deiktwn an einai o deikths einai 0 o deikths paei sto telos tis listas
            if (listBox1.SelectedItem != null)
            {
                timer1.Stop();
                label3.Text = "00:00";
              
                if (listBox1.SelectedIndex != 0)
                {
                    listBox1.SelectedIndex--;
                }
                else { listBox1.SelectedIndex = listBox1.Items.Count - 1; }
            }
        }

        private void button11_Click(object sender, EventArgs e)
        {
            if (listBox1.SelectedItem != null)
            {
                timer1.Stop();
                axWindowsMediaPlayer1.Ctlcontrols.pause();
            }
        }

        private void button8_Click(object sender, EventArgs e)
        {
            if (listBox1.SelectedItem != null)
            {
                axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                axWindowsMediaPlayer1.Ctlcontrols.fastReverse();
                timer1.Stop();
                label3.Text = "00:00";
                
               
                timer1.Start();
            }
        }

        private void button9_Click(object sender, EventArgs e)
        {//i flag allazei sto teleutaio kommati otan ginetai shuffle
            flag = 0;
            //tin prwta fora 
            Button b = (Button)sender;
            if (shuffle == false)
            {
                shuffledIndexes = Helper.shuffledList(listBox1.Items.Count).ToList();
                label2.Text = "Shuffle Mode: On";
                shuffle = true;
            }else if(shuffle == true)
            {
                shuffledIndexes.Clear();
                label2.Text = "Shuffle Mode: Off";
                shuffle = false;
            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            //elegxei tin thesi ton deiktwn an einai o deikths einai sto telos tis listas tote o deikths paei stin arxi tis listas
            
            if (listBox1.SelectedItem != null && shuffle == false )
            {
                timer1.Stop();
                label3.Text = "00:00";
                
                if (listBox1.SelectedIndex != listBox1.Items.Count - 1)
                {
                    listBox1.SelectedIndex++;
                }
                else { listBox1.SelectedIndex = 0; }
            }else if(listBox1.SelectedItem != null && shuffle == true)
            {
                timer1.Stop();
                label3.Text = "00:00";
              
                this.BeginInvoke(new Action(() => {
                    if (shuffledIndexes.Count > 0)
                    {
                        listBox1.SelectedIndex = shuffledIndexes[0];
                        shuffledIndexes.RemoveAt(0);
                    }
                    else {

                        if (listBox1.SelectedIndex == listBox1.Items.Count - 1) {

                            listBox1.SelectedIndex = 0;

                        }
                        else { listBox1.SelectedIndex++; }

                        }
                    
                   
                    this.axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                }));
            }

        }
        #endregion

        private void timer1_Tick(object sender, EventArgs e)
        {

        }

        private void axWindowsMediaPlayer1_PlayStateChange(object sender, AxWMPLib._WMPOCXEvents_PlayStateChangeEvent e)
        {//check to state to WMP an einai finished ---> 8 to allazei tragoudi (bazei to epomeno)
            
            if(e.newState == 8 && shuffle == false)
            {
                if(listBox1.Items.Count > 1)
                {
                    if (listBox1.SelectedIndex != listBox1.Items.Count - 1)
                    {
                        listBox1.SelectedIndex++;
                        //listBox1.SelectedItem = listBox1.SelectedIndex;
                        

                       
                        this.BeginInvoke(new Action(() => {
                            this.axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                        }));
                        


                    }
                }
            }else if(e.newState == 8 && shuffle == true)
            {
                if (listBox1.Items.Count > 1)
                {  
                    //kathe fora p epilegetai ena tragoudi afairite o deiktis tou apo ti shuffledlist
                    int i = 0;
                    //an i lista me tous anakatemenous deiktes adeiase stamataei i mousiki
                    if (shuffledIndexes.Count == 0 && flag == -1)
                    {
                        e.newState = 1;
                        this.BeginInvoke(new Action(() => {
                            this.axWindowsMediaPlayer1.URL = null;
                        }));
                        

                    }
                    //elenxos gt ta stoixeia sunexws afairountai
                   
                    if (shuffledIndexes.Count > 0 && shuffledIndexes.Capacity == listBox1.Items.Count)
                    {
                        listBox1.SelectedIndex = shuffledIndexes[i];
                    }
                    
                   
                    if (shuffledIndexes.Count  > 0)
                    {
                        
                        shuffledIndexes.RemoveAt(i);
                        
                    }
                    i = 0;
                    if (listBox1.SelectedIndex != null && flag != -1)
                    {
                       
                        this.BeginInvoke(new Action(() => {
                            this.axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                        }));

                    }
                    if (shuffledIndexes.Count == 0) { flag--; }
                    
                }
            }
        }

        private void listBox1_SelectedValueChanged(object sender, EventArgs e)
        {
            
            if (listBox1.SelectedItem != null)
            {
                clickonce = true;
                trackBar1.Maximum = (int)TracksDict[listBox1.SelectedItem.ToString()].duration.Seconds + (int)TracksDict[listBox1.SelectedItem.ToString()].duration.TotalMinutes * 60;              
                timer1.Start();             
                axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                axWindowsMediaPlayer1.Ctlcontrols.play();

            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            axWindowsMediaPlayer1.URL = null;
            Playlist playlist = new Playlist();
                playlist.Show();
                playlist.Activate();
                //Formclosed event kanei refresh to listbox2 (playlists)
                playlist.FormClosed += new FormClosedEventHandler(playlist_FormClosed);
            
            
        }
        void playlist_FormClosed(object sender, FormClosedEventArgs e)
        {
            listBox2.Items.Clear();
            foreach (var file in Directory.EnumerateFiles("Playlists"))
            {
                playlistDict.Clear();
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
        private void button13_Click(object sender, EventArgs e)
        {//load ta tragoudia sto listbox1
            //stin sunexeia adeiazoume to dict songpaths kai to gemizoume me ta tragoudia tou playlist to idio kanw kai gia to TracksDict
            if (listBox2.SelectedItem != null)
            {
                if(axWindowsMediaPlayer1.URL != null)
                {
                    axWindowsMediaPlayer1.URL = null;
                }
                listBox1.Items.Clear();
                SongsNPaths.Clear();
                TracksDict.Clear();
                Dictionary<string, Tracks> Playlist = new Dictionary<string, Tracks>();
                Playlist = Tracks.deserializePlaylist(listBox2.SelectedItem.ToString());
                foreach (var keyValuePair in Playlist)
                {
                    listBox1.Items.Add(keyValuePair.Key);
                    SongsNPaths.Add(keyValuePair.Key, keyValuePair.Value.path);
                    TracksDict.Add(keyValuePair.Key, keyValuePair.Value);
                }

            }
        }

        private void button5_Click(object sender, EventArgs e)
        {//track deletion
            if (shuffle != true)
            {
                timer1.Stop();
                //elegxoi sxetika me tin diagrafh twn tracks
                if (listBox1.Items.Count > 1)
                {
                    if (listBox1.SelectedIndex != listBox1.Items.Count - 1)
                    {
                        TracksDict.Remove(listBox1.SelectedItem.ToString());
                        SongsNPaths.Remove(listBox1.SelectedItem.ToString());
                        listBox1.SelectedIndex++;
                        axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];

                        listBox1.Items.RemoveAt(listBox1.SelectedIndex - 1);

                    }
                    else if (listBox1.SelectedIndex == listBox1.Items.Count - 1)
                    {
                        TracksDict.Remove(listBox1.SelectedItem.ToString());
                        SongsNPaths.Remove(listBox1.SelectedItem.ToString());
                        listBox1.SelectedIndex--;
                        axWindowsMediaPlayer1.URL = SongsNPaths[listBox1.SelectedItem.ToString()];
                        listBox1.Items.RemoveAt(listBox1.SelectedIndex + 1);
                    }
                }
                else if (listBox1.Items.Count == 1)
                {
                    TracksDict.Clear();
                    SongsNPaths.Clear();
                    listBox1.Items.Clear();
                    axWindowsMediaPlayer1.Ctlcontrols.stop();
                    title.Text = "Title: ";
                    publishedYear.Text = "Published Year: ";
                    artist.Text = "Artist: ";
                    album.Text = "Album: ";
                    duration.Text = "Duration: ";
                    genre.Text = "Genre: ";
                    pictureBox1.Image = null;
                }
            }
    }

        private void button12_Click(object sender, EventArgs e)
        {
            //predetermined tracks
            //Edit button
            if (listBox1.SelectedItem != null)
            {
                //afou to kanoume allages kateutheian panw sto mp3 file prepei o WMP na stamatisei na to xrhsimopoiei
                axWindowsMediaPlayer1.URL = null;
                EditTrack et = new EditTrack(TracksDict[listBox1.SelectedItem.ToString()],true);
                et.Show();
                et.Activate();
            }
            listBox1.SelectedItem = null;
        }

        private void button14_Click(object sender, EventArgs e)
        {
            if(listBox2.SelectedItem != null)
            {   //update button
                //xamadiabazei ta metadata apo kathe track p periexetai mesa sto playlist
                //key ---> onoma tou audio file
                //value ---> Tracks object 

                Dictionary<string, Tracks> Playlist = new Dictionary<string, Tracks>();
                Playlist = Tracks.deserializePlaylist(listBox2.SelectedItem.ToString());
                foreach (var keyValuePair in Playlist)
                {
                    keyValuePair.Value.readMetaData(keyValuePair.Value.path);
                    

                }
                Tracks.serializePlaylist(listBox2.SelectedItem.ToString(), Playlist);
            }
        }

        private void listBox2_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void button15_Click(object sender, EventArgs e)
        {//clear button
            //clear to listbox1 kai katarghsh epilogwn playlist
            axWindowsMediaPlayer1.URL = null;
            listBox1.Items.Clear();
            listBox2.SelectedItem = null;
            SongsNPaths.Clear();
            TracksDict.Clear();
            title.Text = "Title: ";
            publishedYear.Text = "Published Year: ";
            artist.Text = "Artist: ";
            album.Text = "Album: ";
            duration.Text = "Duration: ";
            genre.Text = "Genre: ";
            pictureBox1.Image = null;
        }

        private void timer1_Tick_1(object sender, EventArgs e)
        {
            trackBar1.Value =(int) this.axWindowsMediaPlayer1.Ctlcontrols.currentPosition;
            int totalSeconds = (int)this.axWindowsMediaPlayer1.Ctlcontrols.currentPosition;
            //totalSeconds++;
            
            if(totalSeconds/10 != 0 && totalSeconds/60 ==0 )
            {
                label3.Text ="00:" + totalSeconds.ToString();
            }
            else { label3.Text = "00:0" + totalSeconds.ToString(); }
            if(totalSeconds/60 != 0)
            {
                
                if ((totalSeconds % 60) / 10 == 0)
                {
                    label3.Text = "0" + (totalSeconds / 60).ToString() + ":"+ "0" + (totalSeconds % 60).ToString();

                }
                else
                {
                    label3.Text = label3.Text = "0" + (totalSeconds / 60).ToString() + ":" + (totalSeconds % 60).ToString();
                }
            }
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            this.axWindowsMediaPlayer1.Ctlcontrols.currentPosition = trackBar1.Value;
        }

        private void trackBar2_Scroll(object sender, EventArgs e)
        {
            this.axWindowsMediaPlayer1.settings.volume = trackBar2.Value;
        }
        
        private void button4_Click(object sender, EventArgs e)
        {//10 favorite songs
            listofTracks = new List<Tracks>();
            listBox1.Items.Clear();
            SongsNPaths.Clear();
            TracksDict.Clear();
            foreach(var file in Directory.EnumerateFiles("Tracks"))
            {
                
                string songname = file.TrimStart("Tracks".ToCharArray());
                songname = songname.TrimEnd(".txt".ToCharArray());
                Tracks track = Tracks.DeserializeTrack(songname);
                listofTracks.Add(track);

            }
            //deinoume stin sort ton tropo me bash ton opoio tha ginei i taxinomisi diladi metaxu twn frequency ton obj
            listofTracks.Sort((track1, track2) => track1.playingFrequency.CompareTo(track2.playingFrequency));
            listofTracks.Reverse();
            
            for(int i = 0;i < 10; i++)
            {
                
                //parname ta aparaitita stoixeia sta dictionaries gia na boroun na paixoun ta tragoudia
                if ( i< listofTracks.Count )
                {
                    
                    SongsNPaths.Add(listofTracks[i].songName, listofTracks[i].path);
                    TracksDict.Add(listofTracks[i].songName, listofTracks[i]);
                    listBox1.Items.Add(listofTracks[i].songName);
                }
            }

        }

        private void button16_Click(object sender, EventArgs e)
        {
            if (Directory.Exists("Playlists"))
            {
                if(listBox2.SelectedItem != null)
                {
                    File.Delete(@"Playlists\" + listBox2.SelectedItem + ".txt");
                    listBox2.Items.Remove(listBox2.SelectedItem);
                }
            }
        }
    }
}
