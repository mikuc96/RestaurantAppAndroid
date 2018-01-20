package com.example.kamil.restaurant.DataBase;

import com.example.kamil.restaurant.R;
import com.google.firebase.database.FirebaseDatabase;


public class DishesDataBase {
    private static FirebaseDatabase db=FirebaseDatabase.getInstance();
    private String id;
    private String name;
    private String price;
    private String description;
    private int photo;

    public static final DishesDataBase[] dishes={
            new DishesDataBase("1","Zupa Pomidorowa","20", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany", R.drawable.pomidorowa),
            new DishesDataBase("2","Zupa Ogórkowa","30", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("3","Rosół","10", "descrition3",R.drawable.schabowy),
            new DishesDataBase("4","Krokiety","15", "descrition4",R.drawable.krokiety),
            new DishesDataBase("5","Rolada wieprzowa","20", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany",R.drawable.pomidorowa),
            new DishesDataBase("6","Filet z dorsza","30", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("7","Schabowy","10", "descrition3",R.drawable.schabowy),
            new DishesDataBase("8","Szarlotka","15", "descrition4",R.drawable.krokiety)};

    private DishesDataBase(String id,String name, String price, String description, int photo) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.description=description;
        this.photo=photo;
    }

    public static void  sentToFirebase()
    {
        for(DishesDataBase i:dishes)
        {
            db.getReference().child("Menu").child(i.id).setValue(i);
        }
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public String getPrice()
    {
        return price;
    }

    public int getImg(){return  photo;}

    public String getDescription()
    {
        return description;
    }

    public String toString()
    {
        return this.name;
    }
    }

