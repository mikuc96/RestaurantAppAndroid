package com.example.kamil.restaurant.DataBase;

import java.util.ArrayList;

public class UserDataBase {

    static final ArrayList<UserDataBase> users = new ArrayList<UserDataBase>();

    String name, email, password;

    public UserDataBase(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName()
    {
        return this.name;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }

    public static Boolean addUser(UserDataBase us) {
        String email=us.getEmail();
        for(UserDataBase i:users) if(i.getEmail().equals(email)) return Boolean.FALSE;
        users.add(us);
        return Boolean.TRUE;
    }

    public static Boolean loginUser(String email, String password)
    {

        for(UserDataBase i:users)
        {
            if(i.getEmail().equals(email))
            {
                if(i.getPassword().equals(password)) return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


}
