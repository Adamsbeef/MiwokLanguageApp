package com.example.wiiconnect.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.R;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer pronunciations;

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
// If the media player is not null, then it may be currently playing a sound.
        if (pronunciations != null) {

// Regardless of the current state of the media player, release its resources
// because we no longer need it.

            pronunciations.release();

// Set the media player back to null. For our code, we've decided that
// setting the media player to null is an easy way to tell that the media player
// is not configured to play an audio file at the moment.


            pronunciations = null;
            //Toast.makeText(ColorsActivity.this,"resources realeased",Toast.LENGTH_SHORT).show();

            focusManager.abandonAudioFocus(afChangeListener);
        }
    }




    private AudioManager focusManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                //if lost temporarily
                public void onAudioFocusChange(int focusChange) {
                    // do the same thing if audio focus holder can duck of will release focus shortly
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        // play the sound
                        pronunciations.pause();
                        pronunciations.seekTo(0);
                    }
                    // if regained*
                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        // start the pronunciations
                        pronunciations.start();
                    }
                    else  if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        // release android resources when focus is permanently lost

                        releaseMediaPlayer();
                    }
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordlist);


        //initialize the audioManager object
        focusManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> englishNumbers = new ArrayList<word>();
        /*this code below demonstrates how to use add an object date type word to an array list*/

//        word myWordClass = new word("one","lutti");

        /* A more compact code is this, below declaring a new object and adding it to the arrayList */

        englishNumbers.add(new word("red","weṭeṭṭi",R.drawable.color_red, R.raw.color_red));
        englishNumbers.add(new word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        englishNumbers.add(new word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        englishNumbers.add(new word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        englishNumbers.add(new word("black","kululli",R.drawable.color_black,R.raw.color_black));
        englishNumbers.add(new word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        englishNumbers.add(new word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        englishNumbers.add(new word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //int i = 0;
//        int index = 0;
        ListView rootView = (ListView) findViewById(R.id.rootView);

        // code to automatically add textViews using a while loop
//        while (index < englishNumbers.size()){
//
//            // creates a new textView
//            TextView wordView = new TextView(this);
//
//            // sets the text of the word view
//            wordView.setText(englishNumbers.get(index));
//
//            //adds the textView to the layout
//            rootView.addView(wordView);
//            index++;
//        }

//        for (int index = 0; index < englishNumbers.size(); index++){
//
//            TextView wordView = new TextView(this);
//            wordView.setText(englishNumbers.get(index));
//            rootView.addView(wordView);
//        }
        // create a new WordsAdapter object called practice adapter

        WordsAdapter practiceAdapter = new WordsAdapter(this,englishNumbers,R.color.category_colors);
        rootView.setAdapter(practiceAdapter);


        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word wordPosition = englishNumbers.get(position);

                //release any media player object before start up any new one

                releaseMediaPlayer();

                pronunciations = MediaPlayer.create(ColorsActivity.this, wordPosition.getmAudioResourceId());
                pronunciations.start();
                pronunciations.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        releaseMediaPlayer();
                    }
                });

            }
        });


    }
}
