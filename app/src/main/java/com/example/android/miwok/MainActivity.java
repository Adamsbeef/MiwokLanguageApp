/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wiiconnect.myapplication.ColorsActivity;
import com.example.wiiconnect.myapplication.FamilyActivity;
import com.example.wiiconnect.myapplication.NumbersActivity;
import com.example.wiiconnect.myapplication.PhrasesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);
        /**
         *
         * here i extract ids of my various Textviews and save them to be referenced later
         */


        /** numbersTextView Clicks Listener and the extraction of their IDs
         */
        TextView numbersTextView = (TextView) findViewById(R.id.numbers);

        numbersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //code to make the first activity open up another activity when the textView is  clicked.

                Intent numberIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numberIntent);
            }
        });

        /** familyTextView Clicks Listener and the extraction of their IDs
         */

        TextView familyTextView = (TextView) findViewById(R.id.family);
        familyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //code to make the first activity open up another activity when the textView is  clicked.

                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });


        /** colorsTextView Clicks Listener and the extraction of their IDs
         */
        TextView coloursTextView = (TextView) findViewById(R.id.colors);

        coloursTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //code to make the first activity open up another activity when the textView is  clicked.

                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                //next line of code calls a method that takes an intent object and sets itt to the desired class activity

                startActivity(colorsIntent);
            }
        });

        /** phrasesTextView Clicks Listener and the extraction of their IDs
         */

        TextView phrasesTextView = (TextView) findViewById(R.id.phrases);

        phrasesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //code to make the first activity open up another activity when the textView is  clicked.

                Intent phrasesIntent = new Intent(MainActivity.this,PhrasesActivity.class);

                startActivity(phrasesIntent);
            }
        });


    }



}
