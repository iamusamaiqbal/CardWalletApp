package com.example.cardwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Cards table name
    private static final String TABLE_CARD = "cards";

    // Cards Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";
    private static final String KEY_FRONTIMAGE = "frontimage";
    private static final String KEY_BACKIMAGE = "backimage";

    //Fields table

    private static final String TABLE_FIELDS = "fields";
    private static final String KEY_CARDID = "card_id";
    private static final String KEY_FIELDNAME = "fieldname";
    private static final String KEY_FIELDVALUE = "fieldvalue";




    public SQLiteHandler(@Nullable Context context) {
        super(context, "CardDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create cards table
        String CREATE_CARD_TABLE = "CREATE TABLE " + TABLE_CARD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " VARCHAR(20),"
                + KEY_TYPE +" VARCHAR(20),"
                + KEY_FRONTIMAGE + " TEXT,"
                + KEY_BACKIMAGE + " TEXT" + ")";

        //Create fields table
        String CREATE_FIELDS_TABLE = "CREATE TABLE " + TABLE_FIELDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CARDID + " VARCHAR(20),"
                + KEY_FIELDNAME +" VARCHAR(20),"
                + KEY_FIELDVALUE +" VARCHAR(20),"
                + " FOREIGN KEY("+ KEY_CARDID +") REFERENCES " +TABLE_CARD+ "("+ KEY_ID +"))";

        db.execSQL(CREATE_CARD_TABLE);
        db.execSQL(CREATE_FIELDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIELDS);

        // Create tables again
        onCreate(db);

    }

    // Adding new card
    public int addCard(CardModel card) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, card.name);
        values.put(KEY_TYPE, card.type);
        values.put(KEY_FRONTIMAGE, card.frontImage);
        values.put(KEY_BACKIMAGE, card.backImage);

        // Inserting Row
        long id = db.insert(TABLE_CARD, null, values);
        db.close(); // Closing database connection
        return Integer.parseInt(String.valueOf(id));
    }

    // updating card
    public void updateCard(CardModel card, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, card.name);
        values.put(KEY_TYPE, card.type);
        values.put(KEY_FRONTIMAGE, card.frontImage);
        values.put(KEY_BACKIMAGE, card.backImage);

        db.update(TABLE_CARD, values, ""+KEY_ID+" = ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }

    // Getting single card
    CardModel getCard(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CARD, new String[] { KEY_ID,
                        KEY_NAME,KEY_TYPE, KEY_FRONTIMAGE, KEY_BACKIMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        CardModel card = new CardModel(
                cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_FRONTIMAGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_BACKIMAGE)));

        db.close();
        return card;
    }

    // Getting All Cards
    public List<CardModel> getAllCard(String type) {
        List<CardModel> cardList = new ArrayList<CardModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+ TABLE_CARD + " WHERE " + KEY_TYPE + " =?  ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[] { type });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CardModel card = new CardModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FRONTIMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_BACKIMAGE))
                        );
                // Adding contact to list
                cardList.add(card);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return cardList;
    }

    public boolean deleteCard(int id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag = db.delete(TABLE_CARD, KEY_ID + "=?", new String[]{String.valueOf(id)}) > 0;

        db.close();
        return flag;
    }

    // Adding new field
    public int addField(FieldsModel fields, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CARDID, id);
        values.put(KEY_FIELDNAME, fields.fieldname);
        values.put(KEY_FIELDVALUE, fields.fieldvalue);

        // Inserting Row
        db.insert(TABLE_FIELDS, null, values);
        db.close(); // Closing database connection
        return Integer.parseInt(String.valueOf(id));
    }

    // Getting All Fields
    public List<FieldsModel> getAllFields(int id) {
        List<FieldsModel> fieldsList = new ArrayList<FieldsModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM "+ TABLE_FIELDS + " WHERE " + KEY_CARDID + " =?  ORDER BY "+KEY_FIELDNAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(id)});

        if (cursor.moveToFirst()) {
            do {
                FieldsModel field = new FieldsModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CARDID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIELDNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_FIELDVALUE))
                );
                fieldsList.add(field);
            } while (cursor.moveToNext());
        }

        db.close();
        return fieldsList;
    }

    public boolean deleteFields(int id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag = db.delete(TABLE_FIELDS, KEY_ID + "=?", new String[]{String.valueOf(id)}) > 0;

        db.close();
        return flag;
    }

    // updating field
    public void updateField(FieldsModel field, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIELDNAME, field.fieldname);
        values.put(KEY_FIELDVALUE, field.fieldvalue);

        db.update(TABLE_FIELDS, values, ""+KEY_ID+" = ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }
}
