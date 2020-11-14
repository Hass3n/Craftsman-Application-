package astbina.sanetna.Chat;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import astbina.sanetna.R;

public class Spinner extends AppCompatActivity {


    String[] craftsman = {"اختر المهنة", "سباك", "نجار", "فني تكييف","عامل بناء","كهربائي","تنظيف","نقاش","عشب صناعي","كاميرات مراقبه"

    ,"حداد","مصور"};

    private android.widget.Spinner craftsmanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);


        craftsmanList = findViewById(R.id.list_craftsman);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, craftsman);
        craftsmanList.setAdapter(adapter);

        craftsmanList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 1) {


                  SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                  editor.putString("Type_C","sbak");
                  editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                } else if (position==2) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","nager");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==3) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","fanytakif");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==4) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","amelbana");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==5) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","karbah");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==6) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","tanzif");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==7) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","nagash");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==8) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","asbsnaya");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }
                else if (position==9) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","cemarman");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }


                else if (position==10) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","hadad");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }

                else if (position==11) {


                    SharedPreferences.Editor editor=getSharedPreferences("Type_Craftsman",MODE_PRIVATE).edit();
                    editor.putString("Type_C","photograph");
                    editor.apply();
                    Intent i = new Intent(Spinner.this, CraftsmanChatHome.class);
                    startActivity(i);


                }






            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


}
