package com.example.cardwallet;

public class CardModel {
    int id;
    String name;
    String type;
    String frontImage;
    String backImage;


    public CardModel(String name,String type, String backImage,String frontImage){
        this.name = name;
        this.type = type;
        this.backImage = backImage;
        this.frontImage = frontImage;
    }

    public CardModel(int id, String name,String type, String backImage,String frontImage){
        this.id =id;
        this.name = name;
        this.type = type;
        this.backImage = backImage;
        this.frontImage = frontImage;
    }
}
