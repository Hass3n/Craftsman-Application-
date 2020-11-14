package astbina.sanetna.Craftsman_details;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import astbina.sanetna.Company_details.AdapterCompany;
import astbina.sanetna.Company_post.Companypost;
import astbina.sanetna.Dataclass.Data_item;
import astbina.sanetna.R;

public class CraftsmanFragment extends Fragment {

    RecyclerView company,crafstmen;
    AdapterCompany mAdapter;
    AdapterMoreCraftsman mAdapter2;
   private static FirebaseDatabase database;
   private static DatabaseReference myRef=null, myRef2=null;
    ArrayList<Companypost> data_c;

    ArrayList<Data_item> data;


    public CraftsmanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food, container, false);


        company   = v.findViewById(R.id.rvPopularFood);
       crafstmen    = v.findViewById(R.id.rvMoreFood);

        company.setHasFixedSize(true);
       crafstmen.setHasFixedSize(true);


        // read in firebase
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Comapny");
       myRef.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               data_c=new ArrayList<>();
               for (DataSnapshot dmo : dataSnapshot.getChildren())
               {
                 Companypost data_p=dmo.getValue(Companypost.class);
                   String c_image = data_p.getImage();

                   Log.e("image_company",c_image);
                   data_c.add(new Companypost(c_image));

               }
               RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
               company.setLayoutManager(layoutManager);


               mAdapter = new AdapterCompany(data_c,getActivity());
               company.setAdapter(mAdapter);

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               Toast.makeText(getContext(),databaseError.getMessage().toString(),Toast.LENGTH_LONG).show();

           }
       });


       // add recycle craftsman
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());

        addData();
        mAdapter2=new AdapterMoreCraftsman(data,getActivity());
        crafstmen.setLayoutManager(layoutManager2);
        crafstmen.setAdapter(mAdapter2);

         mAdapter2.setOnItemclickCratsman(new AdapterMoreCraftsman.OnItemclickCratsman() {
             @Override
             public void onitemclick(Data_item data, int postion) {
                 Intent intent = new Intent(getActivity(), CraftsmanData.class);
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

    }

}
