package com.example.voiceprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btn_name,btn_age,btn_symp,btn_diag,btn_pres,btn_preview;

    DataPojo dataPojo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_name=(Button)findViewById(R.id.btn_name);
        btn_age=(Button)findViewById(R.id.btn_age);
        btn_symp=(Button)findViewById(R.id.btn_symptoms);
        btn_diag=(Button)findViewById(R.id.btn_digno);
        btn_pres=(Button)findViewById(R.id.btn_prescription);
        btn_preview=(Button)findViewById(R.id.btn_preview);

        dataPojo=new DataPojo();

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)!= PermissionChecker.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }else
        {
            final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");

            mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
                  //  Toast.makeText(MainActivity.this, "Ready", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onBeginningOfSpeech() {
                  //  Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onRmsChanged(float v) {
                    //
                }
                @Override
                public void onBufferReceived(byte[] bytes) {
                    //
                }
                @Override
                public void onEndOfSpeech() {
                  //  Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int i) {
                    //
                }
                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    //displaying the first match
                    if (matches != null)
                    {
                        if(matches.get(0)!=null)
                        {dataPojo.setName(matches.get(0));}
                        else
                        {dataPojo.setName("abc");}
                    }
                }
                @Override
                public void onPartialResults(Bundle bundle) {
                    //
                }
                @Override
                public void onEvent(int i, Bundle bundle) {
                    //
                }
            });
            btn_name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            mSpeechRecognizer.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                            Toast.makeText(MainActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }

            });

            final SpeechRecognizer mSpeechRecognizer2 = SpeechRecognizer.createSpeechRecognizer(this);

            final Intent mSpeechRecognizerIntent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");
            mSpeechRecognizer2.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
//
                }

                @Override
                public void onBeginningOfSpeech() {
//
                }

                @Override
                public void onRmsChanged(float v) {
//
                }

                @Override
                public void onBufferReceived(byte[] bytes) {
//
                }

                @Override
                public void onEndOfSpeech() {
//
                }

                @Override
                public void onError(int i) {
//
                }

                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //displaying the first match
                    if (matches != null)
                    {
//                    //editText.setText(matches.get(0));
//                    Toast.makeText(MainActivity.this, matches.get(0), Toast.LENGTH_SHORT).show();
                        //dataMap.put(AGE,matches.get(0));
                        if(matches.get(0)!=null)
                        {dataPojo.setAge(matches.get(0));}
                        else
                        {dataPojo.setAge("21");}
                    }
                }

                @Override
                public void onPartialResults(Bundle bundle) {
//
                }

                @Override
                public void onEvent(int i, Bundle bundle) {
//
                }
            });
            btn_age.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            mSpeechRecognizer2.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            mSpeechRecognizer2.startListening(mSpeechRecognizerIntent2);
                            Toast.makeText(MainActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });

            final SpeechRecognizer mSpeechRecognizer3 = SpeechRecognizer.createSpeechRecognizer(this);

            final Intent mSpeechRecognizerIntent3 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");
            mSpeechRecognizer3.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
//
                }

                @Override
                public void onBeginningOfSpeech() {
//
                }

                @Override
                public void onRmsChanged(float v) {
//
                }

                @Override
                public void onBufferReceived(byte[] bytes) {
//
                }

                @Override
                public void onEndOfSpeech() {
//
                }

                @Override
                public void onError(int i) {
//
                }

                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //displaying the first match
                    if (matches != null)
                    {
//                    //editText.setText(matches.get(0));
//                    Toast.makeText(MainActivity.this, matches.get(0), Toast.LENGTH_SHORT).show();
                        //dataMap.put(SYMPTOMS,matches.get(0));
                        if(matches.get(0)!=null)
                        {dataPojo.setSymptom(matches.get(0));}
                        else
                        {dataPojo.setSymptom("abc");}
                    }
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
            btn_symp.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            mSpeechRecognizer3.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            mSpeechRecognizer3.startListening(mSpeechRecognizerIntent3);
                            Toast.makeText(MainActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });

            final SpeechRecognizer mSpeechRecognizer4 = SpeechRecognizer.createSpeechRecognizer(this);

            final Intent mSpeechRecognizerIntent4 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent4.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");
            mSpeechRecognizer4.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
//
                }

                @Override
                public void onBeginningOfSpeech() {
//
                }

                @Override
                public void onRmsChanged(float v) {
//
                }

                @Override
                public void onBufferReceived(byte[] bytes) {
//
                }

                @Override
                public void onEndOfSpeech() {
//
                }

                @Override
                public void onError(int i) {
//
                }

                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //displaying the first match
                    if (matches != null)
                    {
//                    //editText.setText(matches.get(0));
//                    Toast.makeText(MainActivity.this, matches.get(0), Toast.LENGTH_SHORT).show();
                       // dataMap.put(DIAGNOSIS,matches.get(0));
                        if(matches.get(0)!=null)
                        {dataPojo.setDiagnosis(matches.get(0));}
                        else
                        {dataPojo.setDiagnosis("abc");}
                    }
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
            btn_diag.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            mSpeechRecognizer4.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            mSpeechRecognizer4.startListening(mSpeechRecognizerIntent4);
                            Toast.makeText(MainActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }
            });

            final SpeechRecognizer mSpeechRecognizer5 = SpeechRecognizer.createSpeechRecognizer(this);

            final Intent mSpeechRecognizerIntent5 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent5.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mSpeechRecognizerIntent5.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"hi-IN");
            mSpeechRecognizer5.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
//
                }

                @Override
                public void onBeginningOfSpeech() {
//
                }

                @Override
                public void onRmsChanged(float v) {
//
                }

                @Override
                public void onBufferReceived(byte[] bytes) {
//
                }

                @Override
                public void onEndOfSpeech() {
//
                }

                @Override
                public void onError(int i) {
//
                }

                @Override
                public void onResults(Bundle bundle) {
                    //getting all the matches
                    ArrayList<String> matches = bundle
                            .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                    //displaying the first match
                    if (matches != null)
                    {
//                    //editText.setText(matches.get(0));
//                    Toast.makeText(MainActivity.this, matches.get(0), Toast.LENGTH_SHORT).show();
                        //dataMap.put(PRESCRIPTION,matches.get(0));
                        if(matches.get(0)!=null)
                        {dataPojo.appendPrescription(matches.get(0));}
                        else
                        {dataPojo.appendPrescription("abc");}
                    }
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
            btn_pres.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            mSpeechRecognizer5.stopListening();
                            break;

                        case MotionEvent.ACTION_DOWN:
                            mSpeechRecognizer5.startListening(mSpeechRecognizerIntent5);
                            Toast.makeText(MainActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }

            });
            btn_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,Preview.class);
                    intent.putExtra("data_from_main",dataPojo);
                    startActivity(intent);
                }
            });
        }
    }


}
