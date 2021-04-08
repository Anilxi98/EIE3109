package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "MyProfile";
    public static final String PREFERENCE_PACKAGE = "hk.polyu.eie.eie3109.helloworld";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context c = null;
        try {
            c = this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREFERENCE_NAME,MODE);
        String name = sharedPreferences.getString("Name", "Default Name");

        TextView TVName = findViewById(R.id.TVName);
        if (TVName != null){
            TVName.setText(name);
        }

        Button BNList = findViewById(R.id.BNList);
        if (BNList != null){
            BNList.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        Button BNPhone = findViewById(R.id.BNPhone);
        if (BNPhone != null){
            BNPhone.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}