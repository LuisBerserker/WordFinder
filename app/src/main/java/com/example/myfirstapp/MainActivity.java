package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "";
    ArrayList<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDictionaryFromFile(getApplicationContext());
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editNumber = (EditText) findViewById(R.id.editNumber);
        String message = readDictionary(editText.getText().toString(), Integer.parseInt(editNumber.getText().toString()), getApplicationContext());
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public boolean readDictionaryFromFile(Context context) {
        AssetManager mngr = getAssets();
        try {
            InputStream stream = getResources().getAssets().open("Dictionary.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public String readDictionary(String comparisonLetters, Integer allowedChars, Context context) {
        AssetManager mngr = getAssets();
        ArrayList<String> allowedWords = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            if (checkWord(words.get(i), comparisonLetters, allowedChars)) {
                allowedWords.add(words.get(i));
            }
        }

        return stringArrayListToString(allowedWords);
    }

    private boolean checkWord(String Word, String comparisonLetters, Integer allowedChars) {
        if (Word.length() != allowedChars) {
            return false;
        }
        comparisonLetters = comparisonLetters.toLowerCase();
        Word = Word.toLowerCase();
        char[] charArray = Word.toCharArray();
        char[] comparisonArray = comparisonLetters.toCharArray();
        for (int i = 0; i < Word.length(); i++) {
            boolean allowed = false;
            for (int j = 0; j < comparisonArray.length; j++) {
                if (charArray[i] == comparisonArray[j]) {
                    comparisonArray[j] = ' ';
                    allowed = true;
                    break;
                }
            }
            if (!allowed) {
                return false;
            }
        }
        return true;

    }

    private String charListToString(ArrayList<Character> input) {
        String output = "";
        for (int i = 0; i < input.size(); i++) {
            output = output + String.valueOf(input.get(i));
        }

        return output;
    }

    private String stringArrayListToString(ArrayList<String> input) {
        String output = "";
        for (int i = 0; i < input.size(); i++) {
            output = output+"\n" + input.get(i);
        }
        output+="\n\n";
        return output;
    }
}
