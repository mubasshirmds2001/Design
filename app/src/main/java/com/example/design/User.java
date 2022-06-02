package com.example.design;

import android.widget.EditText;

public class User {
    public String usrnm,eml,paswrd,cpaswrd;

    public User(EditText username, EditText email, EditText password, EditText cpassword){

    }
    public User(String usrnm,String eml,String paswrd,String cpaswrd){
        this.usrnm=usrnm;
        this.eml=eml;
        this.paswrd=paswrd;
        this.cpaswrd=cpaswrd;

    }

}
