package astbina.sanetna.client;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.R;
import astbina.sanetna.craftsman_register_login.Login;

public class clientregister extends AppCompatActivity {
    EditText name, email, password, repassword, place,idNumber, et_phone;
    DatePicker datapick;
    Button personal_image,National_id_image,register;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
   private static DatabaseReference myRef = null;
   private static FirebaseDatabase database;
   private static String secondImge ;


    private static FirebaseAuth mAuth;
    private  static FirebaseUser currentUser;
    // Creating URI.
    Uri FilePathUri, ui, ur;
    // Creating StorageReference and DatabaseReference object.
   private static StorageReference storageReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    int Image_Request_Code2 = 8;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientregister);
        name = findViewById(R.id.c_etName);
        email = findViewById(R.id.c_etEmail);
        password=findViewById(R.id.c_etPassword);
        repassword= findViewById(R.id.c_re_etPassword);
        place=findViewById(R.id.c_etPlace);
        idNumber=findViewById(R.id.c_et_id_card);
        et_phone=findViewById(R.id.c_et_phone);
        datapick=findViewById(R.id.c_datatime);
        personal_image=findViewById(R.id.c_imqge_personnel);
        National_id_image=findViewById(R.id.c_identity_card);
        register=findViewById(R.id.c_Register);

        //read data from datapick
        String datatime = datapick.getYear() + "/" + (datapick.getMonth() + 1) + "/" + datapick.getDayOfMonth();

        // show  progressDialog while user register
        progressDialog=new ProgressDialog(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


// Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();


        database = FirebaseDatabase.getInstance();

        // Assign FirebaseStorage instance to storageReference.
        myRef = database.getReference();
        myRef = database.getReference("Client");

          //load image
        personal_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "من فضلك اختار الصور الخاصة بك لاكمال التسجيل "), Image_Request_Code);
            }
        });

        // load image personal from Gallerry

        National_id_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "من فضلك اختار الصور الخاصة بك لاكمال التسجيل "), Image_Request_Code2);


            }
        });


        // send data
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data text
                // progress bar


                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Phone = et_phone.getText().toString();
                String Password = password.getText().toString();
                String Places = place.getText().toString();
                String IDnumber = idNumber.getText().toString();
                String Repassword = repassword.getText().toString();
                if (FilePathUri != null && ui != null) {
                    if (Password.equals(Repassword)) {

                        if (checkData(Name, Email, Phone, Password, Repassword, Places, IDnumber)) {
                            CreateUser(Email, Password);

                           // startActivity(new Intent(clientregister.this, Login.class));

                        }

                    } else {

                        repassword.setError("كلمة السر يجب ان تكون مطابقة");

                    }

                }
                else {
                    Toast.makeText(clientregister.this,"plesea select Image",Toast.LENGTH_LONG).show();
                }
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
        if (requestCode == Image_Request_Code2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Log.e("image", data.getData() + "");
            ui = data.getData();


        }



    }
    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage(final String uid) {

        progressDialog.setTitle("انشاء حساب جديد ؟؟"+"");
        progressDialog.show();

        // Checking whether FilePathUri Is empty or not.

        if (FilePathUri != null&&ui!=null) {


            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // dismiss progressDialog
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "تم رفع الصور بنجاح ", Toast.LENGTH_LONG).show();





                            String id = myRef.push().getKey();
                            String Name = name.getText().toString();
                            String Email = email.getText().toString();
                            String Phone = et_phone.getText().toString();
                            String Password = password.getText().toString();
                            String Places = place.getText().toString();
                            String IDnumber = idNumber.getText().toString();

                            String datatime = datapick.getYear() + "/" + (datapick.getMonth() + 1) + "/" + datapick.getDayOfMonth();

                            @SuppressWarnings("VisibleForTests")
                            PersonalDate date = new PersonalDate(Name, Email, Phone, Password, Places, datatime, IDnumber,secondImge ,taskSnapshot.getDownloadUrl().toString(),uid,"online","noOne");
                            myRef.child(uid).setValue(date);
                            Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();



                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // dismiss progressDialog
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(clientregister.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


        } else {

            Toast.makeText(clientregister.this, "من فضلك اختار الصور الخاصة بك لاكمال التسجيل ", Toast.LENGTH_LONG).show();

        }

    }



        // check validation data
    public boolean checkData(String Name, String Email, String phone, String Password, String c_password, String Location, String idntitycad)

    {
        if (Name.equals("")) {
            name.setError("من فضلك ادخل الاسم");
            return false;
        }
        name.setError(null);

        if (Email.equals("")) {
            email.setError("من فضلك ادخل البريد الالكتروني الخاص بك");

            return false;
        }

        email.setError(null);
        if (!Email.matches(emailpattern)) {
            email.setError("هذا البريد غير مناسب");
            return false;
        }
        if (Password.length() < 6) {
            password.setError("كلمة السر لا تقل عن ستة حروف اوارقام");
            return false;
        }
        password.setError(null);

        if (c_password.length() < 6)

        {
            repassword.setError("كلمة السر لا تقل عن ستة حروف اوارقام");


            return false;
        }
        repassword.setError(null);

        if (Location.equals("")) {
            place.setError("من فضلك ادخل عنوانك");
            return false;
        }
        place.setError(null);

        if (idntitycad.length() < 14) {
            idNumber.setError(" الرقم القومي يجب ان يكون 14 رقم");
            return false;
        }
        idNumber.setError(null);

        if (phone.length() < 11) {
            et_phone.setError(" رقم التليفون  يجب ان يكون 11 رقم");
            return false;
        }
        et_phone.setError(null);

        return true;


    }
    // upload secod image

    public void UploadSecondImge() {

        if (ui != null) {


            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(ui));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(ui).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.e("image2", taskSnapshot.getDownloadUrl().toString() + "");
                    secondImge = taskSnapshot.getDownloadUrl().toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Log.e("bug", e.getLocalizedMessage() + "");

                }
            });


        }
    }
    // create user

    public void CreateUser(String Email, String password) {
        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(clientregister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid().toString();
                            UploadSecondImge();
                            UploadImageFileToFirebaseStorage(uid);
                            // Sign in success, update UI with the signed-in user's information
                            sendemailverfiy();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("error", "خطا اثناء انشاء الحساب"+ task.getException()+"");
                            Toast.makeText(clientregister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }

    // send message to verfiy email to user

    public void sendemailverfiy() {

        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(clientregister.this, "يرجي مراجعة البريد الالكتروني تم ارسال رسالة للتاكيد من حسابك", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(clientregister.this, Login.class));
                } else {
                    Toast.makeText(clientregister.this, task.getException() + "", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
