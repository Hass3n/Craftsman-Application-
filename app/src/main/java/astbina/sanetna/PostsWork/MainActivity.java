package astbina.sanetna.PostsWork;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import astbina.sanetna.Comment.PostDetailActivity;
import astbina.sanetna.Dataclass.Data;
import astbina.sanetna.Dataclass.NewsItem;
import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView NewsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    LinearLayoutManager layoutManager;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";

    private DatabaseReference databaseReference;
    static Activity context;
    private ProgressDialog progressDialog;
    private TextView emptyView;

    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // let's make this activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("يرجى الانتظار...");
        // ini view

        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();
        emptyView = findViewById(R.id.empty_view);

        //getIntent
        Intent intent = getIntent();
        switch (intent.getIntExtra("position", 0)) {
            case 0:
                databaseReference = FirebaseDatabase.getInstance().getReference("sbak");
                break;
            case 1:
                databaseReference = FirebaseDatabase.getInstance().getReference("nager");
                break;
            case 2:
                databaseReference = FirebaseDatabase.getInstance().getReference("fanytakif");
                break;
            case 3:
                databaseReference = FirebaseDatabase.getInstance().getReference("amelbana");
                break;
            case 4:
                databaseReference = FirebaseDatabase.getInstance().getReference("karbah");
                break;
            case 5:
                databaseReference = FirebaseDatabase.getInstance().getReference("tanzif");
                break;
            case 6:
                databaseReference = FirebaseDatabase.getInstance().getReference("nagash");
                break;
            case 7:
                databaseReference = FirebaseDatabase.getInstance().getReference("asbsnaya");
                break;
            case 8:
                databaseReference = FirebaseDatabase.getInstance().getReference("cemarman");
                break;
            case 9:
                databaseReference = FirebaseDatabase.getInstance().getReference("hadad");
                break;
            case 10:
                databaseReference = FirebaseDatabase.getInstance().getReference("photograph");
                break;
            default:
                databaseReference = FirebaseDatabase.getInstance().getReference("ScholarshipsAndScientificAssignments");
        }

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dm : dataSnapshot.getChildren()) {

                    NewsItem data = dm.getValue(NewsItem.class);
                    Log.e("man", data + "");
                    mData.add(data);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        NewsRecyclerview.setHasFixedSize(true);
        newsAdapter = new NewsAdapter(getApplicationContext(), mData);
        NewsRecyclerview.setLayoutManager(layoutManager);

        NewsRecyclerview.setAdapter(newsAdapter);

        newsAdapter.setNews(new NewsAdapter.newsItem() {
            @Override
            public void onItemClick(NewsItem newsItem, int position) {
                String postId = newsItem.getUid();

                Intent postDetailActivity = new Intent(getApplicationContext(), PostDetailActivity.class);
                //int position = getAdapterPosition();

                postDetailActivity.putExtra("news_name",mData.get(position).getNews_name());
                postDetailActivity.putExtra("image",mData.get(position).getImage());
                postDetailActivity.putExtra("description",mData.get(position).getDescription());
                postDetailActivity.putExtra("uid",mData.get(position).getUid());
                postDetailActivity.putExtra("date",mData.get(position).getPostDate());

                startActivity(postDetailActivity);


            }
        });






   /* public void notificationM() {
        Intent notificationIntent = new Intent(MainActivity.this, Navhome.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        builder.setContentTitle("New Post");
        builder.setSmallIcon(R.drawable.uv);
        builder.setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        //Vibration
        builder.setVibrate(new long[]{500, 500, 500, 500, 500});
        //LED
        builder.setLights(Color.RED, 3000, 3000);
        builder.setOnlyAlertOnce(true);

        Notification noti = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }*/


    }
}
