package com.example.voiceprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

public class Preview extends AppCompatActivity {

    EditText name,age,symptom,diagnosis,prescription,contact;
    Button btn_save;
    //HashMap<String,String> mapToBeSent;
    DataPojo incomingDataPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);

        incomingDataPojo=(DataPojo) getIntent().getSerializableExtra("data_from_main");

        name=(EditText)findViewById(R.id.et_name);
        age=(EditText)findViewById(R.id.et_age);
        diagnosis=(EditText)findViewById(R.id.et_diag);
        symptom=(EditText)findViewById(R.id.et_symp);
        prescription=(EditText)findViewById(R.id.et_presc);
        contact=(EditText)findViewById(R.id.et_contact);
        btn_save=(Button)findViewById(R.id.btn_save);

//        Log.d("temp1===",incomingDataPojo.getName());

        name.setText(incomingDataPojo.getName());
        age.setText(incomingDataPojo.getAge());
        symptom.setText(incomingDataPojo.getSymptom());
        diagnosis.setText(incomingDataPojo.getDiagnosis());
        prescription.setText(incomingDataPojo.getPrescription());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomingDataPojo.setName(name.getText().toString());
                incomingDataPojo.setAge(age.getText().toString());
                incomingDataPojo.setSymptom(symptom.getText().toString());
                incomingDataPojo.setDiagnosis(diagnosis.getText().toString());
                incomingDataPojo.setPrescription(prescription.getText().toString());
                incomingDataPojo.setContact(contact.getText().toString());

                Intent intent=new Intent(Preview.this,SignatureActivity.class);
                intent.putExtra("data_from_preview",incomingDataPojo);
                startActivity(intent);

            }
        });
    }
}
