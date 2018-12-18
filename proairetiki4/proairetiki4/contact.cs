using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proairetiki4
{
    [Serializable]
    class contact
    {
        

        public String onoma { get; set; }
        public String epitheto { get; set; }
        public String email { get; set; }
        public string thlefwno { get; set; }
        public String katoikia { get; set; }
        public String date { get; set; }

        public contact addOnoma(String onoma)
        {
            this.onoma = onoma;
            return this;
        }
        public contact addEpitheto(String epitheto)
        {
            this.epitheto = epitheto;
            return this;
        }
        public contact addThlefwno(string thlefwno)
        {
            this.thlefwno = thlefwno;
            return this;
        }
        public contact addEmail(String email)
        {
            this.email = email;
            return this;
        }
        public contact addKatoikia(String katoikia)
        {
            this.katoikia = katoikia;
            return this;
        }
        public contact addDate(String date)
        {
            this.date = date;
            return this;
        }

    }

}

