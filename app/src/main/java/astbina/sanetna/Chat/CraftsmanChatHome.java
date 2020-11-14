package astbina.sanetna.Chat;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.R;

import static com.activeandroid.Cache.getContext;

public class CraftsmanChatHome extends AppCompatActivity {


    private RecyclerView recyclerCraftsman;
    private ArrayList<Datacrafstman> craftsmen;
    private FirebaseAuth mAuth;
    private FirebaseUser User;
    private DatabaseReference ref=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craftsman_chat_home);


        mAuth = FirebaseAuth.getInstance();
        User= mAuth.getCurrentUser();

        recyclerCraftsman = findViewById(R.id.recycler_clients);
        recyclerCraftsman.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        craftsmen=new ArrayList<>();

        getAllCraftsman();





    }

    public void getAllCraftsman()
    {
        // get all Craftsman except current user
        final SharedPreferences sharedPreferences = getSharedPreferences("mydetails", MODE_PRIVATE);
        final String c_email = sharedPreferences.getString("email", null);

        //Type_Craftsman
        SharedPreferences sh=getSharedPreferences("Type_Craftsman",MODE_PRIVATE);
        String type=sh.getString("Type_C",null);

        if(type.equals("sbak"))
        {

          ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("sbak");

        }
        else if(type.equals("nager"))
        {
            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("nager");

        }
        else if(type.equals("fanytakif"))
        {
            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("fanytakif");

        }


        else if (type.equals("amelbana")) {

            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("amelbana");


        }

        else if (type.equals("karbah")) {


            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("karbah");

        }

        else if (type.equals("tanzif")) {

            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("tanzif");


        }
        else if (type.equals("nagash")) {

            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("nagash");


        }
        else if (type.equals("asbsnaya")) {


            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("asbsnaya");


        }

        else if (type.equals("cemarman")) {

            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("cemarman");




        }

        else if (type.equals("hadad")) {


            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("hadad");



        }


        else if (type.equals("photograph")) {
            ref= FirebaseDatabase.getInstance().getReference("Carfstman").child("photograph");


        }







        final FirebaseUser Fuser= FirebaseAuth.getInstance().getCurrentUser();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                craftsmen.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Datacrafstman data=ds.getValue(Datacrafstman.class);
                        // get all Craftsman except current user
                          String email=data.getEmail().toString();
                          if(!(email.equals(c_email))) {

                              String image = data.getImagepersonal().toString();
                              String name = data.getName().toString();
                              String id = data.getUid().toString();
                              craftsmen.add(new Datacrafstman(name, "", "", "", "", "", "", "", "", image, "", id, "", ""));
                          }



                }

                // recyclerView
                CraftsmanAdapter adapterUser=new CraftsmanAdapter(CraftsmanChatHome.this,craftsmen);
                recyclerCraftsman.setAdapter(adapterUser);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getMessage()+"",Toast.LENGTH_LONG).show();

            }
        });




    }
// get menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate layout menu
        getMenuInflater().inflate(R.menu.main2,menu);


        return super.onCreateOptionsMenu(menu);
    }

    // handle click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.log_out)
        {
            mAuth.signOut();
            CheckcurrentUser();

        }
        return super.onOptionsItemSelected(item);
    }

    //CheckcurrentUser state
    public void CheckcurrentUser()
    {

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {


            // stay here
        }
        else
        {
            // go to main act
            startActivity(new Intent(CraftsmanChatHome.this, HomeChat.class));

        }


    }





}
