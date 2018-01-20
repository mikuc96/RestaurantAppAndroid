package com.example.kamil.restaurant.DataBase;

import com.example.kamil.restaurant.R;

public class DishesDataBase {
    private String id;
    private String name;
    private String price;
    private String description;
    private int photo;

    public static final DishesDataBase[] dishes={
            new DishesDataBase("Zupa Pomidorowa","20", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany", R.drawable.pomidorowa),
            new DishesDataBase("Zupa Ogórkowa","30", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("Rosół","10", "descrition3",R.drawable.schabowy),
            new DishesDataBase("Krokiety","15", "descrition4",R.drawable.krokiety),
            new DishesDataBase("Rolada wieprzowa","20", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany",R.drawable.pomidorowa),
            new DishesDataBase("Filet z dorsza","30", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("Schabowy","10", "descrition3",R.drawable.schabowy),
            new DishesDataBase("Szarlotka","15", "descrition4",R.drawable.krokiety)};

    private DishesDataBase(String name, String price, String description, int photo) {
        this.name=name;
        this.price=price;
        this.description=description;
        this.photo=photo;
    }

    public String getName()
    {
        return name;
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

