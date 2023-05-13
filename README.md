# friebase-Library
1) save/get/delete string
2) save/get/delete int
3) save/get/delete object
4) save/get/delete PDF FILE

# import com.example.mylibrary.fireBase;

# tag


<img width="373" alt="צילום מסך 2023-05-06 ב-4 51 02" src="https://user-images.githubusercontent.com/119360009/236592017-86a41de1-a7ba-46c7-8125-8182e388bdd0.png">


# step 1: 


<img width="712" alt="צילום מסך 2023-05-06 ב-4 53 57" src="https://user-images.githubusercontent.com/119360009/236592052-c3ce387b-6e67-40e8-bae1-8d1851a12ea1.png">


# step 2:


<img width="659" alt="צילום מסך 2023-05-06 ב-5 00 09" src="https://user-images.githubusercontent.com/119360009/236592447-8629cc53-f810-48db-8a59-b23c98b43cce.png">


#Usage:

1.import com.example.mylibrary.fireBase;
2.fireBase fr=new fireBase(this);

#functions:

1) updateString(String, path,  key)
2) updateInt(int , path,  key) 
3) delete( path, key)
4) updateObject(Object , path, key) 
5) readObjectbyId(path,key, fireBasecallbackObj) example :


fr.readObjectbyId("j", "111", new fireBaselistener.fireBasecallbackObj() {
         @Override
         public void onCallback(Object object) {
             Log.w("ptttx", ""+object.toString());
         }
     });
     
6)  readString(String path, String key,fireBasecallback fireBasecallback) example:


        fr.readString("j", "9", new fireBaselistener.fireBasecallback() {
            @Override
            public void onCallback(String str) {
                Log.w("ptttx", "ss"+str);

            }
        });
        
7)  readInt(String path, String key,fireBasecallbackInt fireBasecallbackInt) example:


          fr.readInt("x", "1", new fireBaselistener.fireBasecallbackInt() {
            @Override
            public void onCallback(int i) {
                Log.w("ptttx", ""+i);

            }
        });
8) UploadFile( filepath, key) example :


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
