package astbina.sanetna;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import astbina.sanetna.Dataclass.Datacrafstman;

public class CompleteRequestActivity extends AppCompatActivity {

    private RecyclerView request_list;
    private static DatabaseReference clientReference;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    String my_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_request);



        mAuth = FirebaseAuth.getInstance();
        my_id = mAuth.getCurrentUser().getUid();


        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Friend_List").child(my_id);
        clientReference = FirebaseDatabase.getInstance().getReference("Client");


        request_list = findViewById(R.id.rv_friend_list);
        request_list.setHasFixedSize(true);
        request_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Request_Friend_list();


    }//end onCreate

    private void Request_Friend_list() {


        FirebaseRecyclerAdapter<Datacrafstman, RequestsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Datacrafstman, RequestsViewHolder>(Datacrafstman.class, R.layout.view_more_food, RequestsViewHolder.class, mReference) {


                    @Override
                    protected void populateViewHolder(final RequestsViewHolder viewHolder, Datacrafstman model, final int position) {


                        final String friend_key = getRef(position).getKey();


                                    clientReference.child(friend_key).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {


                                            final String RequestProfileImage = dataSnapshot.child("imagepersonal").getValue().toString();
                                            final String RequestUserName = dataSnapshot.child("name").getValue().toString();
                                            final String RequestUserNumber = dataSnapshot.child("phoneNumbe").getValue().toString();
                                            //String userPhone = dataSnapshot.child("phoneNumbe").getValue().toString();

                                            viewHolder.textName.setText(RequestUserName);

                                            Picasso.get().load(RequestProfileImage).placeholder(R.drawable.profile_imagee).into(viewHolder.imageRequest);

                                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    String visitUserId = getRef(position).getKey();

                                                    Intent profileClientIntent= new Intent(CompleteRequestActivity.this, ClientProfileActivity.class);
                                                    profileClientIntent.putExtra("visitUserId", visitUserId);
                                                    profileClientIntent.putExtra("visitUserPhone", RequestUserNumber);

                                                    startActivity(profileClientIntent);


                                                }
                                            });



                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                    }//end populateViewHolder

                    @Override
                    public RequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_food, parent, false);
                        RequestsViewHolder holder = new RequestsViewHolder(view);

                        return holder;
                    }

                };//end FirebaseRecyclerAdapter

        request_list.setAdapter(firebaseRecyclerAdapter);



    }//end Request_Friend_list



    public static class RequestsViewHolder extends RecyclerView.ViewHolder{

        ImageView imageRequest;
        TextView textName;


        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);

            textName= itemView.findViewById(R.id.tvNameFood);
            imageRequest = itemView.findViewById(R.id.img);


        }

    }//end RequestsViewHolder

}
