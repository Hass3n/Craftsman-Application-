package astbina.sanetna.Comment;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends AppCompatActivity {


    CircleImageView imgUserPost, imgCurrentUser;
    ImageView imgPost;
    TextView txtPostDesc, txtPostDateName, txtPostTitle;
    EditText editTextComment;
    Button btnAddComment;
    String PostKey;


    boolean found = false;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RecyclerView RvComment;

    String my_id;
    FirebaseAuth mAuth;

    ArrayList<String> arrayList;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null, myClient = null;

    CommentAdapter commentAdapter;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comment";
    // add child


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        mAuth = FirebaseAuth.getInstance();
        my_id = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Carfstman");

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


        // let's set the statue bar to transparent
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getSupportActionBar().hide();


        // ini Views
        RvComment = findViewById(R.id.rv_comment);
        imgPost = findViewById(R.id.post_detail_img);
        imgUserPost = findViewById(R.id.post_detail_user_img);
        imgCurrentUser = findViewById(R.id.post_detail_currentuser_img);

        txtPostTitle = findViewById(R.id.post_detail_title);
        txtPostDesc = findViewById(R.id.post_detail_desc);
        txtPostDateName = findViewById(R.id.post_detail_date_name);

        editTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        // add Comment button click listener


        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = editTextComment.getText().toString();
                if (message.isEmpty()) {
                    editTextComment.setError("Plz, enter your comment");
                } else {

                    found = false;
                    LoadChild();
                }


            }
        });


        String postImage = getIntent().getExtras().getString("image");
        Picasso.get().load(postImage).placeholder(R.drawable.banna).into(imgPost);


        String postTitle = getIntent().getExtras().getString("news_name");
        txtPostTitle.setText(postTitle);

        String postDescription = getIntent().getExtras().getString("description");
        txtPostDesc.setText(postDescription);

        // set comment user image
        Picasso.get().load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.banna).into(imgCurrentUser);


        // get post id
        PostKey = getIntent().getExtras().getString("uid");

        String date = getIntent().getExtras().getString("date");
        txtPostDateName.setText(date);


        // ini Recyclerview Comment
        iniRvComment();


    }//end onCreate


    //read comment
    private void iniRvComment() {

        RvComment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment);

                }

                commentAdapter = new CommentAdapter(getApplicationContext(), listComment);
                RvComment.setAdapter(commentAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    // load all child
    public void LoadChild() {
        for (int i = 0; i < arrayList.size(); i++) {
            if (found == true) {
                return;
            } else {
                readdataChild(arrayList.get(i).toString());
            }

        }


    }

    private void readdataChild(String child) {

        myRef.child(child).child(my_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String imagepersonal = dataSnapshot.child("imagepersonal").getValue().toString();

                    Picasso.get().load(imagepersonal).placeholder(R.drawable.banna).into(imgCurrentUser);

                    btnAddComment.setVisibility(View.INVISIBLE);
                    DatabaseReference commentReference = firebaseDatabase.getReference(COMMENT_KEY).child(PostKey).push();
                    String comment_content = editTextComment.getText().toString();
                    String uid = firebaseUser.getUid();
                    String uname = firebaseUser.getDisplayName();

                    Comment comment = new Comment(comment_content, uid, imagepersonal, name);

                    commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showMessage("comment added");
                            editTextComment.setText("");
                            btnAddComment.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("fail to add comment : " + e.getMessage());
                        }
                    });


                }

                found = true;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }//end showMessage


}
