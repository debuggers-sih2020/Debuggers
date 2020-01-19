package com.example.voiceprescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PdfActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOR_CHOOSEING_PDF =100 ;
    private TextView name,age,symptom,prescription,diagnosis;
    private Button btn_pdf_conversion;
    private DataPojo incomingDataPojo=null;
    private Bitmap incomingBitmap=null;
    private ImageView iv_signature;

    private LinearLayout llScroll;
    private Bitmap bitmap;

    private StorageReference mRef;
    private FirebaseStorage mStorageReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        iv_signature=(ImageView)findViewById(R.id.iv_signature);
        progressDialog=new ProgressDialog(PdfActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mStorageReference=FirebaseStorage.getInstance();
        mRef=mStorageReference.getReference("prescriptions/");

        btn_pdf_conversion = findViewById(R.id.btn_pdf_conver);
        llScroll = findViewById(R.id.llscroll);

        incomingDataPojo=(DataPojo)getIntent().getSerializableExtra(SignatureActivity.DATA_POHO_FROM_SIGN_ACT);
        if(getIntent().hasExtra("byteArray")) {
            //ImageView previewThumbnail = new ImageView(this);
            incomingBitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent()
                            .getByteArrayExtra("byteArray").length);
            //previewThumbnail.setImageBitmap(b);
        }

        //incomingBitmap=(Bitmap)getIntent().getParcelableExtra(SignatureActivity.SIGN_BITMAP_FROM);
        if(incomingBitmap!=null)
        {
            iv_signature.setImageBitmap(incomingBitmap);
            //Toast.makeText(this, "Bitmap recieved successfully", Toast.LENGTH_SHORT).show();
        }


        name=(TextView)findViewById(R.id.tv_name_pdf);
        age=(TextView)findViewById(R.id.tv_actual_age_pdf);
        symptom=(TextView)findViewById(R.id.tv_symtom_pdf);
        prescription=(TextView)findViewById(R.id.tv_presc_pdf);
        diagnosis=(TextView)findViewById(R.id.tv_diagnosis_pdf);
        btn_pdf_conversion=(Button) findViewById(R.id.btn_pdf_conver);

        if(incomingDataPojo!=null)
        {
            //Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, incoming_data_map.get(MainActivity.NAME), Toast.LENGTH_SHORT).show();
            name.setText(incomingDataPojo.getName());
            age.setText(incomingDataPojo.getAge());
            symptom.setText(incomingDataPojo.getSymptom());
            prescription.setText(incomingDataPojo.getPrescription());
            diagnosis.setText(incomingDataPojo.getDiagnosis());

        }

        btn_pdf_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag1=0,flag2=0;
                if(ActivityCompat.checkSelfPermission(PdfActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED)
                {
                    flag1=1;
                    ActivityCompat.requestPermissions(PdfActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

                if(ActivityCompat.checkSelfPermission(PdfActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED)
                {
                    flag2=1;
                    ActivityCompat.requestPermissions(PdfActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }

                if(flag1==0 && flag2==0)
                {
                    bitmap = loadBitmapFromView(llScroll, llScroll.getWidth(), llScroll.getHeight());
                    if(createPdf())
                    {
                        //now you can upload it on firebase
                        uploadOnFirebase();
                    }
                }
            }
        });
        }

    private void uploadOnFirebase() {
        if(ActivityCompat.checkSelfPermission(PdfActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(PdfActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else
        {
            Intent intent=new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,REQUEST_CODE_FOR_CHOOSEING_PDF);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_FOR_CHOOSEING_PDF && data!=null)
        {
            Uri pdfUri=data.getData();
            final String image_name="myprescription"+ System.currentTimeMillis();
            UploadTask uploadTask=mRef.child(image_name).putFile(pdfUri);
            progressDialog.show();
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    long total=taskSnapshot.getTotalByteCount();
                    long transferred=taskSnapshot.getBytesTransferred();
                    progressDialog.setMessage("Uploading to the database...");
//                    if(total==transferred)
//                    {
//                        progressDialog.dismiss();
//                        Toast.makeText(PdfActivity.this, "Uploaded to the database", Toast.LENGTH_SHORT).show();
//                    }
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mRef.child(image_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                            String drMail="dr_mail";
                            String patient_contact;
                            patient_contact=incomingDataPojo.getContact();
                            //Toast.makeText(PdfActivity.this, "Download url is: "+uri.toString(), Toast.LENGTH_SHORT).show();
                            FirebaseDatabase mDatabase;
                            DatabaseReference mRef;
                            mDatabase=FirebaseDatabase.getInstance();
                            mRef=mDatabase.getReference(drMail);

                            long timeInMillis=Calendar.getInstance().getTimeInMillis();
                            Date date=new Date(timeInMillis);
                            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                            String dateString=sdf.format(date);
                            String stringTimeInMillis=String.valueOf(timeInMillis);

                            DatabasePojo databasePojo=new DatabasePojo(stringTimeInMillis,dateString,uri.toString());

//                            String key=mRef.child(patient_contact).push().getKey();
                            mRef.child(patient_contact).child(stringTimeInMillis).setValue(databasePojo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.dismiss();
                                                Toast.makeText(PdfActivity.this, "Uploaded to the database", Toast.LENGTH_SHORT).show();
                                                String message="Download your prescription from the below link \n"+uri.toString();
                                                String patientMailId=incomingDataPojo.getPatientEmail();
                                                if(!patientMailId.equals("wrong email"))
                                                {
                                                    //Creating SendMail object
                                                    SendMail sm = new SendMail(PdfActivity.this, patientMailId, "E-prescription", message);

                                                    //Executing sendmail to send email
                                                    sm.execute();
                                                }
                                            }
                                        });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(PdfActivity.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private boolean createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;
        boolean isSuccess=false;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/1111Prescription.pdf";
        File filePath;
        filePath = new File(targetPdf);
     //   File filepath = new File(new File(Environment.getExternalStorageDirectory(),"Onlinfo"), "pdffromlayout.pdf" );
        //filePath = new File(Environment.getExternalStorageDirectory().getPath()+"/pdffromScroll.pdf");
        try {
            document.writeTo(new FileOutputStream(filePath));
            // close the document
            document.close();
            Toast.makeText(this, "PDF is created as 1111Prescription.pdf in the mobile", Toast.LENGTH_LONG).show();
            isSuccess=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

//        // close the document
//        document.close();
//        Toast.makeText(this, "PDF is created as pdffromlayout.pdf in the mobile", Toast.LENGTH_LONG).show();

     //   openGeneratedPDF();
        return isSuccess;
    }

//    private void openGeneratedPDF(){
//        File file = new File("/sdcard/pdffromlayout.pdf");
//        //File file = new File(new File(Environment.getExternalStorageDirectory(),"Onlinfo"), "pdffromlayout.pdf" );
//        if (file.exists())
//        {
//            Intent intent=new Intent(Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
//            intent.setDataAndType(uri, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            try
//            {
//                startActivity(intent);
//            }
//            catch(ActivityNotFoundException e)
//            {
//                Toast.makeText(PdfActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

}
