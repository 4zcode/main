package com.example.myapplication;

public class Medicament {
    private final String title;
    private final String info;
    private final String cate;
    private final int imageResource;


    Medicament(String title, String info, String cate, int imageResource) {
        this.title = title;
        this.info = info;
        this.cate = cate;
        this.imageResource = imageResource;
    }


    String getTitle() {
        return title;
    }

    String getInfo() {
        return info;
    }

    String getCate() {
        return cate;
    }

    int getImageResource() {
        return imageResource;
    }

}
