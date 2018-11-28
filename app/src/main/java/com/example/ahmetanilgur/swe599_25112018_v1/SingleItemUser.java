package com.example.ahmetanilgur.swe599_25112018_v1;

public class SingleItemUser {
    private String mUserName;
    private String mUserJob;
    private int mUserPrice;
    public SingleItemUser(String userName, String userJob, int
            userPrice) {
        mUserName = userName;
        mUserJob = userJob;
        mUserPrice = userPrice;
    }
    public String getItemUserName(){
        return mUserName;
    }
    public String getItemUserJob(){
        return mUserJob;
    }
    public int getItemUserPrice(){
        return mUserPrice;
    }
}
