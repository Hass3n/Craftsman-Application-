package astbina.sanetna.Home;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;

import astbina.sanetna.R;
import astbina.sanetna.client.clientregister;
import astbina.sanetna.craftsman_register_login.RegisterActivity;

public class Home extends AppCompatActivity implements View.OnClickListener {

    CardView btn_herafy,btn_ameel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_herafy=findViewById(R.id.Herafy);
        btn_ameel=findViewById(R.id.Ameel);
        btn_herafy.setOnClickListener(this);
        btn_ameel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_herafy){
            Intent i=new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
        else if(v==btn_ameel){
            Intent i=new Intent(this, clientregister.class);
            startActivity(i);
        }
    }
}
