package com.techsyndicate.undecided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    TextView outputText;
    TextInputEditText inputEditText;
    MaterialButton btn;
    private Bitmap bitmap;
    private Uri mUriPhotoTaken;
    private File mFilePhotoTaken;
    private Uri imagUrl;
    static final int REQUEST_IMAGE_CAPTURE = 1;

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
                    reversedSentence = reversedSentence + sentenceSplit[i].trim() + " ";
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
                        outputText.setText(capitalize(meep));

                    }
                }
                String normal = Arrays.stream(zorpArray).collect(Collectors.joining(" "));
                outputText.setText(capitalize(normal));
            }
        });

    }

    public void clickPic(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                mFilePhotoTaken = File.createTempFile(
                        "IMG_",  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
                // Create the File where the photo should go
                // Continue only if the File was successfully created
                if (mFilePhotoTaken != null) {
                    mUriPhotoTaken = FileProvider.getUriForFile(this,
                            "com.techsyndicate.undecided.fileprovider",
                            mFilePhotoTaken);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriPhotoTaken);

                    // Finally start camera activity
                    startActivityForResult(intent, 1);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    imagUrl = Uri.fromFile(mFilePhotoTaken);
                    bitmap = ImageHelper.loadSizeLimitedBitmapFromUri(imagUrl, getContentResolver());
                    if (bitmap != null) {
                        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(bitmap);
                        recognizeText(visionImage);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void recognizeText(FirebaseVisionImage visionImage){
        FirebaseApp.initializeApp(this);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        final Task<FirebaseVisionText> result =
                detector.processImage(visionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        System.out.println("Image processsed successfully");
                        displayText(firebaseVisionText);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Image processing failed.");
                    }
                });
    }

    private void displayText(FirebaseVisionText firebaseVisionText) {
        String resultText = firebaseVisionText.getText();
        inputEditText.setText(resultText, TextView.BufferType.EDITABLE);
    }
      
    public char lastChar(String str){
        int len = str.length();
        char[] ch = str.toCharArray();
        char c = ch[len-1];
        return c;
    }

    public String capitalize(String str) {
        char[] ch = str.toCharArray();
        System.out.println(ch);
        for(int i = 0; i<str.length(); i++) {
            if (ch[i]=='.'&& i!=str.length()-1){
                ch[i+2] = Character.toUpperCase(ch[i+2]);
            }
            if (ch[i]=='a' && ch[i+1]=='n' && ch[i+3]=='M'){
                ch[i+1] = ' ';
            }
        }
        System.out.println(ch);
        ch[0] = Character.toUpperCase(ch[0]);
        System.out.println(ch[0]);
        String st = new String(ch);
        return st;
    }
}
