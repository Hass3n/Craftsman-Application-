package astbina.sanetna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import astbina.sanetna.CraftsmanSetting.Rate;
import astbina.sanetna.Dataclass.Datacrafstman;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientSettingsActivity extends AppCompatActivity {

    public String client_id="" ;

    private ProgressDialog loadingBar;

    private CircleImageView clientProfileImage;
    private TextView clientProfileName, clientProfilePlace, clientProfilePhone;
    private RecyclerView request_list;

    private DatabaseReference rootReference, cliReference, mReference, craftsReference;
    private StorageReference userProfileImageReference;
    FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String my_id;
    boolean r=false;

    private static final int galleryPick = 1;

    ArrayList<String> arrayList ;
    boolean found=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_settings);


        mAuth = FirebaseAuth.getInstance();
        my_id = mAuth.getCurrentUser().getUid();
        rootReference = FirebaseDatabase.getInstance().getReference();
        cliReference = FirebaseDatabase.getInstance().getReference("Client").child(my_id);
        userProfileImageReference = FirebaseStorage.getInstance().getReference().child("All_Image_Uploads/");
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Rate_List").child(my_id);
        craftsReference = FirebaseDatabase.getInstance().getReference("Carfstman");


        clientProfileImage = findViewById(R.id.visit_client_profile_image);
        clientProfileName = findViewById(R.id.visit_client_user_name);
        clientProfilePlace = findViewById(R.id.visit_client_profile_place);
        loadingBar = new ProgressDialog(this);


        request_list = findViewById(R.id.rateing_list);
        request_list.setHasFixedSize(true);
        request_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


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


        retrieveUserInfo();


        Request_Friend_list();


        clientProfileImage.setOnClickListener(new View.OnClickListener() {
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
                Log.e("profileimage",resultUri+"" );
                StorageReference filePath = userProfileImageReference.child(my_id + ".jpg");
                Log.e("profileimage",resultUri+"" );
                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        HashMap<String, Object> results = new HashMap<>();
                        results.put("imagepersonal", taskSnapshot.getDownloadUrl().toString());
                        cliReference.updateChildren(results);
                        loadingBar.dismiss();

                        Toast.makeText(ClientSettingsActivity.this, "Profile Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                    }
                });

            }
        }


    }//end onActivityResult


    private void retrieveUserInfo() {

        rootReference.child("Client").child(my_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("imagepersonal")))) {

                            String userImage = dataSnapshot.child("imagepersonal").getValue().toString();
                            String userName = dataSnapshot.child("name").getValue().toString();
                            String userPlace = dataSnapshot.child("places").getValue().toString();
                            String uid=dataSnapshot.child("uid").getValue().toString();

                            client_id=uid;
                            Picasso.get().load(userImage).placeholder(R.drawable.profile_imagee).into(clientProfileImage);
                            clientProfileName.setText(userName);
                            clientProfilePlace.setText(userPlace);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }//end retrieveUserInfo()



    private void Request_Friend_list() {


        FirebaseRecyclerAdapter<Datacrafstman, RatingsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Datacrafstman, RatingsViewHolder>(Datacrafstman.class, R.layout.view_more_food, RatingsViewHolder.class, mReference) {


                    @Override
                    protected void populateViewHolder(final RatingsViewHolder viewHolder, Datacrafstman model, final int position) {


                        final String friend_key = getRef(position).getKey();

                        for (int i = 0; i < arrayList.size(); i++)
                        {
                            if (found==true)
                            {
                                return;
                            }
                            else {

                                String stChild = arrayList.get(i).toString();

                                craftsReference.child(stChild).child(friend_key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {


                                        if (dataSnapshot.exists()){


                                            final String RequestProfileImage = dataSnapshot.child("imagepersonal").getValue().toString();
                                            final String RequestUserName = dataSnapshot.child("name").getValue().toString();

                                            viewHolder.textRateName.setText(RequestUserName);

                                            Picasso.get().load(RequestProfileImage).placeholder(R.drawable.profile_imagee).into(viewHolder.imageRateRequest);

                                            //on item view Clicked

                                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    //dialog Building
                                                    final Dialog dialog = new Dialog(v.getContext());
                                                    dialog.setCancelable(true);
                                                    dialog.setContentView(R.layout.dialog_rate);
                                                    final RatingBar ratingBar = dialog.findViewById(R.id.simpleRatingBar);
                                                    final Button submitRate = dialog.findViewById(R.id.submit_rate);
                                                    //reading rate from client and sending it to firebase
                                                    submitRate.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            float rating;
                                                            rating=ratingBar.getRating();
                                                            rootReference=mDatabase.getReference("Rates");//object in the root called Rates
                                                            //  currentUserId=mAuth.getCurrentUser().getUid();//user that will give a rates
                                                            Rate myRate=new Rate(my_id,friend_key,rating);
                                                            String s=rootReference.push().getKey();
                                                            rootReference.child(s).setValue(myRate);//setting the rate for the  craftsman with his id


                                                            Toast.makeText(v.getContext(), "تقييم = "+rating, Toast.LENGTH_SHORT).show();
                                                            dialog.dismiss();

                                                            Toast.makeText(ClientSettingsActivity.this, "تم التقييم", Toast.LENGTH_SHORT).show();
                                                            r=true;

                                                        }//onClick submitRate end
                                                    });//listener submitrate end
                                                    if(r==true){
                                                        submitRate.setEnabled(false);
                                                        submitRate.setBackgroundColor(getResources().getColor(R.color.md_red_100));
                                                        Toast.makeText(ClientSettingsActivity.this, "تــم التقــييم مـــن قبـــل ", Toast.LENGTH_SHORT).show();
                                                    }

                                                    int screenWidth = v.getContext().getResources().getDisplayMetrics().widthPixels;
                                                    int screenHeight = v.getContext().getResources().getDisplayMetrics().heightPixels;

                                                    Window dialogWindow = dialog.getWindow();
                                                    if(dialogWindow != null){
                                                        dialogWindow.setLayout(((int)(0.95 * screenWidth)), ((int)(0.5 * screenHeight)));
                                                    }

                                                    dialog.show();

                                                }
                                            });



                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }//end else

                        }

                    }//end populateViewHolder

                    @Override
                    public RatingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_more_food, parent, false);
                        RatingsViewHolder holder = new RatingsViewHolder(view);

                        return holder;
                    }

                };//end FirebaseRecyclerAdapter

        request_list.setAdapter(firebaseRecyclerAdapter);



    }//end Request_Friend_list



    public static class RatingsViewHolder extends RecyclerView.ViewHolder{

        ImageView imageRateRequest;
        TextView textRateName;


        public RatingsViewHolder(@NonNull View itemView) {
            super(itemView);

            textRateName= itemView.findViewById(R.id.tvNameFood);
            imageRateRequest = itemView.findViewById(R.id.img);


        }

    }//end RequestsViewHolder

//   public String getClientId(String uid) {
//
//
//        return uid;
//
//    }//end getClientId


}
