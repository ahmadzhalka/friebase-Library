package com.example.mylibrary;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.INotificationSideChannel;

public interface fireBaselistener {
    boolean updateString(String str, String path, String key);
    boolean updateInt(int i, String path, String key);
    boolean delete(String path,String key);
    boolean updateObject(Object object,String path,String key);
    void  readObjectbyId(String path,String key,fireBasecallbackObj fireBasecallbackObj);
    void readString(String path,String key,fireBasecallback fireBasecallback);
    void readInt(String path,String key,fireBasecallbackInt fireBasecallbackInt);
    void UploadFile(Uri filepath,String key);
    void deletePDF(String key);
    void downloadPDF(String s);
    interface fireBasecallback{
        void onCallback(String str);

    }
    interface fireBasecallbackInt{
        void onCallback(int i);

    }
    interface fireBasecallbackObj{
        void onCallback(Object object);

    }
}
