package astbina.sanetna.Postscraftsman;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import astbina.sanetna.Dataclass.Data_item;
import astbina.sanetna.PostsWork.MainActivity;
import astbina.sanetna.R;
import astbina.sanetna.craftsman_register_login.Login;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    News2Adapter adapter;
    ArrayList<Data_item> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        addData();
        adapter = new News2Adapter(data);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemclick(new News2Adapter.OnItemclick() {
            @Override
            public void onitemclick(Data_item data, int postion) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.putExtra("position", postion);
                startActivity(intent);
            }
        });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item8) {
            startActivity(new Intent(Main2Activity.this, Login.class));
            return true;
        }
        if (item.getItemId() == R.id.item9) {
            // show dialog exit
            show_Dialog();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void show_Dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("هل تريد حقا الخروج من التطبيق ؟")
                .setCancelable(false)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main2Activity.this.finish();
                    }
                })
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

