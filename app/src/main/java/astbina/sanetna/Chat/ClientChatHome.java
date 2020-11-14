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

import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.R;

import static com.activeandroid.Cache.getContext;

public class ClientChatHome extends AppCompatActivity {


    private RecyclerView recyclerClients;
    private ArrayList<PersonalDate> clients;
    private FirebaseAuth mAuth;
    private FirebaseUser User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_chat_home);



        recyclerClients = findViewById(R.id.recycler_clients);

        mAuth = FirebaseAuth.getInstance();
        User= mAuth.getCurrentUser();
        recyclerClients.setHasFixedSize(true);
        recyclerClients.setLayoutManager(new LinearLayoutManager(this));
        clients=new ArrayList<>();


        getAllClient();

    }


    public void getAllClient()
    {

        final FirebaseUser Fuser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Client");
        final SharedPreferences sharedPreferences = getSharedPreferences("mydetails", MODE_PRIVATE);
        final String c_email = sharedPreferences.getString("email", null);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clients.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                     PersonalDate clientdata=ds.getValue(PersonalDate.class);
                       String email=clientdata.getEmail().toString();
                       if(!(email.equals(c_email))) {
                           String image = clientdata.getImagepersonal().toString();
                           String name = clientdata.getName().toString();
                           String id = clientdata.getUid().toString();
                           clients.add(new PersonalDate(name, "", "", "", "", "", "", "", image, id, "", ""));

                       }



                }

                // recyclerView
                ClientAdapter adapterUser=new ClientAdapter(ClientChatHome.this,clients);
                recyclerClients.setAdapter(adapterUser);


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
            startActivity(new Intent(ClientChatHome.this, HomeChat.class));

        }


    }



}
