package astbina.sanetna.CraftsmanSetting;

import android.app.Dialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import astbina.sanetna.Craftsman_details.CraftsmanData;
import astbina.sanetna.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Craftsmansetting extends AppCompatActivity {

    private CircleImageView userProfileImage;
    private TextView userProfileName, userRating, userPhone, typeWork;
    private Button add_request, cancel_request, btnRate;
    private ArrayList<Rate> rates=null;
    private double ratingSum=0,rateAvg=0;
    private int rateCount=0;




    private DatabaseReference mReference;
    private FirebaseAuth mAuth;

    private static FirebaseDatabase database, mDatabase;
    private static DatabaseReference myRef=null;

    private String friend_id, my_id ;

    int currentState = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craftsmansetting);

        /////////////////////////////////////////////////   THE NEW ONE     /////////////////////////////////////

        mAuth = FirebaseAuth.getInstance();

        //Craftsman Id
        friend_id = getIntent().getExtras().getString("craftsmanId");

        //client id
        my_id = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();


        userProfileImage = findViewById(R.id.profile_image);
        userProfileName = findViewById(R.id.profile_name);
        userRating = findViewById(R.id.rating);
        userPhone = findViewById(R.id.phone);
        typeWork = findViewById(R.id.type_work);

        add_request = findViewById(R.id.sendRequest);
        cancel_request = findViewById(R.id.cancelRequest);
        btnRate=findViewById(R.id.rate);

        rates=new ArrayList<>();

       if(my_id.equals( friend_id))
       {
//           btnRate.setEnabled(false);
//           cancel_request.setEnabled(false);
//           add_request.setEnabled(false);

           btnRate.setVisibility(View.INVISIBLE);
           cancel_request.setVisibility(View.INVISIBLE);
           add_request.setVisibility(View.INVISIBLE);


       }



        /*
        *  0 = not friend
        *  1 = friend
        * */

        retrieveUserInfo();



        add_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_friend_request();

            }
        });

        /////////////////////////////////


        //rating craftsman
        btnRate.setOnClickListener(new View.OnClickListener() {
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
                        //here we send it to firebase database
                        myRef=database.getReference("Rates");//object in the root called Rates
                        //  currentUserId=mAuth.getCurrentUser().getUid();//user that will give a rate

                        Rate myRate=new Rate(my_id,friend_id,rating);
                        myRef.child(friend_id).setValue(myRate);//setting the rate for the  craftsman with his id


                        Toast.makeText(v.getContext(), "rate = "+rating, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }//onClick submitRate end
                });//listener submitrate end



                int screenWidth = v.getContext().getResources().getDisplayMetrics().widthPixels;
                int screenHeight = v.getContext().getResources().getDisplayMetrics().heightPixels;

                Window dialogWindow = dialog.getWindow();
                if(dialogWindow != null){
                    dialogWindow.setLayout(((int)(0.95 * screenWidth)), ((int)(0.5 * screenHeight)));
                }

                dialog.show();



            }//onClick btnRate end

        });//listener btnRate end

        readRate();//reading the rate and toast to it



        ///////////////////////////////////////////




    }//end onCreate




    private void add_friend_request() {

        add_request.setEnabled(false);

        if (currentState == 0){

            mReference.child("Request").child(friend_id).child(my_id).child("Request_type")
                    .setValue("Received").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    mReference.child("Request").child(my_id).child(friend_id).child("Request_type")
                            .setValue("Sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            add_request.setEnabled(true);
                            add_request.setText("إلغاء الطلب");
                            currentState = 1;

                        }
                    });

                }
            });

        }//end first if

        add_request.setEnabled(false);

        if (currentState == 1){

            mReference.child("Request").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    mReference.child("Request").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            add_request.setEnabled(true);
                            add_request.setText("إرسال طلب");
                            //cancel_request.setVisibility(View.VISIBLE);

                            currentState = 0;

                        }
                    });

                }
            });

        }



    }//end add_friend_request


    private void retrieveUserInfo() {

        database=FirebaseDatabase.getInstance();


        final int post = getIntent().getIntExtra("pos", 0);


        switch (post) {
            case 0:
                myRef=database.getReference("Carfstman").child("sbak");
                break;
            case 1:
                myRef=database.getReference("Carfstman").child("nager");
                break;
            case 2:

                myRef=database.getReference("Carfstman").child("fanytakif");
                break;

            case 3:

                myRef=database.getReference("Carfstman").child("amelbana");
                break;

            case 4:

                myRef=database.getReference("Carfstman").child("karbah");
                break;

            case 5:

                myRef=database.getReference("Carfstman").child("tanzif");
                break;

            case 6:

                myRef=database.getReference("Carfstman").child("nagash");
                break;

            case 7:

                myRef=database.getReference("Carfstman").child("asbsnaya");
                break;
            case 8:

                myRef=database.getReference("Carfstman").child("cemarman");
                break;
            case 9:

                myRef=database.getReference("Carfstman").child("hadad");
                break;
            case 10:

                myRef=database.getReference("Carfstman").child("photograph");
                break;


            default:
                //  myRef.child("");
                myRef=database.getReference("ScholarshipsAndScientificAssignments ");
        }




        myRef.child(friend_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String crafImage = dataSnapshot.child("imagepersonal").getValue().toString();
                    String crafName = dataSnapshot.child("name").getValue().toString();
                    String crafPhone = dataSnapshot.child("phoneNumbe").getValue().toString();
                    String crafType = dataSnapshot.child("type_w").getValue().toString();

                    Picasso.get().load(crafImage).placeholder(R.drawable.banna).into(userProfileImage);
                    userProfileName.setText(crafName);
                    userPhone.setText(crafPhone);
                    typeWork.setText(crafType);



                    mReference.child("Request").child(friend_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(my_id)){

                                String request = dataSnapshot.child(my_id).child("Request_type").getValue().toString();

                                if (request.equals("Received")){

                                    add_request.setEnabled(true);
                                    add_request.setText("إلغاء الطلب");
                                    //cancel_request.setVisibility(View.VISIBLE);
                                    currentState = 1;


                                }


                            }


                            else {

                                mReference.child("Friend_List").child(my_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.hasChild(friend_id)){

                                            String request = dataSnapshot.child(friend_id).child("Request_type").getValue().toString();

                                            add_request.setEnabled(true);
                                            add_request.setText("إلغاء الطلب");

                                            add_request.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    UnFriend();

                                                }
                                            });


                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }//end retrieveUserInfo

    private void UnFriend() {

        mReference.child("Friend_List").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mReference.child("Friend_List").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        finish();

                    }
                });

            }
        });

    }//end UnFriend


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Craftsmansetting.this, CraftsmanData.class);
        startActivity(intent);

    }




    public void readRate(){
        rates=new ArrayList<>();
        myRef=database.getReference("Rates");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot n: dataSnapshot.getChildren())
                {
                    Rate rateObject=n.getValue(Rate.class);
                    rates.add(rateObject);
                }//for end
                if( rates.size()>= 0){
                    for(int j=0;j<rates.size();j++)
                    {
                        if(rates.get(j).getCraftsmanId().toString().equals(friend_id)){
                            ratingSum += rates.get(j).rating;
                            rateCount++;}
                        Log.e("rates",rates.get(j).getRating()+"");
                        Log.e("rateSum",ratingSum+"");
                    }

                    // rateCount=rates.size();
                    rateAvg = ratingSum / rateCount;
                    if(rateAvg>0) {
                        userRating.setText(String.valueOf(rateAvg));
                    }else if(rateAvg==0)
                        userRating.setText("0");

                    //  Toast.makeText(Craftsmansetting.this, "Rate Average = "+rateAvg, Toast.LENGTH_LONG).show();
                    ratingSum =0 ;
                    rateCount=0;

                }//if end
                else
                    Toast.makeText(Craftsmansetting.this, "has no rates yet", Toast.LENGTH_SHORT).show();

            }//onDataChange end

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }//onCanceled end
        });//addValueEventListener end

    }//readRate method end


}
