package astbina.sanetna.craftsman_register_login;


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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

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
import java.util.ArrayList;

import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.R;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    public String t_c;
    public int c_postion;
    private Animation animation;
    private Menu menu;
    Spinner spinner;
    Button btnRegister, btnIdenity, btnImage, btncardwork;
    DatePicker datapick;
    EditText name, email, password, repassword, place, date, idNumber, et_phone;
    private static DatabaseReference myRef = null;
    private static FirebaseDatabase database;
    static String secondImge = "";
    static String threeImage = "";
    // progress bar
    ProgressDialog progressDialog;
    private static FirebaseAuth mAuth;
    // Creating URI.
    Uri FilePathUri, ui, ur;
    // Creating StorageReference and DatabaseReference object.
   private static StorageReference storageReference;
    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;
    int Image_Request_Code2 = 8;
    int Image_Request_Code3 = 9;
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        rlayout.setAnimation(animation);
        spinner = findViewById(R.id.Spinner);
        btnRegister = findViewById(R.id.bt_Register);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        repassword = findViewById(R.id.re_etPassword);
        place = findViewById(R.id.etPlace);
        datapick = findViewById(R.id.datatime);
        // date=findViewById(R.id.date);
        et_phone = findViewById(R.id.et_phone);
        idNumber = findViewById(R.id.et_id_card);
        btnIdenity = findViewById(R.id.et_identity_card);
        btnImage = findViewById(R.id.imqge_id_personnel);
        btncardwork = findViewById(R.id.btn_job_card);

        // show  progressDialog while user register
        progressDialog=new ProgressDialog(this);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // add data to spinner

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("سباك");
        arrayList.add("نجار");
        arrayList.add("فني تكييف");
        arrayList.add("عامل بناء");
        arrayList.add("كهربائي");
        arrayList.add("تنظيف");
        arrayList.add("نقاش");
        arrayList.add(" عشب صناعي");
        arrayList.add("كاميرات مراقبه");
        arrayList.add("حداد");
        arrayList.add("مصور");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    t_c = parent.getItemAtPosition(0).toString();
                    c_postion = 0;
                } else if (position == 1) {
                    t_c = parent.getItemAtPosition(1).toString();
                    c_postion = 1;
                } else if (position == 2) {
                    t_c = parent.getItemAtPosition(2).toString();
                    c_postion = 2;
                }

                else if (position == 3) {
                    t_c = parent.getItemAtPosition(3).toString();
                    c_postion = 3;
                }
                else if (position == 4) {
                    t_c = parent.getItemAtPosition(4).toString();
                    c_postion = 4;
                }
                else if (position == 5) {
                    t_c = parent.getItemAtPosition(5).toString();
                    c_postion = 5;
                }
                else if (position == 6) {
                    t_c = parent.getItemAtPosition(6).toString();
                    c_postion = 6;
                }

                else if (position == 7) {
                    t_c = parent.getItemAtPosition(7).toString();
                    c_postion = 7;
                }
                else if (position == 8) {
                    t_c = parent.getItemAtPosition(8).toString();
                    c_postion = 8;
                }

                else if (position == 9) {
                    t_c = parent.getItemAtPosition(9).toString();
                    c_postion = 9;
                }

                else if (position == 10) {
                    t_c = parent.getItemAtPosition(10).toString();
                    c_postion = 10;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //read data from datapick
        String datatime = datapick.getYear() + "/" + (datapick.getMonth() + 1) + "/" + datapick.getDayOfMonth();


        //  create user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();


// Assign FirebaseStorage instance to storageReference.
        storageReference = FirebaseStorage.getInstance().getReference();


        database = FirebaseDatabase.getInstance();

        // Assign FirebaseStorage instance to storageReference.
        myRef = database.getReference();
        myRef = database.getReference("Carfstman");
        // load image identity from Gallerry
        btnIdenity.setOnClickListener(new View.OnClickListener() {
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

        // load image personal from Gallerry

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code2);


            }
        });

        // load image  cardwork from Gallerry
        btncardwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code3);

            }
        });

        // send data
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data text

                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Phone = et_phone.getText().toString();
                String Password = password.getText().toString();
                String Places = place.getText().toString();
                String IDnumber = idNumber.getText().toString();
                String Repassword = repassword.getText().toString();
               // String uid=user.getUid().toString();

                if (FilePathUri != null&&ui!=null&&ur!=null) {
                    if (Password.equals(Repassword)) {

                        if (checkData(Name, Email, Phone, Password, Repassword, Places, IDnumber)) {
                            CreateUser(Email, Password);



                            //startActivity(new Intent(RegisterActivity.this, Login.class));

                        }
                    } else {

                        repassword.setError("كلمة السر يجب ان تكون مطابقة");

                    }
                }
                else {

                    Toast.makeText(RegisterActivity.this,"من فضلك اختار الصور الخاصة بك لاكمال التسجيل ",Toast.LENGTH_LONG).show();
                }
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        if (requestCode == Image_Request_Code3 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Log.e("image", data.getData() + "");
            ur = data.getData();


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

        // Checking whether FilePathUri Is empty or not.
// progress bar
        progressDialog.setTitle("انشاء حساب جديد ؟؟");

         progressDialog.show();
        if (FilePathUri != null&&ui!=null&&ur!=null) {


            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "تم تحميل الصور بنجاح ", Toast.LENGTH_LONG).show();



                            String id = myRef.push().getKey();
                            String Name = name.getText().toString();
                            String Email = email.getText().toString();
                            String Phone = et_phone.getText().toString();
                            String Password = password.getText().toString();
                            String Places = place.getText().toString();
                            String IDnumber = idNumber.getText().toString();
                            String onlineState ="online";


                            String datatime = datapick.getYear() + "/" + (datapick.getMonth() + 1) + "/" + datapick.getDayOfMonth();
                            if (c_postion == 0) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,onlineState,"noOne");
                                myRef.child("sbak").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح  ", Toast.LENGTH_LONG).show();
                            } else if (c_postion == 1) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("nager").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح", Toast.LENGTH_LONG).show();
                            }
                            else if (c_postion == 2) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("fanytakif").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }

                            else if (c_postion == 3) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("amelbana").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }


                            else if (c_postion == 4) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("karbah").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }


                            else if (c_postion == 5) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("tanzif").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }

                            else if (c_postion == 6) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("nagash").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }
                            else if (c_postion == 7) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("asbsnaya").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }

                            else if (c_postion == 8) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("cemarman").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }


                            else if (c_postion == 9) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("hadad").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }
                            else if (c_postion == 10) {

                                @SuppressWarnings("VisibleForTests")
                                Datacrafstman date = new Datacrafstman(Name, Email, Phone, Password, Places, datatime, IDnumber, taskSnapshot.getDownloadUrl().toString(), threeImage, secondImge, t_c,uid,"online","noOne");
                                myRef.child("photograph").child(uid).setValue(date);
                                Toast.makeText(getApplicationContext(), "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                            }
















                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {


                            // Showing exception erro message.
                            Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


        } else {

            Toast.makeText(RegisterActivity.this, "من فضلك اختار الصور الخاصة بك لاكمال التسجيل ", Toast.LENGTH_LONG).show();

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
    // upload three image

    public void UploadThreeImge() {

        if (ur != null) {


            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(ur));
            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(ur).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Log.e("image3", taskSnapshot.getDownloadUrl().toString() + "");
                    threeImage = taskSnapshot.getDownloadUrl().toString();

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
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid=user.getUid().toString();
                            UploadSecondImge();
                            UploadThreeImge();
                            UploadImageFileToFirebaseStorage(uid);
                            // Sign in success, update UI with the signed-in user's information
                            sendemailverfiy();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("error", "خطا في انشاء الحساب "+ task.getException()+"");
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
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
                    Toast.makeText(RegisterActivity.this, "يرجي مراجعة البريد الالكتروني تم ارسال رسالة للتاكيد من حسابك", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, Login.class));
                } else {
                    Toast.makeText(RegisterActivity.this, task.getException() + "", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    }