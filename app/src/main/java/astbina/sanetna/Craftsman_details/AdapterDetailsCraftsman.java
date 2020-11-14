package astbina.sanetna.Craftsman_details;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import astbina.sanetna.CraftsmanSetting.Craftsmansetting;
import astbina.sanetna.CraftsmanSetting.Rate;
import astbina.sanetna.Dataclass.Datacrafstman;
import astbina.sanetna.R;

public class AdapterDetailsCraftsman extends RecyclerView.Adapter<AdapterDetailsCraftsman.ViewHolder> {
    List<Datacrafstman> data_items;
    Context context;
    private static FirebaseDatabase database, mDatabase;

    OnItemclick onItemclick;

    public void setOnItemclick(OnItemclick onItemclick) {
        this.onItemclick = onItemclick;
    }

    public AdapterDetailsCraftsman(List<Datacrafstman> data_items, Context context) {
        this.data_items = data_items;


        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_more_food, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Datacrafstman item = data_items.get(i);

        viewHolder.txt1.setText(item.getName());

        String imagephoto = item.getImagepersonal();
        Log.e("photo", imagephoto + "");
        if (imagephoto.isEmpty()) {

            viewHolder.imag.setImageResource(R.drawable.banna);
        } else {
            Picasso.get().load(imagephoto).into(viewHolder.imag);
        }


        if (onItemclick != null) {
            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemclick.onitemclick(item, i);


                }
            });


        }


    }

    @Override
    public int getItemCount() {
        return data_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        ImageView imag;
        View parent;

        public ViewHolder(View view) {
            super(view);


            txt1 = view.findViewById(R.id.tvNameFood);
            txt2 = view.findViewById(R.id.tvrate);

            imag = view.findViewById(R.id.img);
            parent = view;


        }
    }

    public void filterlist(ArrayList<Datacrafstman> name) {
        this.data_items = name;
        notifyDataSetChanged();
    }

    public interface OnItemclick {
        public void onitemclick(Datacrafstman datacrafstman, int postion);


    }

}
