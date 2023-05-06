package com.example.mylibrary;



import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class fireBase implements fireBaselistener {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private  Context context;
    private String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/order.pdf";
    private File file = new File(stringFilePath);
    private Uri filepath;
    private final int PICK_PDF_CODE = 2342;


    public fireBase(Context context) {
        this.context = context;
    }

    @Override
    public  boolean updateString(String str, String path, String key) {
        ref = db.getReference(path);
        ref.child(key).setValue(str);
        return true;
    }

    @Override
    public boolean updateInt(int i, String path, String key) {
        ref = db.getReference(path);
        ref.child(key).setValue(i+"");
        return true;
    }


    @Override
    public boolean delete( String path,String key) {
         ref = db.getReference(path);
         ref.child(key).removeValue();
         return true;
    }

    @Override
    public boolean updateObject(Object object, String path,String key) {
        ref = db.getReference(path);
        ref.child(key).setValue(object);
        return true;
    }

    @Override
    public void readObjectbyId( String path,String key,fireBasecallbackObj fireBasecallbackObj) {
        DatabaseReference ref = db.getReference(path);
        final Object[] c = {new Object()};
        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                     c[0] = dataSnapshot.getValue(Object.class);
                    fireBasecallbackObj.onCallback(c[0]);

                } else {
                    Log.w("pttt", "ffff");
                     c[0] = new Object();
                    fireBasecallbackObj.onCallback(c[0]);

                }

            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("pttt", "Failed to read value.", error.toException());

            }
        });
    }

    @Override
    public void readString(String path, String key,fireBasecallback fireBasecallback) {

        ref = db.getReference(path);
        final String[] str = {null};

        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str[0] =snapshot.getValue(String.class);
                fireBasecallback.onCallback(str[0]);

                //data.setStr(str[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }


    @Override
    public void readInt(String path, String key,fireBasecallbackInt fireBasecallbackInt) {
        ref = db.getReference(path);
        final String[] str = {null};

        ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                str[0] =snapshot.getValue(String.class);
                int x=Integer.parseInt(str[0]);


                fireBasecallbackInt.onCallback(x);
                //data.setStr(str[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void UploadFile(Uri filepath,String key) {
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (filepath != null) {

            Date dateObject = new Date(System.currentTimeMillis());

            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference sref = storageReference.child("" +key+ ".pdf");

            sref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() )/ taskSnapshot
                                    .getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });


        }

    }

    @Override
    public void deletePDF(String key) {
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference sref = storageReference.child(key+".pdf");
        sref.delete();
    }

    @Override
    public void downloadPDF(String s) {
        FirebaseStorage firebaseStorage;
        StorageReference storageReference;
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference ref=storageReference.child(s+".pdf");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url=uri.toString();
                downloadFile(s,".pdf",DIRECTORY_DOWNLOADS,url);

            }
        });
    }


    private   void downloadFile(String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }



}
