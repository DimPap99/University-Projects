using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TagLib;

namespace AlepisCsharpMp3Player
{
    [Serializable]
    public class Tracks
    {
       
        public string  songName { get; set; }
        public string path { get; set; }
        public string album = "Unknown";
        public TimeSpan duration;
        public string artistName = "Unknown";
        public string musicGenre = "Unknown";
        public uint publishedYear = 0;
        private string language = "Unknown";
        public int playingFrequency = 0;
        public string title = "Unknown";
       
        public Image pictures = Image.FromFile("noimg.png");
        public bool picExists = false;
        
        public void readMetaData(string songPath)
        {
            // read ta metadata apo kathe audio file kai assign se kathe tracks instance
            #region AssignMetaDataToTrack

            var tfile = TagLib.File.Create(songPath);
            
            this.publishedYear = tfile.Tag.Year;
            
                if (tfile.Tag.FirstAlbumArtist != null)
                {
                    this.artistName = tfile.Tag.FirstAlbumArtist;
                }
            
            this.duration = tfile.Properties.Duration;
            if (tfile.Tag.FirstGenre !=null) {
                this.musicGenre = tfile.Tag.FirstGenre; }
            if (tfile.Tag.Album != null)
            {
                this.album = tfile.Tag.Album;
            }
            if (tfile.Tag.Title != null)
            {
                this.title = tfile.Tag.Title;
            }
            #endregion
            //check an uparxei artwork
            if (tfile.Tag.Pictures.Length > 0)
            {
                this.pictures = Helper.ConvertIpicToImg(tfile.Tag.Pictures);
                picExists = true;
            }
        }

        public static void serializeTrack(Tracks track)
        {
            
                BinaryFormatter bf = new BinaryFormatter();
                Stream st = new FileStream(@"Tracks\" + track.songName + ".txt",FileMode.OpenOrCreate);
                bf.Serialize(st, track);
                st.Close();
        }

        public static void serializePlaylist(string playlistName , Dictionary<string,Tracks> playlist)
        {
            BinaryFormatter bf = new BinaryFormatter();
            Stream st = new FileStream(@"Playlists\" + playlistName + ".txt", FileMode.OpenOrCreate);
            bf.Serialize(st, playlist);
            st.Close();
        }
        public static Dictionary<string, Tracks> deserializePlaylist(string playlistName)
        {
           
            BinaryFormatter bf = new BinaryFormatter();
            Stream st = new FileStream(@"Playlists\" + playlistName + ".txt", FileMode.OpenOrCreate);
            Dictionary<string, Tracks> dic = (Dictionary<string, Tracks>)bf.Deserialize(st);
            st.Close();
            return dic;

        }
        public static Tracks DeserializeTrack(string songName)
        {
            PropertyInfo[] propertiesArray;
            BinaryFormatter bf = new BinaryFormatter();
            Stream st = new FileStream(@"Tracks\" + songName + ".txt", FileMode.OpenOrCreate);
            Tracks tr = (Tracks)bf.Deserialize(st);
           //propertiesArray = tr.GetType().GetProperties();
            st.Close();

            return tr;

        }
    }
}
