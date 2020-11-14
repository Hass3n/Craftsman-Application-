package astbina.sanetna.Basic.Aboutus;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import astbina.sanetna.R;


public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.Holder> {

    private Context context;
    private ArrayList<DeveloperData> employees;
    OnItemclickCratsman onItemclickCratsman;

    public void setOnItemclickCratsman(OnItemclickCratsman onItemclickCratsman) {
        this.onItemclickCratsman = onItemclickCratsman;
    }

    public DeveloperAdapter(Context context, ArrayList<DeveloperData> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        final DeveloperData currentEmployee=employees.get(i);  // i = position


        holder.tvName.setText(currentEmployee.getName());
        holder.tvJob.setText(currentEmployee.getJob());
        holder.ivImage.setImageResource(currentEmployee.getImage());

        if (onItemclickCratsman!=null)
        {
            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclickCratsman.onitemclick(currentEmployee, i);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView tvName,tvJob;
        ImageView ivImage;
        View parent;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvJob=itemView.findViewById(R.id.tv_job);
            ivImage=itemView.findViewById(R.id.image_one);
            parent=itemView;

        }
    }



    interface  OnItemclickCratsman
    {
        public void  onitemclick(DeveloperData data, int postion);



    }


}
