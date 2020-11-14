package astbina.sanetna.Chat;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import astbina.sanetna.Dataclass.PersonalDate;
import astbina.sanetna.R;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.Holder> {

    private Context context;
    private ArrayList<PersonalDate> showData;



    public ClientAdapter(Context context, ArrayList<PersonalDate> showData) {
        this.context = context;
        this.showData = showData;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_chat, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder,final int i) {

        final PersonalDate currentShowData = showData.get(i);  // i = position

        holder.tvName.setText(currentShowData.getName());
        final String id=currentShowData.getUid().toString();
        String image=currentShowData.getImagepersonal().toString();
        try {

            Picasso.get().load(image).into(holder.ivImage);

        }
        catch ( Exception e)
        {
            Picasso.get().load(R.drawable.banna).into(holder.ivImage);


        }


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("hasUidclient",id);
                context.startActivity(intent);




            }
        });



    }

    @Override
    public int getItemCount() {
        return showData.size();
    }


    //class holder

    class Holder extends RecyclerView.ViewHolder{

        TextView tvName,tvJob;
        ImageView ivImage;
        View parent;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            ivImage=itemView.findViewById(R.id.image_one);
            parent=itemView;

        }
    }





}
