package com.example.giov13.veladoras.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.giov13.veladoras.classes.orderClass;

import java.util.ArrayList;
import java.util.List;

public class ordersOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OrderCandle";
    private static final int DATABASE_VERSION = 2;
    public static  final String TABLE_NAME = "orders";
    public static final String COLUMN_CLIENT = "client";
    public static final String COLUMN_DIRECTION = "direction";
    public static final String COLUMN_CANDLE = "candle";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_ESSENCE = "essence";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DETAILS="details";


    public ordersOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE orders(id INTEGER primary key autoincrement, client TEXT NOT NULL, direction TEXT, candle TEXT NOT NULL, quantity TEXT NOT NULL, essence TEXT, type TEXT, date TEXT NOT NULL, details TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //metodo para insertar datos a la base de datos
    public void insertData (orderClass orderClass)
    {
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null)
        {
            db.execSQL("INSERT INTO orders ( '"+COLUMN_CLIENT+"' , '"+COLUMN_DIRECTION+"' , '"+COLUMN_CANDLE+"' , '"+COLUMN_QUANTITY+"' , '"+COLUMN_ESSENCE+"' , '"+COLUMN_TYPE+"' , '"+COLUMN_DATE+"' , '"+COLUMN_DETAILS+"') VALUES ('"+orderClass.getNameOrder()+"','"+
                    orderClass.getDirection()+"','"+orderClass.getCandleTypes()+"','"+orderClass.getQuantity()+"','"+orderClass.getCandleEssence()+"','"+orderClass.getTypes()+"','"+orderClass.getDate()+"','"+orderClass.getEsoterica()+"')");

        }
    }
    public List<orderClass> findOrder(String client, String date)
    {
        SQLiteDatabase db = getReadableDatabase();
        String instruction="SELECT "+COLUMN_CANDLE+","+COLUMN_QUANTITY + ","+ COLUMN_ESSENCE+","+COLUMN_TYPE+","+COLUMN_DETAILS+" FROM "
                +TABLE_NAME+" WHERE "+COLUMN_CLIENT+"= '"+client+"' AND "+COLUMN_DATE+"= '"+date+"'";
        Cursor cursor = db.rawQuery(instruction,null);
        List<orderClass> ordersA = cursorToList(cursor);
        return ordersA;
    }
    public List<orderClass> cursorToList(Cursor cursor)
    {
        List<orderClass> order = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                orderClass ordersGive = new orderClass();
                ordersGive.setCandleTypes(cursor.getString(0));
                ordersGive.setQuantity(cursor.getString(1));
                ordersGive.setCandleEssence(cursor.getString(2));
                ordersGive.setTypes(cursor.getString(3));
                ordersGive.setEsoterica(cursor.getString(4));
                order.add(ordersGive);
            }while (cursor.moveToNext());
        }
        return order;
    }
    public List<orderClass> giveAll()
    {
        SQLiteDatabase db=getReadableDatabase();
        String instruction = "SELECT "+COLUMN_CLIENT+","+COLUMN_DATE+" FROM "+TABLE_NAME+" GROUP BY "+COLUMN_CLIENT+","+COLUMN_DATE+" ORDER BY "+COLUMN_DATE+" DESC ";
        Cursor cursor=db.rawQuery(instruction,null);
        List<orderClass> orders = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                orderClass order=new orderClass();
                //check check=new check();
                order.setNameOrder(cursor.getString(0));
                order.setDate(cursor.getString(1));
                orders.add(order);
            }while (cursor.moveToNext());
        }
        return orders;
    }
    public void delete(ArrayList<String> names,ArrayList<String> dates)
    {
        SQLiteDatabase db=getWritableDatabase();
        for (int i = 0;i<names.size();i++)
        {
            db.delete(TABLE_NAME,COLUMN_CLIENT+" = '"+names.get(i)+"' AND "+COLUMN_DATE+" = '"+dates.get(i)+"'",null);
        }
    }
    public ArrayList<String[]> findInfo(ArrayList<String> name,ArrayList<String> date)
    {
        ArrayList<String[]> ordersInfo=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
     for(int i = 0;i<name.size();i++) {
         Cursor cursor = db.rawQuery("SELECT " + COLUMN_CANDLE + "," + COLUMN_QUANTITY + "," + COLUMN_ESSENCE + "," + COLUMN_TYPE + "," + COLUMN_DETAILS + " FROM "
                 + TABLE_NAME + " WHERE " + COLUMN_CLIENT + "= '" + name.get(i) + "' AND " + COLUMN_DATE + "= '" + date.get(i) + "'", null);
         if (cursor.moveToFirst()) {
             do {
                 ordersInfo.add(new String[]{cursor.getString(1), cursor.getString(0), cursor.getString(2), cursor.getString(3), cursor.getString(4)});
             } while (cursor.moveToNext());
         }
     }
        return ordersInfo;
    }
}
