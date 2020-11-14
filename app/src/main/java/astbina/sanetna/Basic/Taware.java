package astbina.sanetna.Basic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import astbina.sanetna.R;

public class Taware extends AppCompatActivity implements View.OnClickListener {

    Button water,health,kahraba,gaj,sarf;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_taware);
    water=findViewById(R.id.water);
    health=findViewById(R.id.health);

    kahraba=findViewById(R.id.kahraba);
    gaj=findViewById(R.id.gaj);
    sarf=findViewById(R.id.sarf);


    water.setOnClickListener(this);
    health.setOnClickListener(this);
sarf.setOnClickListener(this);
    kahraba=findViewById(R.id.kahraba);
    gaj=findViewById(R.id.gaj);
}

@Override
public void onClick(View v) {
    if(v==water){
            Intent callIntent =new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:125"));
            if (ActivityCompat.checkSelfPermission(Taware.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);
        }

    else if(v==health){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:127"));
        if (ActivityCompat.checkSelfPermission(Taware.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);

    }
    if(v==kahraba){
        Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:121"));
        if (ActivityCompat.checkSelfPermission(Taware.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
    else if(v==gaj){
        Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:149"));
        if (ActivityCompat.checkSelfPermission(Taware.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    else if(v==sarf){
        Intent callIntent =new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:175"));
        if (ActivityCompat.checkSelfPermission(Taware.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
}
}
