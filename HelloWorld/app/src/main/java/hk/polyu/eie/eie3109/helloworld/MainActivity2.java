package hk.polyu.eie.eie3109.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView TVWelcome = findViewById(R.id.TVWelcome);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFERENCE_NAME,MainActivity.MODE);
        String name = sharedPreferences.getString("Name","Default Name");
        if (TVWelcome != null){
            TVWelcome.setText(name);
        }

        Button BNback = findViewById(R.id.BN_back);

        if(BNback != null) {
            BNback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}