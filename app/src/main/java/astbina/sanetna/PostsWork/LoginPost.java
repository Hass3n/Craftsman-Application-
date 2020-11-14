package astbina.sanetna.PostsWork;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import astbina.sanetna.Company_post.Postcompany;
import astbina.sanetna.R;

public class LoginPost extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    EditText userName,userPassword;
    int count = 0;
Button button1,login ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loginpost);
        imageView = findViewById(R.id.imageView);
        button1=findViewById(R.id.sign_up);
        textView = findViewById(R.id.textView);
        userName=findViewById(R.id.user_email);
        userPassword=findViewById(R.id.user_pass);

       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));



            }
        });
*/
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((userName.getText().toString().equals("Admin"))&&userPassword.getText().toString().equals("654321")){
                    startActivity(new Intent(LoginPost.this, Postcompany.class));


                }
                else
                {

                    Toast.makeText(LoginPost.this, " Check user or password ", Toast.LENGTH_SHORT).show();

                }
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeLeft() {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.good_night_img);
                    textView.setText("Night");
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.good_morning_img);
                    textView.setText("Morning");
                    count = 0;
                }
            }

            public void onSwipeBottom() {
            }

        });
    }
}
