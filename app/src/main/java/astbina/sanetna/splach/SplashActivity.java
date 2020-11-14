package astbina.sanetna.splach;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import astbina.sanetna.Nav_Home.Navhome;
import astbina.sanetna.R;
import astbina.sanetna.slider.UserSession;
import astbina.sanetna.slider.WelcomeActivity;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    //to get user session data
    private UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session =new UserSession(SplashActivity.this);

      Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);

      TextView appname= findViewById(R.id.appname);
      appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(7000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {

                    final SharedPreferences sharedPreferences = getSharedPreferences("mydetails", MODE_PRIVATE);
                    String email = sharedPreferences.getString("email", null);
                    //Log.e("email_s",email);
                    if(email==null)
                    {
                        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                        finish();
                    }

                    else {
                        startActivity(new Intent(SplashActivity.this, Navhome.class));
                        finish();
                    }

                }
            }, SPLASH_TIME_OUT);
        }
    }
