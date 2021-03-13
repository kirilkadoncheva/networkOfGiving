package com.example.demo.data;

public class ImageModel {

    private String name;
    private int charityId;
    private byte[] picByte;

    public ImageModel(String name, int charityId, byte[] picByte) {

        this.name = name;
        this.charityId = charityId;
        this.picByte = picByte;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }


}
