package astbina.sanetna.Craftsman_details;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import astbina.sanetna.Dataclass.Data_item;
import astbina.sanetna.R;

public class AdapterMoreCraftsman extends RecyclerView.Adapter<AdapterMoreCraftsman.ViewHolder> {
    List<Data_item> data_items;
    Context context;
    OnItemclickCratsman onItemclickCratsman;

    public void setOnItemclickCratsman(OnItemclickCratsman onItemclickCratsman) {
        this.onItemclickCratsman = onItemclickCratsman;
    }

    public AdapterMoreCraftsman(List<Data_item> data_items, Context context) {
        this.data_items= data_items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Data_item item= data_items.get(i);
        viewHolder.txt1.setText(item.getNews_name());
        viewHolder.imag.setImageResource(item.getImage());

        if (onItemclickCratsman!=null)
        {
            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclickCratsman.onitemclick(item,i);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return data_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        ImageView imag;
        View parent;

        ViewHolder(View view){
            super(view);
            txt1= view.findViewById(R.id.txt1);
            imag = view.findViewById(R.id.image);
            parent=view;

        }
    }
    interface  OnItemclickCratsman
    {
        public void  onitemclick(Data_item data, int postion);



    }
}
