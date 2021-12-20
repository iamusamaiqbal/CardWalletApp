package com.example.cardwallet;

public class FieldsModel {

    public FieldsModel(int id,int cardid, String fieldname,String fieldvalue){

        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
        this.id = id;
        this.cardid = cardid;
    }

    public FieldsModel(int cardid,String fieldname,String fieldvalue){

        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
        this.cardid = cardid;
    }

    public FieldsModel(String fieldname,String fieldvalue){

        this.fieldname = fieldname;
        this.fieldvalue = fieldvalue;
    }

    int id;
    int cardid;
    String fieldname;
    String fieldvalue;

}
