package com.example.speechtotextandtexttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE = 100;
    TextView textView;
    TextToSpeech t1;

    Button b1,mvoicebtn;
    EditText e1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mvoicebtn = findViewById(R.id.buttonmic);
        textView = findViewById(R.id.textview);
        b1 = findViewById(R.id.listen);
        e1 = findViewById(R.id.e1);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speak = e1.getText().toString();
                Toast.makeText(getApplicationContext(), speak, Toast.LENGTH_SHORT).show();
               t1.speak(speak,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


    }

    public void OnPause()
    {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }





public void btnspeech(View view) {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Need to speak something");
    try {
        startActivityForResult(intent, 1);
    } catch (ActivityNotFoundException a) {
        Toast.makeText(getApplicationContext(),
                "Sorry your device not supported",
                Toast.LENGTH_SHORT).show();


    }

}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1:


                if(resultCode==RESULT_OK&&null!=data)
                {
                    ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                }

                break;
        }
    }

}
