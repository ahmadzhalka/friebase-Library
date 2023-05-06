package com.example.friebase_library;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mylibrary.fireBase;
import com.example.mylibrary.fireBaselistener;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/order.pdf";
    private File file = new File(stringFilePath);
    private Uri filepath;
    private Button createPdf;
    private Button createorder;

    private final int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireBase fr=new fireBase(this);
        createPdf=findViewById(R.id.createPdf);
        createorder=findViewById(R.id.createorder);

        createorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFile();

            }
        });
        createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr.downloadPDF("j");

            }
        });
       // fr.deletePDF("j");


      /*// fr.updateString("z","j","9");
       // Worker w=new Worker( "name", "www",111, 222) ;
        //fr.updateObject(w,"j",w.getId()+"");
       // fr.delete("j","8");
        fr.updateInt(1,"x","1");
        fr.readInt("x", "1", new fireBaselistener.fireBasecallbackInt() {
            @Override
            public void onCallback(int i) {
                Log.w("ptttx", ""+i);

            }
        });

     fr.readString("j", "9", new fireBaselistener.fireBasecallback() {
            @Override
            public void onCallback(String str) {
                Log.w("ptttx", "ss"+str);

            }
        });
     fr.readObjectbyId("j", "111", new fireBaselistener.fireBasecallbackObj() {
         @Override
         public void onCallback(Object object) {
             Log.w("ptttx", ""+object.toString());

         }
     });*/
        //selectFile();




    }
    public void selectFile(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                filepath=data.getData();
                fireBase fr=new fireBase(this);
                fr.UploadFile(filepath,"j");
            } else
                Toast.makeText(this, "NO FILE CHOSEN", Toast.LENGTH_SHORT).show();

        }
    }
}