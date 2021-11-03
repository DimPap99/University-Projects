package com.example.atomikiergasia2;
//used to allow firebase to serialise the user info in order to insert it
public class User {


        public String email;
        public String full_name;
        public String address;



        public  User(String email, String fname, String address){
            this.email = email;
            this.full_name = fname;
            this.address = address;
        }
    }
