package astbina.sanetna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;

import astbina.sanetna.Dataclass.Datacrafstman;
import de.hdodenhof.circleimageview.CircleImageView;

public class TestAppActivity extends AppCompatActivity {

    public String user_Id="" ;

    private CircleImageView userProfileImage;
    private TextView userProfileName, userRating, userPhone, typeWorks;
    private ProgressDialog loadingBar;


    private DatabaseReference rootReference, clientReference;
    private StorageReference userProfileImageReference;
    private FirebaseAuth mAuth;
    private String my_id;

    private static final int galleryPick = 2;

    private RecyclerView request_list;
    private static DatabaseReference craftsReference;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference, mReceiverRef;

    ArrayList<String> arrayListCra;
    boolean found=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_app);


        mAuth = FirebaseAuth.getInstance();
        my_id = mAuth.getCurrentUser().getUid();
        rootReference = FirebaseDatabase.getInstance().getReference();
        craftsReference = FirebaseDatabase.getInstance().getReference("Carfstman");
        clientReference = FirebaseDatabase.getInstance().getReference("Client");
        userProfileImageReference = FirebaseStorage.getInstance().getReference().child("Profile Images Craftsman");
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Rate_List").child(my_id);


        userProfileImage = findViewById(R.id.profile_image_craftsman_settings);
        userProfileName = findViewById(R.id.profile_name_craftsman_settings);
        userRating = findViewById(R.id.rating_craftsman_settings);
        userPhone = findViewById(R.id.phone_craftsman_settings);
        typeWorks = findViewById(R.id.type_work_craftsman_settings);
        loadingBar = new ProgressDialog(this);


        request_list = findViewById(R.id.rateing_list_craftsman_settings);
        request_list.setHasFixedSize(true);
        request_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        // add child to array list
        arrayListCra = new ArrayList<>();
        arrayListCra.add("sbak");
        arrayListCra.add("nager");
        arrayListCra.add("fanytakif");
        arrayListCra.add("amelbana");
        arrayListCra.add("karbah");
        arrayListCra.add("tanzif");
        arrayListCra.add("nagash");
        arrayListCra.add("asbsnaya");
        arrayListCra.add("cemarman");
        arrayListCra.add("hadad");
        arrayListCra.add("photograph");



        retrieveUserInfo();

        Request_Friend_list();


        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPick);

            }
        });



    }//end onCreate




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();/////

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                loadingBar.setTitle("Set Profile Image");
                loadingBar.setMessage("Please wait, your profile image is updating");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                final Uri resultUri = result.getUri();

                StorageReference filePath = userProfileImageReference.child(my_id + ".jpg");


               // Picasso.get().load(resultUri).placeholder(R.drawable.profile_imagee).into(userProfileImage);


                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(TestAppActivity.this, "Profile Image Uploaded Successfully", Toast.LENGTH_LONG).show();
                        Log.e("url tom",taskSnapshot.getDownloadUrl().toString() );
                        HashMap<String, Object> results = new HashMap<>();
                        results.put("imagepersonal",taskSnapshot.getDownloadUrl().toString() );


                        for (int i = 0; i < arrayListCra.size(); i++) {


                            String stChild = arrayListCra.get(i).toString();

                             craftsReference.child(stChild).child(my_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("imagepersonal")))) {


                                        user_Id = dataSnapshot.child("uid").getValue().toString();


                                        for (int i = 0; i < arrayListCra.size(); i++) {

                                            String stChild = arrayListCra.get(i).toString();


                                            if (user_Id.equals(my_id)){

                                                craftsReference.child(stChild).child(my_id).updateChildren(results);
                                                loadingBar.dismiss();
                                                Toast.makeText(TestAppActivity.this, "update", Toast.LENGTH_SHORT).show();
                                                return;


                                            }


                                        }




                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }





























//                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//
//                        //isSuccessful()/////////////////////////////////////////////////////////////
//                        while (!uriTask.isComplete()) ;
//                        Uri downloadUri = uriTask.getResult();

                        //isSuccessful()/////////////////////////////////////////////////////////////
//                        if (uriTask.isComplete()) {
//
//
//
//
//                        } else {
//
//                            loadingBar.dismiss();
//                            Toast.makeText(TestAppActivity.this, "Error: ", Toast.LENGTH_LONG).show();
//
//                        }

                    }
                });

            }
        }


    }//end onActivityResult


    private void retrieveUserInfo() {

        for (int i = 0; i < arrayListCra.size(); i++) {


                String stChild = arrayListCra.get(i).toString();

                craftsReference.child(stChild).child(my_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("imagepersonal")))) {

                            String userImage = dataSnapshot.child("imagepersonal").getValue().toString();
                            String userName = dataSnapshot.child("name").getValue().toString();
                            String crafPhone = dataSnapshot.child("phoneNumbe").getValue().toString();
                            String typeWork = dataSnapshot.child("type_w").getValue().toString();
                            //user_Id = dataSnapshot.child("uid").getValue().toString();

                            Picasso.get().load(userImage).placeholder(R.drawable.profile_imagee).into(userProfileImage);
                            userProfileName.setText(userName);
                            userPhone.setText(crafPhone);
                            typeWorks.setText(typeWork);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }


    }//end retrieveUserInfo()




    private void Request_Friend_list() {


        FirebaseRecyclerAdapter<Datacrafstman,RatingViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Datacrafstman, RatingViewHolder>(Datacrafstman.class, R.layout.view_more_food, RatingViewHolder.class, mReference) {


                    @Override
                    protected void populateViewHolder(final RatingViewHolder viewHolder, Datacrafstman model, final int position) {


                        final String friend_key = getRef(position).getKey();

                        clientReference.child(friend_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                if (dataSnapshot.exists()){

                                    final String RequestProfileImage = dataSnapshot.child("imagepersonal").getValue().toString();
                                    final String RequestUserName = dataSnapshot.child("name").getValue().toString();


                                    viewHolder.textName.setText(RequestUserName);
                                    Picasso.get().load(RequestProfileImage).placeholder(R.drawable.profile_imagee).into(viewHolder.imageRequest);

                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });




                    }//end populateViewHolder

                    @Override
                    public RatingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_food, parent, false);
                        RatingViewHolder holder = new RatingViewHolder(view);

                        return holder;
                    }

                };//end FirebaseRecyclerAdapter

        request_list.setAdapter(firebaseRecyclerAdapter);



    }//end Request_Friend_list


    public static class RatingViewHolder extends RecyclerView.ViewHolder{

        ImageView imageRequest;
        TextView textName;


        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);

            textName= itemView.findViewById(R.id.tvNameFood);
            imageRequest = itemView.findViewById(R.id.img);


        }

    }//end RequestsViewHolder


    private String gettId(){




        for (int i = 0; i < arrayListCra.size(); i++) {



                String stChild = arrayListCra.get(i).toString();

                craftsReference.child(stChild).child(my_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if ((dataSnapshot.exists())) {


                            user_Id = dataSnapshot.child("uid").getValue().toString();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




        }

        return user_Id;
    }

}
