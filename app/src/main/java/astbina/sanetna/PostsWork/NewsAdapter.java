package astbina.sanetna.PostsWork;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import astbina.sanetna.Dataclass.NewsItem;
import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private Context mContext;
    private List<NewsItem> mDataFiltered;
    private boolean isDark = false;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;

    newsItem news ;

    public void setNews(newsItem news) {
        this.news = news;
    }

    public NewsAdapter(Context mContext, List<NewsItem> mDataFiltered) {
        this.mContext = mContext;
        // this.mData = mData;
        this.mDataFiltered = mDataFiltered;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //return inflater.inflate(R.layout.<your_view_layout>, container, false);
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup, false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        NewsItem item =mDataFiltered.get(position);

        newsViewHolder.tv_title.setText(mDataFiltered.get(position).getNews_name());
        newsViewHolder.tv_content.setText(mDataFiltered.get(position).getDescription());
        newsViewHolder.tv_date.setText(mDataFiltered.get(position).getPostDate());
        String image_use = mDataFiltered.get(position).getImage();
        if (image_use.isEmpty()) {

            newsViewHolder.img_user.setImageResource(R.drawable.banna);

        } else {
            Picasso.get().load(image_use).into
                    (newsViewHolder.img_user);
        }


        // get  informatio from client table person send post

        String uid = mDataFiltered.get(position).getPosteruid();

        // read id from client table abd check with uid to get name and profi
        // le image
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Client");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dmo : dataSnapshot.getChildren()) {

                    PersonalDate data = dmo.getValue(PersonalDate.class);
                    String c_uid = data.getUid().toString();
                    String name = data.getName().toString();
                    String profileimage = data.getImagepersonal().toString();

                    if (c_uid.equals(uid)) {
                        newsViewHolder.username.setText(name);

                        if (profileimage.isEmpty()) {

                            newsViewHolder.imageprofile.setImageResource(R.drawable.banna);

                        } else {
                            Picasso.get().load(profileimage).into
                                    (newsViewHolder.imageprofile);
                        }

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



        newsViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                news.onItemClick(item,position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {


        TextView tv_title, tv_content, tv_date, username;
        ImageView img_user, imageprofile;
        RelativeLayout container;
        View parent;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_description);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);
            username = itemView.findViewById(R.id.user_name);
            imageprofile = itemView.findViewById(R.id.post_user_profile);

            parent=itemView;

        }


    }

    interface newsItem {

        void onItemClick(NewsItem newsItem, int position);

    }

}
