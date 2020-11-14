package astbina.sanetna.Company_details;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import astbina.sanetna.Company_post.Companypost;
import astbina.sanetna.R;

public class AdapterCompany extends RecyclerView.Adapter<AdapterCompany.ViewHolder> {
   ArrayList<Companypost> data;
    Context context;

    public AdapterCompany(ArrayList<Companypost> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_popular_food,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Companypost item=data.get(i);

        String imagephoto= item.getImage();
        Log.e("photo",imagephoto+"");
        if (imagephoto.isEmpty())
        {
            // Picasso.get().load(R.drawable.ic_launcher_foreground).into( viewHolder.book_image);
            viewHolder.image.setImageResource(R.drawable.banna);
        }
        else
        {
            Picasso.get().load(imagephoto).into( viewHolder.image);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.img);

        }
    }
}
