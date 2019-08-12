package com.example.wiiconnect.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteController;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.R;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
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
            //Toast.makeText(NumbersActivity.this,"resources released",Toast.LENGTH_SHORT).show();
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

        // focus manager global variable

        //mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
       focusManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> englishNumbers = new ArrayList<word>();
        /*this code below demonstrates how to use add an object date type word to an array list*/

//        word myWordClass = new word("one","lutti");

        /* A more compact code is this, below declaring a new object and adding it to the arrayList */

        englishNumbers.add(new word("one","lutti",R.drawable.number_one,R.raw.number_one));
        englishNumbers.add(new word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        englishNumbers.add(new word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        englishNumbers.add(new word("four","oyyisa",R.drawable.number_four,R.raw.number_three));
        englishNumbers.add(new word("five","massokka",R.drawable.number_five,R.raw.number_three));
        englishNumbers.add(new word("six","temmokka",R.drawable.number_six,R.raw.number_three));
        englishNumbers.add(new word("seven","kenekaku",R.drawable.number_seven,R.raw.number_three));
        englishNumbers.add(new word("eight","kawinta",R.drawable.number_eight,R.raw.number_three));
        englishNumbers.add(new word("nine","wo’e",R.drawable.number_nine, R.raw.number_three));
        englishNumbers.add(new word("ten","na’aacha",R.drawable.number_ten,R.raw.number_three));

        //int i = 0;
        // int index = 0;
        final ListView rootView = (ListView) findViewById(R.id.rootView);

        //click listener for the list objects.

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

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

        WordsAdapter practiceAdapter = new WordsAdapter(this,englishNumbers,R.color.category_numbers);
        rootView.setAdapter(practiceAdapter);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word wordPosition = englishNumbers.get(position);

                //release any media player object before start up any new one

                releaseMediaPlayer();

                int result = focusManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //audio focus has been gained.

                    pronunciations = MediaPlayer.create(NumbersActivity.this, wordPosition.getmAudioResourceId());
                    pronunciations.start();

                }


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
