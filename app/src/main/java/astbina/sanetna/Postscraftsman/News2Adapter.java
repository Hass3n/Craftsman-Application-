package astbina.sanetna.Postscraftsman;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import astbina.sanetna.Dataclass.Data_item;
import astbina.sanetna.R;

public class News2Adapter extends RecyclerView.Adapter<News2Adapter.ViewHolder>  {

    OnItemclick onItemclick;

    public void setOnItemclick(OnItemclick onItemclick) {
        this.onItemclick = onItemclick;
    }

    ArrayList<Data_item> data;

    public News2Adapter(ArrayList<Data_item> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Data_item item= data.get(i);
        viewHolder.txt1.setText(item.getNews_name());
         viewHolder.imag.setImageResource(item.getImage());
        if (viewHolder !=null)
        {
            viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclick.onitemclick(item,i);
                }
            });

        }

    }

   /* @Override
    public int getItemCount() {
        if (data.size()=!null)
        {
        return data.size();
    }*/
   public int getItemCount() {
       return (data == null) ? 0 : data.size();
   }

    class ViewHolder extends RecyclerView.ViewHolder{
    private     TextView txt1;
    private     ImageView imag;
  private       View parent;

        ViewHolder(View view){
            super(view);
            txt1= view.findViewById(R.id.txt1);
            imag = view.findViewById(R.id.image);
            parent=view;

        }
    }

    public interface  OnItemclick
     {
         public void  onitemclick(Data_item data, int postion);



     }


}
