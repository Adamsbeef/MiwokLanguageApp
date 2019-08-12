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

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer pronunciations;


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
            //Toast.makeText(PhrasesActivity.this,"resources released",Toast.LENGTH_SHORT).show();


        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
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

        focusManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> englishNumbers = new ArrayList<word>();


        englishNumbers.add(new word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        englishNumbers.add(new word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        englishNumbers.add(new word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        englishNumbers.add(new word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        englishNumbers.add(new word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        englishNumbers.add(new word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        englishNumbers.add(new word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        englishNumbers.add(new word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        englishNumbers.add(new word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        englishNumbers.add(new word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        final ListView rootView = findViewById(R.id.rootView);


        WordsAdapter practiceAdapter = new WordsAdapter(this, englishNumbers, R.color.category_phrases);
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

                    pronunciations = MediaPlayer.create(PhrasesActivity.this, wordPosition.getmAudioResourceId());
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
