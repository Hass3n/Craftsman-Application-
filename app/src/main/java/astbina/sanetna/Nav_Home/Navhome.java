package astbina.sanetna.Nav_Home;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import astbina.sanetna.Basic.BasicHome;
import astbina.sanetna.Basic.Contact;
import astbina.sanetna.Basic.Help;
import astbina.sanetna.Basic.Share;
import astbina.sanetna.Basic.Taware;
import astbina.sanetna.Basic.WebSite;
import astbina.sanetna.Chat.HomeChat;
import astbina.sanetna.ClientSettingsActivity;
import astbina.sanetna.Company_details.AdapterCompany;
import astbina.sanetna.Company_post.Postcompany;
import astbina.sanetna.CompleteRequestActivity;
import astbina.sanetna.Craftsman_details.AdapterDetailsCraftsman;
import astbina.sanetna.Craftsman_details.CraftsmanFragment;
import astbina.sanetna.Craftsman_details.CraftsmanTypeFragment;
import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.Notifcation.Token;
import astbina.sanetna.Paying.Paying;
import astbina.sanetna.PostsWork.LoginPost;
import astbina.sanetna.PostsWork.PostActivity;
import astbina.sanetna.PostsWork.ViewPagerAdapter;
import astbina.sanetna.R;
import astbina.sanetna.RequestsActivity;
import astbina.sanetna.TestAppActivity;
import astbina.sanetna.craftsman_register_login.Login;
import astbina.sanetna.splach.SplashActivity;

public class Navhome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabMenu;
    ViewPager viewPager;
    Button btn_roh;
    Intent i;
    ImageView image_p;
    TextView txt_name, txt_email;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null,my=null;
    ArrayList<PersonalDate> data_p;
    ArrayList<Datacrafstman> data;
    NavigationView navigationView;
    String mUID;

    private FirebaseAuth mAuth;

    boolean found=false;

    ArrayList<String> arrayList ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tabMenu = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Client");
        tabMenu.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);  // 0 = drink , 1=food
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // add child
        arrayList = new ArrayList<>();
        arrayList.add("sbak");
        arrayList.add("nager");
        arrayList.add("fanytakif");
        arrayList.add("amelbana");
        arrayList.add("karbah");
        arrayList.add("tanzif");
        arrayList.add("nagash");
        arrayList.add("asbsnaya");
        arrayList.add("cemarman");
        arrayList.add("hadad");
        arrayList.add("photograph");


        // call function to read data client and crftsman
        readdata_client();
        LoadChild();


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // update Token
        CheckcurrentUser();

        UpdateToken(FirebaseInstanceId.getInstance().getToken());





    }//end onCreate

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is   present.

        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tesst) {
            i = new Intent(Navhome.this, TestAppActivity.class);
            startActivity(i);
        }

        if (id == R.id.nav_profile) {
            i = new Intent(Navhome.this, ClientSettingsActivity.class);
            startActivity(i);
        }

        if (id == R.id.nav_home) {
            i = new Intent(Navhome.this, BasicHome.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            i = new Intent(Navhome.this, Help.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            i = new Intent(Navhome.this, Taware.class);
            startActivity(i);
        } else if (id == R.id.nav_tools) {
            i = new Intent(Navhome.this, WebSite.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            i = new Intent(Navhome.this, Share.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            i = new Intent(Navhome.this, PostActivity.class);
            startActivity(i);

        }

        else if (id == R.id.comp_send) {
            i = new Intent(Navhome.this, Postcompany.class);
            startActivity(i);
        }
        else if (id == R.id.chat) {
            i = new Intent(Navhome.this, HomeChat.class);
            startActivity(i);

        } else if (id == R.id.nav_requests) {
            i = new Intent(Navhome.this, RequestsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_complete_requests){
            i = new Intent(Navhome.this, CompleteRequestActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_payment){
            i = new Intent(Navhome.this, Paying.class);
            startActivity(i);

        } else if (id == R.id.nav_log_out) {
            i = new Intent(Navhome.this, Login.class);
            startActivity(i);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter
                (getSupportFragmentManager());
        //
        adapter.addFragment(new CraftsmanTypeFragment(), "المنشورات");
        adapter.addFragment(new CraftsmanFragment(), "الحرفيين");
        viewPager.setAdapter(adapter);
    }


    // read data client  from firebase-->image,name,email
    public void readdata_client() {

        txt_name = navigationView.getHeaderView(0).findViewById
                (R.id.txt_name);
        // txt_name.setText("hassan moon");
        txt_email = navigationView.getHeaderView(0).findViewById
                (R.id.txt_email);
        // txt_email.setText("hassan moon @gmail.com");
        image_p =navigationView.getHeaderView(0). findViewById
                (R.id.image_p);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data_p = new ArrayList<>();
                for (DataSnapshot dmo : dataSnapshot.getChildren()) {
                    PersonalDate data = dmo.getValue(PersonalDate.class);
                    String c_Name = data.getName();
                    String C_photo = data.getImagepersonal();
                    String c_email = data.getEmail();
                    Log.e("e", c_Name+"");
                    data_p.add( new PersonalDate(c_Name, c_email, "", "",
                            "", "", "", "", C_photo, "", "", ""));


                }

                if(data_p!=null)
                {

                    for (int i = 0; i < data_p.size(); i++) {
                        final SharedPreferences sharedPreferences =
                                getSharedPreferences("mydetails", MODE_PRIVATE);
                        String email = sharedPreferences.getString
                                ("email", null);
                        String p_email = data_p.get(i).getEmail
                                ().toString();
                        Log.e("email_c", p_email);

                        if (p_email != null && email.equals(p_email)) {
                            String image_pe = data_p.get
                                    (i).getImagepersonal().toString();
                            String p_name = data_p.get(i).getName
                                    ().toString();
                            txt_name.setText(p_name + "");
                            txt_email.setText(p_email + "");
                            Log.e("photo", image_pe + "");
                            SharedPreferences.Editor editor = getSharedPreferences("Type_login", MODE_PRIVATE).edit();
                            editor.putString("Type_log", "Client");
                            editor.apply();

                            if (image_pe.isEmpty()) {

                                image_p.setImageResource  (R.drawable.banna);

                            } else {
                                Picasso.get().load(image_pe).into
                                        (image_p);
                            }
                            return;

                        }


                    }
                }

                else {

                    LoadChild();


                }




            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Navhome.this,databaseError.getMessage
                        ().toString(),Toast.LENGTH_LONG).show();

            }
        });




    }

    // load all child
    public  void  LoadChild() {
        for (int i = 0; i < arrayList.size(); i++)
        {
            if (found==true)
            {
                return;
            }
            else {
                readdataChild(arrayList.get(i).toString());
            }

        }


    }


    // read data craftsman  from firebase-->image,name,email
    public void readdataChild(final String child )
    {

        txt_name = navigationView.getHeaderView(0).findViewById
                (R.id.txt_name);
        // txt_name.setText("hassan moon");
        txt_email = navigationView.getHeaderView(0).findViewById
                (R.id.txt_email);
        // txt_email.setText("hassan moon @gmail.com");
        image_p =navigationView.getHeaderView(0). findViewById
                (R.id.image_p);

        database=FirebaseDatabase.getInstance();
        my=database.getReference("Carfstman");

        my.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data=new ArrayList<>();


                for (DataSnapshot dmo : dataSnapshot.getChildren())
                {
                    Datacrafstman datacrafstman = dmo.getValue (Datacrafstman.class);

                    String c_Name = datacrafstman.getName();
                    String C_photo = datacrafstman.getImagepersonal();
                    String c_email = datacrafstman.getEmail();


                    data.add(new Datacrafstman(c_Name, c_email, "", "",
                            "", "", "", "", "", C_photo, "","","",""));

                }

                //Log.e("datac",data.get(0).getEmail());

                if(data!=null) {


                    readdata_craftsma(child);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext
                        (),databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();

            }
        });









    }

    public void readdata_craftsma(String child) {

        for (int i = 0; i < data.size(); i++) {
            final SharedPreferences sharedPreferences =
                    getSharedPreferences("mydetails", MODE_PRIVATE);
            String email = sharedPreferences.getString("email", null);

            String c_email = data.get(i).getEmail().toString();
            Log.e("email_c", c_email + "");
            if (c_email != null) {
                if (email.equals(c_email)) {
                    String image_c = data.get(i).getImagepersonal
                            ().toString();
                    String c_name = data.get(i).getName().toString();

                    txt_name.setText(c_name+ "");
                    txt_email.setText(c_email + "");

                    SharedPreferences.Editor editor=getSharedPreferences
                            ("Type_login",MODE_PRIVATE).edit();
                    editor.putString("Type_log","craftsman");
                    editor.apply();

                    SharedPreferences.Editor
                            editor2=getSharedPreferences("Type_Craftsman_log",MODE_PRIVATE).edit();
                    editor2.putString("Type_C",child);
                    editor2.apply();



                    Log.e("photo", image_c + "");
                    if (image_c.isEmpty()) {
                        // Picasso.get().load(R.drawable.ic_launcher_foreground).into(viewHolder.book_image);

                        image_p.setImageResource(R.drawable.banna);
                    } else {
                        Picasso.get().load(image_c).into(image_p);
                    }
                    found=true;
                    return;

                }


            }
            else {

                Toast.makeText
                        (Navhome.this,"null",Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    protected void onResume() {
        CheckcurrentUser();
        super.onResume();
    }

    public void UpdateToken(String token)
    {
        DatabaseReference ref= FirebaseDatabase.getInstance
                ().getReference("Tokens");
        Token mtoken=new Token(token);
        ref.child(mUID).setValue(mtoken);


    }

    //CheckcurrentUser state
    public void CheckcurrentUser()
    {

        // Check if user is signed in (non-null) and update UI

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {

            // stay here
            mUID=currentUser.getUid();

            SharedPreferences sp=getSharedPreferences
                    ("SP_USER",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("Current_USERID",mUID);
            editor.apply();


        }
        else
        {
            // go to main act
            startActivity(new Intent(Navhome.this,
                    SplashActivity.class));

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        CheckcurrentUser();

    }






}
