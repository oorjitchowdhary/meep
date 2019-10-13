package com.techsyndicate.undecided;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    TextView outputText;
    TextInputEditText inputEditText;
    MaterialButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputtext);
        outputText = findViewById(R.id.outputtext);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputEditText.getText().toString();
                String replace = content.replace("c", "k");
                String sentence = replace.replace("w", "v");


                int count = 0;
                for (int i = 0; i < sentence.length(); i++) {
                    if (sentence.charAt(i) == ',') {
                        count=count+1;
                    }
                }
                int spacecount[];

                String[] sentenceSplit = sentence.split(",", count+1);

                String reversedSentence = "";
                for (int i =count; i>=0; i--) {
                    if (lastChar(sentenceSplit[i]) == '.' || lastChar(sentenceSplit[i]) == '?') {
                        int f = 0;
                    }
                    else
                        sentenceSplit[i] = sentenceSplit[i] + ",";
                    if (i==0) {
                        sentenceSplit[i] = sentenceSplit[i].substring(0, sentenceSplit[i].length() - 1);
                    }
                    reversedSentence = reversedSentence + " " + sentenceSplit[i];
                    System.out.println(reversedSentence);
                }

                String[] revArray = reversedSentence.split(" ");
                String lastWord = revArray[revArray.length - 1];
                String newLastWord = lastWord + " zorp.";
                revArray[revArray.length - 1] = lastWord.replace(lastWord, newLastWord);

                String zorp = Arrays.stream(revArray).collect(Collectors.joining(" "));
                String[] zorpArray = zorp.split(" ");
                for (String i : zorpArray) {
                    if (i.length() > 8) {
                        List<String> zorpList = Arrays.asList(zorpArray);
                        zorpList.set(zorpList.indexOf(i), "MEEP");
                        String meep = TextUtils.join(", ", zorpList);
                        outputText.setText(meep);

                    }
                }
                String normal = Arrays.stream(zorpArray).collect(Collectors.joining(" "));
                outputText.setText(normal);
            }
        });

    }

    public char lastChar(String str){
        int len = str.length();
        char[] ch = str.toCharArray();
        char c = ch[len-1];
        return c;
    }
}
