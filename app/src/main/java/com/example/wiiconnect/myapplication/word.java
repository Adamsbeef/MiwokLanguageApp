package com.example.wiiconnect.myapplication;

public class word {

    /** private string variables for both languages
     * @param mDefaultlanguage and
     * @param mMiwokLanguage are not accessible for modification.
     */

    private String mDefaultLanguage;
    private String mMiwokLanguage;
    private  static  final int NO_IMAGE_PROVIDED = -1;
    private int mImageResource = NO_IMAGE_PROVIDED;
    private int mAudioResourceId;


    public word(String defaultLanguage, String miwokLanguage,int audioResourceId){
        mMiwokLanguage = miwokLanguage;
        mDefaultLanguage = defaultLanguage;
        mAudioResourceId = audioResourceId;


    }
// new word consctructor

    public word(String defaultLanguage, String miwokLanguage, int imageResource, int audioResourceId){
        mMiwokLanguage = miwokLanguage;
        mDefaultLanguage = defaultLanguage;
        mImageResource = imageResource;
        mAudioResourceId = audioResourceId;

    }

    /*method to get the default language*/

    public String getmDefaultLanguage() {
        return mDefaultLanguage;
    }
    /*method to get the default language*/

    public String getmMiwokLanguage(){
        return mMiwokLanguage;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }

    // this code tests if the image resource member variable is == no image and returns true

    public boolean hasImage() {
        return   mImageResource != NO_IMAGE_PROVIDED;
    }
}
