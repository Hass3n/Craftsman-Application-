package astbina.sanetna.Craftsman_details;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import astbina.sanetna.Dataclass.Data_item;
import astbina.sanetna.PostsWork.MainActivity;
import astbina.sanetna.Postscraftsman.News2Adapter;
import astbina.sanetna.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CraftsmanTypeFragment extends Fragment {

    RecyclerView recyclerView;
    News2Adapter adapter;
    ArrayList<Data_item> data;
    Context context;
    public CraftsmanTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity2_main, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        addData();

        adapter = new News2Adapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemclick(new News2Adapter.OnItemclick() {
            @Override
            public void onitemclick(Data_item data, int postion) {
              Intent intent = new Intent( CraftsmanTypeFragment.super.getContext(), MainActivity.class);
            //  context.startActivity(new Intent(DrinkFragment.super.getContext(),MainActivity.class));
                intent.putExtra("position", postion);
               startActivity(intent);
            }
        });
        return v;
    }

    public void addData() {
        data = new ArrayList<>();

        data.add(new Data_item("سبــــاك", R.drawable.sabak));
        data.add(new Data_item("نجار", R.drawable.nagar));
        data.add(new Data_item("فني تكييف", R.drawable.takyeef));
        data.add(new Data_item("عــامل بنــاء", R.drawable.banna));
        data.add(new Data_item("كهربــائي", R.drawable.kahraba));
        data.add(new Data_item("تنظيــف", R.drawable.nazafa));
        data.add(new Data_item("نقــاش", R.drawable.naqash));
        data.add(new Data_item("عشــب صنــاعي", R.drawable.oshb));
        data.add(new Data_item("كـاميـرات مـراقـبة", R.drawable.morakaba));
        data.add(new Data_item("حـداد", R.drawable.hadad));
        data.add(new Data_item("مـصـور", R.drawable.mosswer));
        data.add(new Data_item("طوارئ", R.drawable.toaree));
    }



}
