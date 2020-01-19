package com.example.voiceprescription;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class DataPojo implements Serializable {

    private String name;
    private String age;
    private String symptom;
    private String diagnosis;
    private String prescription;
    private String contact;
    private String patientEmail;

    public DataPojo(){
        prescription="Rx...";
    }

    public DataPojo(String name, String age, String symptom, String diagnosis, String prescription, String contact) {
        this.name = name;
        this.age = age;
        this.symptom = symptom;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.contact = contact;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public void appendPrescription(String presc)
    {
        this.prescription=this.prescription+"\n"+presc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
