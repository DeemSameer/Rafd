package com.example.rafad;

import android.net.Uri;

public class postinfo {
    String itemID;
    String imageID;
    String UID;
    String des;
    String cat;
    String tit;
    String isRe;
    String BID;


    public postinfo(String itemID1, String UID1, String imageUri1 , String des1 , String cat1, String tit1,String isRe){
        itemID = itemID1;
        UID=UID1;
        imageID = imageUri1;
        des = des1;
        cat=cat1;
        tit=tit1;
        this.isRe=isRe;

    }
    public postinfo(String itemID1, String UID1, String imageUri1,String BID1,String tit){
        itemID = itemID1;
        UID=UID1;
        imageID = imageUri1;
        BID=BID1;
        this.tit=tit;



    }
    public String getUID() {
        return UID;
    }

}
