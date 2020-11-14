package astbina.sanetna.craftsman_register_login;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import astbina.sanetna.Home.Home;
import astbina.sanetna.R;
import astbina.sanetna.Nav_Home.Navhome;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btRegister;
    private ImageView circle1;
    TextView tvLogin,tvForgot;
    private FirebaseAuth mAuth;
    EditText ed1,ed2;
    SharedPreferences sharedPreferences;
    Button btLogin;
    // progress bar
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // init
        btLogin= findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        tvLogin = findViewById(R.id.tvLogin);
        circle1= findViewById(R.id.circle1);
        ed1=findViewById(R.id.etUsername);
        ed2=findViewById(R.id.etPassword);
        tvForgot=findViewById(R.id.tvForgot);

        tvLogin.setOnClickListener(this);
        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        // show  progressDialog while user register
        progressDialog=new ProgressDialog(this);

        sharedPreferences=getSharedPreferences("mydetails", Context.MODE_PRIVATE);

        // handle forgest password
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RecoverPassword();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == btRegister) {
            Intent a = new Intent(Login.this, Home.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(tvLogin, "login");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
            startActivity(a, activityOptions.toBundle());



        }

        if (v == btLogin) {
            progressDialog.setMessage("تسجيل الدخول ");
            progressDialog.show();
            // sign in wuth email and password
              final String email=ed1.getText().toString();
              String passwod=ed2.getText().toString();
            if(checkData(email,passwod)) {
                mAuth.signInWithEmailAndPassword(email, passwod).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            verifyEmail();

                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("email",email);
                            editor.commit();


                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this,"من فضلك تاكد من البيانات الذي ادخلتها صحيحة", Toast.LENGTH_LONG).show();

                        }




                    }
                });

            }
            else {
                progressDialog.dismiss();
                Toast.makeText(Login.this,"من فضلك تاكد من البيانات الذي ادخلتها صحيحة", Toast.LENGTH_LONG).show();

            }
        }
    }

    // verify email
    public void verifyEmail()
    {
        FirebaseUser user= mAuth.getCurrentUser();
        if(user.isEmailVerified())
        {
            startActivity(new Intent(Login.this, Navhome.class));
        }
        else
        {

            Toast.makeText(Login.this,"من فضلك تاكد من البريد الالكتروني صحيح",Toast.LENGTH_LONG).show();
        }


    }


    // recover password

    public void RecoverPassword()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("RecoverPassword");

        //set layout Manger

        LinearLayout linearLayout=new LinearLayout(this);

        final EditText Emailhint=new EditText(this);
        Emailhint.setHint("Email");
        Emailhint.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        // mak min width
        Emailhint.setMinEms(16);
        linearLayout.setPadding(10,10,10,10);
        linearLayout.addView(Emailhint);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                String email=Emailhint.getText().toString().trim();
                BeginRecover(email);


            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();


            }
        });

        builder.create().show();

    }
//recover password
    private void BeginRecover(String email) {
        progressDialog.setMessage(" ارسال رساله للميل الخاص بيك لاسترجاع كلمه السر.....");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this,"Emailsent",Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(Login.this,"failed",Toast.LENGTH_LONG).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(Login.this,"failed",Toast.LENGTH_LONG).show();

            }
        });
    }




    // check validation data
    public boolean checkData( String Email,String Password)

    {
        if (Email.equals("")) {
            ed1.setError("من فضلك ادخل البريد الالكتروني الخاص بك");
            return false;
        }
        ed1.setError(null);
        if ( Password.equals("من فضلك ادخل كلمة السر الخاص بك")) {
            ed2.setError("");
            return false;
        }
        ed2.setError(null);

        if (Password.length() < 6 && Password.equals("")) {
            ed2.setError("كلمة السر لا تقل عن ستة حروف اوارقام");
            return false;
        }
        ed2.setError(null);



        return true;


    }
}
