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

public class FamilyActivity extends AppCompatActivity {
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
            //Toast.makeText(FamilyActivity.this,"resources realeased",Toast.LENGTH_SHORT).show();

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


        englishNumbers.add(new word("father","әpә",R.drawable.family_father,R.raw.family_father));
        englishNumbers.add(new word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        englishNumbers.add(new word("son","angsi",R.drawable.family_son,R.raw.family_son));
        englishNumbers.add(new word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        englishNumbers.add(new word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        englishNumbers.add(new word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        englishNumbers.add(new word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        englishNumbers.add(new word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        englishNumbers.add(new word("grand mother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        englishNumbers.add(new word("grand father","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        ListView rootView = (ListView) findViewById(R.id.rootView);


        WordsAdapter practiceAdapter = new WordsAdapter(this,englishNumbers,R.color.category_family);
        rootView.setAdapter(practiceAdapter);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word wordPosition = englishNumbers.get(position);

                //release any media player object before start up any new one

                releaseMediaPlayer();

                pronunciations = MediaPlayer.create(FamilyActivity.this, wordPosition.getmAudioResourceId());
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
