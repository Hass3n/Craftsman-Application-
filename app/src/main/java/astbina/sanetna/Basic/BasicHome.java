package astbina.sanetna.Basic;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import astbina.sanetna.Basic.Aboutus.DeveloperInfo;
import astbina.sanetna.R;
import astbina.sanetna.craftsman_register_login.Login;

public class BasicHome extends AppCompatActivity implements View.OnClickListener {
CardView mycard  ,mycartware, mycarthelp, mycartcontact, mycartshare, mycartprogrammer, mycartweb ;
    Intent i ;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
//        ll = (LinearLayout) findViewById(R.id.ll);

        mycard = (CardView) findViewById(R.id.bankcardId);
        mycard.setOnClickListener(this);

        mycartware=findViewById(R.id.taware);
        mycartware.setOnClickListener(this);

        mycarthelp=findViewById(R.id.help);
        mycarthelp.setOnClickListener(this);

        mycartcontact=findViewById(R.id.contact);
        mycartcontact.setOnClickListener(this);

        mycartshare=findViewById(R.id.share);
        mycartshare.setOnClickListener(this);

        mycartprogrammer=findViewById(R.id.programmer);
        mycartprogrammer.setOnClickListener(this);

        mycartweb=findViewById(R.id.web);
        mycartweb.setOnClickListener(this);



        //i = new Intent(this,ae.class);


    }
    @Override
    public void onClick(View v){

            if (v == findViewById(R.id.bankcardId)) {
                i = new Intent(BasicHome.this, Login.class);
                startActivity(i);
            }

            if (v == findViewById(R.id.taware)) {
                i = new Intent(BasicHome.this, Taware.class);
                startActivity(i);
            }


            if (v == findViewById(R.id.help)) {
                i = new Intent(BasicHome.this, Help.class);
                startActivity(i);
            }


            if (v == findViewById(R.id.contact)) {
                i = new Intent(BasicHome.this, Contact.class);
                startActivity(i);
            }


            if (v == findViewById(R.id.share)) {
                i = new Intent(BasicHome.this, Share.class);
                startActivity(i);
            }


            if (v == findViewById(R.id.programmer)) {
                i = new Intent(BasicHome.this, DeveloperInfo.class);
                startActivity(i);
            }


            if (v == findViewById(R.id.web)) {
               /* i = new Intent(BasicHome.this, WebSite.class);
                startActivity(i);*/

                WebSite.ul_name="https://wincc.000webhostapp.com/grad_pro/index.html";
                startActivity(new Intent(BasicHome.this,WebSite.class));
            }




    }
}
