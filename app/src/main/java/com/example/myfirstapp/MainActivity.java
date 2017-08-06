package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editNumber = (EditText) findViewById(R.id.editNumber);
        String message = editText.getText().toString()+readDictionary(editText.getText().toString(),Integer.parseInt( editNumber.getText().toString()), getApplicationContext());
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public String readDictionary(String comparisonLetters, Integer allowedChars, Context context){
        AssetManager mngr = getAssets();
        try{
            InputStream stream = getResources().getAssets().open("Dictionary.txt");
            ArrayList<String> words = new ArrayList<String>() ;
            ArrayList<Character> chars = new ArrayList<Character>() ;
            ArrayList<String> allowedWords = new ArrayList<String>();
            Reader inputStreamReader = new InputStreamReader(stream);

            int data = inputStreamReader.read();
            while(data != -1){
                char theChar = (char) data;
                if (data == (int)'\n'){
                    if (checkWord(charListToString(chars), comparisonLetters, allowedChars)){
                        allowedWords.add(charListToString(chars));
                    }
                    chars.clear();
                }
                else{
                    chars.add(theChar);

                }
                data = inputStreamReader.read();
            }
            words.add(charListToString(chars));

            String output ="testeroni"+ words.get(0)+ "test"+stringArrayListToString(allowedWords)+"erino";
            inputStreamReader.close();
            return output;
        }
        catch(IOException ex){
            return "This shouldn't be here"+ex.toString();
        }
    }
    private boolean checkWord(String Word, String comparisonLetters, Integer allowedChars){
        if (Word.length() != allowedChars){
            return false;
        }
        comparisonLetters = comparisonLetters.toLowerCase();
        Word = Word.toLowerCase();
        char[] charArray = Word.toCharArray();
        char[] comparisonArray = comparisonLetters.toCharArray();
        for (int i = 0; i < Word.length(); i++)
        {
            boolean allowed = false;
            for (int j = 0; j < comparisonArray.length; j++)
            {
                if (charArray[i]==comparisonArray[j])
                {
                    comparisonArray[j] = ' ';
                    allowed = true;
                    break;
                }
            }
            if (!allowed)
            {
                return false;
            }
        }
        return true;

    }
    private String charListToString(ArrayList<Character>input){
        String output = "";
        for (int i=0; i<input.size(); i++){
            output = output + String.valueOf(input.get(i));
        }

        return output;
    }
    private String stringArrayListToString(ArrayList<String> input){
        String output ="";
        for (int i = 0; i<input.size(); i++){
            output = output + input.get(i);
        }
        return output;
    }
}
