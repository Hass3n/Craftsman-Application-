package astbina.sanetna.Basic.Aboutus;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import astbina.sanetna.R;

public class DeveloperInfo extends AppCompatActivity {


    private RecyclerView recyclerEmployee;
    private ArrayList<DeveloperData> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);


        getEmployees();
        recyclerEmployee = findViewById(R.id.recycler_employees);
        recyclerEmployee.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        DeveloperAdapter adapter=new DeveloperAdapter(this,employees);
        recyclerEmployee.setAdapter(adapter);

        adapter.setOnItemclickCratsman(new DeveloperAdapter.OnItemclickCratsman() {
            @Override
            public void onitemclick(DeveloperData data, int postion) {

                if (postion == 0){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/ane.hassan.5"));
                    startActivity(intent);

                }
                if (postion == 1){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100006337533725"));
                    startActivity(intent);

                }
                if (postion == 2){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100007118144994"));
                    startActivity(intent);

                }
                if (postion == 3){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/profile.php?id=100006171451203"));
                    startActivity(intent);

                }
                if (postion == 4){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/noureldin.raafat"));
                    startActivity(intent);

                }
                if (postion == 5){

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/new.human.359"));
                    startActivity(intent);

                }

            }
        });



    }

    private void getEmployees() {

        employees = new ArrayList<>();

        employees.add(new DeveloperData("Hassan Reda", "Android Developer", R.drawable.hassan));
        employees.add(new DeveloperData("Ahmed Soliman", "Android Developer", R.drawable.semo));
        employees.add(new DeveloperData("Ahmed Abdulrazek", "Android Developer", R.drawable.ahmed));
        employees.add(new DeveloperData("Osama Abdellateif", "Front-End Developer", R.drawable.osama));
        employees.add(new DeveloperData("Nour Raafat", "Software Engineer", R.drawable.nour));
        employees.add(new DeveloperData("Waleed Khadraui", "Co-Founder", R.drawable.waleed));

    }
}
