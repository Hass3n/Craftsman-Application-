package astbina.sanetna.Chat;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import astbina.sanetna.R;

public class HomeChat extends AppCompatActivity {


    private Button btnHerafy, btnAmeel, btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homechat);


        btnHerafy = findViewById(R.id.herafy);
        btnAmeel = findViewById(R.id.ameel);



        //start onclick btnHerafy Button

        btnHerafy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor=getSharedPreferences("Type_user",MODE_PRIVATE).edit();
                editor.putString("Type_use","craftsman");
                editor.apply();

                Intent i = new Intent(HomeChat.this, Spinner.class);
                startActivity(i);


            }
        });

        //end onclick btnHerafy Button


        //start onclick btnAmeel Button

        btnAmeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor=getSharedPreferences("Type_user",MODE_PRIVATE).edit();
                editor.putString("Type_use","client");
                editor.apply();
                Intent i = new Intent(HomeChat.this, ClientChatHome.class);
                startActivity(i);

            }
        });

        //end onclick btnAmeel Button


    }
}
