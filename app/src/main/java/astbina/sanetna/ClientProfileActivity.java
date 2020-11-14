package astbina.sanetna;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientProfileActivity extends AppCompatActivity {

    private CircleImageView userProfileImage;
    private TextView userProfileName, userProfilePlace, userProfilePhone;
    private Button sendMessageRequestButton, declineMessageRequestButton, callButton;

    private String friend_id, friend_phone;
    private String  my_id ;

    int currentState = 0;

    private DatabaseReference clientReference, mReference;
    private FirebaseDatabase  mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        mAuth = FirebaseAuth.getInstance();
        clientReference = FirebaseDatabase.getInstance().getReference().child("Client");

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference();


        friend_id = getIntent().getExtras().get("visitUserId").toString();//client id

        //friend_phone = getIntent().getExtras().get("visitUserPhone").toString();

        my_id = mAuth.getCurrentUser().getUid(); //current user id


        userProfileImage = findViewById(R.id.visit_profile_image);
        userProfileName = findViewById(R.id.visit_user_name);
        userProfilePlace = findViewById(R.id.visit_profile_place);
        userProfilePhone = findViewById(R.id.visit_profile_phone);
        sendMessageRequestButton = findViewById(R.id.send_request_button);
        declineMessageRequestButton = findViewById(R.id.decline_request_button);
        callButton = findViewById(R.id.call_request_button);

        retrieveUserInfo();

        sendMessageRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_friend_request();

            }
        });


        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + friend_phone));
                startActivity(intent);

            }
        });


    }//end onCreate



    private void add_friend_request() {

        sendMessageRequestButton.setEnabled(false);
        if (currentState == 0){

            mReference.child("Friend_List").child(friend_id).child(my_id).child("Request_type")
                    .setValue("Friend").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    mReference.child("Friend_List").child(my_id).child(friend_id).child("Request_type")
                            .setValue("Friend").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            mReference.child("Request").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    mReference.child("Request").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            sendMessageRequestButton.setEnabled(true);
                                            sendMessageRequestButton.setText("إلغاء الطلب");
                                            declineMessageRequestButton.setVisibility(View.INVISIBLE);

                                        }
                                    });

                                }
                            });



                        }
                    });

                }
            });

        }//end first if


    }//end add_friend_request


    private void retrieveUserInfo() {

        clientReference.child(friend_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {

                    String userImage = dataSnapshot.child("imagepersonal").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userPlace = dataSnapshot.child("places").getValue().toString();
                    final String userPhone = dataSnapshot.child("phoneNumbe").getValue().toString();

                    Picasso.get().load(userImage).placeholder(R.drawable.profile_imagee).into(userProfileImage);
                    userProfileName.setText(userName);
                    userProfilePlace.setText(userPlace);
                    userProfilePhone.setText(userPhone);

                    mReference.child("Request").child(friend_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(my_id)){

                                String request = dataSnapshot.child(my_id).child("Request_type").getValue().toString();

                                if (request.equals("Sent")){

                                    sendMessageRequestButton.setEnabled(true);
                                    sendMessageRequestButton.setText("قبول الطلب");
                                    currentState = 0;
                                    declineMessageRequestButton.setVisibility(View.VISIBLE);
                                    declineMessageRequestButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            decline_request();

                                        }
                                    });

                                }

                            }


                            else {

                                mReference.child("Friend_List").child(my_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.hasChild(friend_id)){

                                            //String request = dataSnapshot.child(friend_id).child("Request_type").getValue().toString();


                                            sendMessageRequestButton.setEnabled(true);
                                            sendMessageRequestButton.setText("تم التنفيذ");

                                            sendMessageRequestButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    UnFriend(); //////////////////////////////////////////////////////////////////////////

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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }//end retrieveUserInfo

    private void UnFriend() {

//        mReference.child("Friend_List").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                mReference.child("Friend_List").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        finish();
//
//                    }
//                });
//
//            }
//        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        mReference.child("Rate_List").child(friend_id).child(my_id).child("Rate_type")
                .setValue("Rated_sent").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mReference.child("Rate_List").child(my_id).child(friend_id).child("Rate_type")
                        .setValue("Rated_received").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        mReference.child("Friend_List").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                mReference.child("Friend_List").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        //sendMessageRequestButton.setEnabled(true);
                                        sendMessageRequestButton.setVisibility(View.INVISIBLE);//
                                        //declineMessageRequestButton.setVisibility(View.INVISIBLE);

                                    }
                                });

                            }
                        });



                    }
                });

            }
        });







        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    }//end UnFriend


    private void decline_request() {

        mReference.child("Request").child(friend_id).child(my_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mReference.child("Request").child(my_id).child(friend_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        finish();

                    }
                });

            }
        });

    }//end decline_request

}
