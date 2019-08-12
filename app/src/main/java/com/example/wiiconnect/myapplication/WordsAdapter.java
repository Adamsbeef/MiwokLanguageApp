package com.example.wiiconnect.myapplication;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;

import java.util.ArrayList;

public class WordsAdapter extends ArrayAdapter<word> {
    // int id for color resource

    private int mcolorResourceId;

    public WordsAdapter(Activity context, ArrayList<word> words, int colorResourceId){

        super(context,0,words);
        mcolorResourceId = colorResourceId;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_for_list_adapter,parent,false);
        }
        word currentWord = getItem(position);

        TextView defaultTextView = (TextView)listItemView.findViewById(R.id.englishTextView);
        defaultTextView.setText(currentWord.getmDefaultLanguage());


        TextView theMiwokTextView = (TextView)listItemView.findViewById(R.id.miwokTextView);
        theMiwokTextView.setText(currentWord.getmMiwokLanguage());

        // modifying this inflater to inflate an image view along with the textViews
        ImageView action_ImageView = (ImageView)listItemView.findViewById(R.id.imageDisplayer);

        if (currentWord.hasImage()){

            action_ImageView.setImageResource(currentWord.getmImageResource());
            action_ImageView.setVisibility(View.VISIBLE);
        }
        else {
            action_ImageView.setVisibility(View.GONE);

        }

        LinearLayout text_Container = (LinearLayout) listItemView.findViewById(R.id.textViewContainer);

        //get a color from the resource id and set it to the text_containers color

        int color = ContextCompat.getColor(getContext(),mcolorResourceId);
        text_Container.setBackgroundColor(color);

        return listItemView;
    }
}
