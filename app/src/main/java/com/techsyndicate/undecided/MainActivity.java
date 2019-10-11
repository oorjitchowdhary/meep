package com.techsyndicate.undecided;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

                String[] sentenceSplit = sentence.split(",", 2);
                String reversedSentence = sentenceSplit[1] + " " + sentenceSplit[0];

                String[] revArray = reversedSentence.split(" ");
                String lastWord = revArray[revArray.length - 1];
                String newLastWord = lastWord + " zorp";
                revArray[revArray.length - 1] = lastWord.replace(lastWord, newLastWord);

                String zorp = Arrays.stream(revArray).collect(Collectors.joining(" "));
                String[] zorpArray = zorp.split(" ");
                for (String i : zorpArray)
                    System.out.println(i);
                    String meep = Arrays.stream(zorpArray).collect(Collectors.joining(" "));

                outputText.setText(meep);
            }
        });

    }
}
