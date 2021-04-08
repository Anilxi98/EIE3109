package hk.polyu.eie.eie3109.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int MODE = Context.MODE_PRIVATE;
    public static final String PREFERENCE_NAME = "MyProfile";
    private SharedPreferences sharedPreferences;
    private EditText ETName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView TV_title = findViewById(R.id.TV_title);

        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE);
        String title = "Hi " + sharedPreferences.getString("Name","Hello World!");
        if (TV_title != null){
            TV_title.setText(title);
        }
        ETName = findViewById(R.id.ETName);
        Button BNOK = findViewById(R.id.BNOK);

        if(BNOK != null){
            BNOK.setOnClickListener(new View.OnClickListener(){
                @Override
               public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Name", ETName.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                    finish();
               }
            });
        }
    }
}