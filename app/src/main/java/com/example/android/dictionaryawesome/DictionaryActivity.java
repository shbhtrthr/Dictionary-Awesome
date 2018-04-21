package com.example.android.dictionaryawesome;

import android.provider.UserDictionary;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import stanford.androidlib.SimpleActivity;
import stanford.androidlib.SimpleList;

public class DictionaryActivity extends SimpleActivity {


    private Map<String, String> dictionary;
    private List<String> words;

    private void readFileData() {

        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split("-");
            dictionary.put(parts[0], parts[1]);
            words.add(parts[0]);
        }
    }

    private void chooseWords(){
        Random randy = new Random();
     int randomIndex = randy.nextInt(words.size());
     String theWord = words.get(randomIndex);
     String theDefn = dictionary.get(theWord);

     List<String> defns = new ArrayList<>(dictionary.values());
     defns.remove(theDefn);
        Collections.shuffle(defns);
        defns = defns.subList(0,4);
        defns.add(theDefn);
        Collections.shuffle(defns);

        $TV(R.id.the_word).setText(theWord);
        SimpleList.with(this).setItems(R.id.word_list,defns);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        dictionary = new HashMap<>();
        words=new ArrayList<>();
        readFileData();
        chooseWords();


        ListView list = $(R.id.word_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defnClicked = parent.getItemAtPosition(position).toString();
                String theWord = $TV(R.id.the_word).getText().toString();
                String correctDefn = dictionary.get(theWord);
                if (defnClicked.equals(correctDefn)) {
                    toast("AWESOME");
                }
                else{
                    toast("OOPS");
                }
                chooseWords();
            }
        });
    }
}