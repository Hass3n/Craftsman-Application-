package astbina.sanetna.Company_post;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import astbina.sanetna.R;

public class Postcompany extends AppCompatActivity {

    Button btn_upload, btn_upload_;;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef=null;
    Uri FilePathUri;
    // Creating StorageReference and DatabaseReference object.
    private static StorageReference storageReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postcompany);

        btn_upload=findViewById(R.id.btn_upload_image);
        btn_upload_=findViewById(R.id.btn_upload_firebase);


// Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();


        database = FirebaseDatabase.getInstance();

        // Assign FirebaseStorage instance to storageReference.
        myRef = database.getReference();
        myRef = database.getReference("Comapny");
        // load image identity from Gallerry
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);


            }
        });

         btn_upload_.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 UploadImage();
             }
         });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // FilePathUri = data.getData();

            FilePathUri = data.getData();


            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                //  image.setImageBitmap(bitmap);


            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

   //upload image comany
     public void UploadImage()
     {


         // Checking whether FilePathUri Is empty or not.

         if (FilePathUri != null) {


             // Creating second StorageReference.
             StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
             // Adding addOnSuccessListener to second StorageReference.
             storageReference2nd.putFile(FilePathUri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                             String id = myRef.push().getKey();
                             Companypost date = new Companypost(taskSnapshot.getDownloadUrl().toString());
                             myRef.child(id).setValue(date);
                             Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                         }


                     })


                     // If something goes wrong .
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception exception) {


                             // Showing exception erro message.
                             Toast.makeText(Postcompany.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     });


         }

         else
         {


             Toast.makeText(Postcompany.this, "من فضلك اختار الصور الخاصة بك لاكمال التسجيل ", Toast.LENGTH_LONG).show();



         }




     }
}
