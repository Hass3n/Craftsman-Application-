package astbina.sanetna.Craftsman_details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import astbina.sanetna.CraftsmanSetting.Craftsmansetting;
import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.R;


public class CraftsmanData extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterDetailsCraftsman Adapter;
    ArrayList<Datacrafstman> data;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;
    EditText ed_seah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craftsman_data);
        recyclerView = findViewById(R.id.rc_craf_data);
        linearLayoutManager = new LinearLayoutManager(this);
        ed_seah = findViewById(R.id.ed_search);


        ed_seah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        // read in firebase

        add_data();


    }


    //  read data crafstman  from server
    public void add_data() {
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        Log.e("postion", position + "");
        switch (position) {
            case 0:
                myRef = database.getReference("Carfstman").child("sbak");
                break;
            case 1:
                myRef = database.getReference("Carfstman").child("nager");
                break;
            case 2:

                myRef = database.getReference("Carfstman").child("fanytakif");
                break;

            case 3:

                myRef = database.getReference("Carfstman").child("amelbana");
                break;

            case 4:

                myRef = database.getReference("Carfstman").child("karbah");
                break;

            case 5:

                myRef = database.getReference("Carfstman").child("tanzif");
                break;

            case 6:

                myRef = database.getReference("Carfstman").child("nagash");
                break;

            case 7:

                myRef = database.getReference("Carfstman").child("asbsnaya");
                break;
            case 8:

                myRef = database.getReference("Carfstman").child("cemarman");
                break;
            case 9:

                myRef = database.getReference("Carfstman").child("hadad");
                break;
            case 10:

                myRef = database.getReference("Carfstman").child("photograph");
                break;


            default:
                //  myRef.child("");
                myRef = database.getReference("ScholarshipsAndScientificAssignments ");
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                data = new ArrayList<>();

                final SharedPreferences sharedPreferences = getSharedPreferences("mydetails", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", null);


                for (DataSnapshot dmo : dataSnapshot.getChildren()) {
                    Datacrafstman datacrafstman = dmo.getValue(Datacrafstman.class);


//////////////////////////////////////////////////////////////////////////////  the new ////////////////////////////////////////
                    String c_Name = datacrafstman.getName();
                    String c_Work = datacrafstman.getType_w();
                    String c_Phone = datacrafstman.getPhoneNumbe();
                    String c_Id = datacrafstman.getUid();
//////////////////////////////////////////////////////////////////////////////  the new ////////////////////////////////////////

                    String C_photo = datacrafstman.getImagepersonal();
                    String c_email = datacrafstman.getEmail();
                    data.add(0, new Datacrafstman(c_Name, "", c_Phone, "", "", "", "", "", "", C_photo, c_Work, c_Id, "", ""));

                    Log.e("emil", c_email);


                }
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                Adapter = new AdapterDetailsCraftsman(data, getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(Adapter);
                Adapter.setOnItemclick(new AdapterDetailsCraftsman.OnItemclick() {
                    @Override
                    public void onitemclick(Datacrafstman datacrafstman, int postion) {


                        Intent craftsmanSettings = new Intent(CraftsmanData.this, Craftsmansetting.class);
                        //int position = getAdapterPosition();


                        craftsmanSettings.putExtra("pos", position);
                        craftsmanSettings.putExtra("craftsmanImage", data.get(postion).getImagepersonal());
                        craftsmanSettings.putExtra("name", data.get(postion).getName());
                        craftsmanSettings.putExtra("phone", data.get(postion).getPhoneNumbe());
                        craftsmanSettings.putExtra("type_work", data.get(postion).getType_w());
                        craftsmanSettings.putExtra("craftsmanId", data.get(postion).getUid());
                        craftsmanSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(craftsmanSettings);


                        // ////////////////////////////////////////the new //////////////////////////////////////////


                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), databaseError.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        });


    }

    // filter using search
    public void filter(String text) {
        ArrayList<Datacrafstman> filternames = new ArrayList<>();


        for (Datacrafstman s : data) {
            String name = s.getName().toString();

            if (name.toLowerCase().contains(text.toLowerCase())) {
                filternames.add(s);
            }
        }

        Adapter.filterlist(filternames);
    }
}
