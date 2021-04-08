package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListActivity extends AppCompatActivity {
    private ListView myToDoList;
    private String[] myStringList;
    private ArrayAdapter<String> myAdapter;
    public static final String PREFERENCE_NAME = "MyProfile";
    public static final String PREFERENCE_PACKAGE = "hk.polyu.eie.eie3109.helloworld";
    public static final int MODE = Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView TVHello = findViewById(R.id.TVHello);

        Context c = null;
        try {
            c = this.createPackageContext(PREFERENCE_PACKAGE, CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = c.getSharedPreferences(PREFERENCE_NAME,MODE);
        String title = "Hi, " + sharedPreferences.getString("Name","Hello World!");
        if (TVHello != null){
            TVHello.setText(title);
        }

        ArrayList<String> myStringList = new ArrayList<String>();
        for (int i=0; i<10; i++) {
            myStringList.add("Empty" + i);
        }
        myStringList.set(0,"Return Books to Library");
        myStringList.remove(1);
        myStringList.set(1,"Meeting with Advisor");

        myToDoList = findViewById(R.id.LVList);
        myToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int item_loc, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                ListView options = new ListView(ListActivity.this);
                options.setAdapter(new ArrayAdapter<String>(ListActivity.this,
                        android.R.layout.simple_list_item_1,
                        new String[] {"Add a new item", "Edit this item", "Reomove this item"}));
                builder.setView(options);

                final Dialog dialog = builder.create();
                dialog.show();

                options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String choose = new String();
                        switch (i) {
                            case 0:
                                choose = "Add";
                                break;
                            case 1:
                                choose = "Edit";
                                break;
                            case 2:
                                choose = "Remove";
                                break;
                        }
                        Toast.makeText(getApplicationContext(),"Item " + Integer.toString(item_loc) +" "+ choose, Toast.LENGTH_SHORT).show();
                        Log.i("TAG", "Run");

                        if (i == 0 || i == 1){
                            final Dialog dialogForm = new Dialog(ListActivity.this);
                            dialogForm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogForm.setContentView(R.layout.form_operation);

                            TextView Title = dialogForm.findViewById(R.id.Title);
                            final EditText ETText = dialogForm.findViewById(R.id.ETText);
                            Button BNSubmit = dialogForm.findViewById(R.id.BN_submit);
                            if(Title != null){
                                if (i == 0){
                                    Title.setText("Add a new item above the item you selected");
                                }
                                else{
                                    Title.setText("Edit this item");
                                }

                            }
                            if (ETText  != null && i == 1){
                                ETText.setText(myStringList.get(item_loc));
                            }
                            if(BNSubmit != null) {
                                BNSubmit.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        if (i == 0){
                                            String add_content = ETText.getText().toString();
                                            myStringList.add(item_loc,add_content);
                                        }
                                        myStringList.set(item_loc,ETText.getText().toString());
                                        myAdapter.notifyDataSetChanged();
                                        dialogForm.dismiss();;
                                        dialog.dismiss();
                                    }
                                });
                            }
                            dialogForm.show();
                        }
                        if (i == 2){
                            final Dialog dialogForm = new Dialog(ListActivity.this);
                            dialogForm.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogForm.setContentView(R.layout.confirm);

                            TextView TConfirm = dialogForm.findViewById(R.id.TextConfirm);
                            Button BNyes = dialogForm.findViewById(R.id.BNyes);
                            Button BNno = dialogForm.findViewById(R.id.BNno);
                            if(TConfirm != null){
                                TConfirm.setText("Are you sure to delete item"+Integer.toString(item_loc)+"?");
                            }
                            if(BNyes != null) {
                                BNyes.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        myStringList.remove(item_loc);
                                        myAdapter.notifyDataSetChanged();
                                        dialogForm.dismiss();;
                                        dialog.dismiss();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        Set<String> set = new HashSet<String>();
                                        set.addAll(myStringList);
                                        editor.putStringSet("List", set);
                                        editor.commit();
                                    }
                                });
                            }
                            if(BNno != null) {
                                BNno.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        dialogForm.dismiss();;
                                        dialog.dismiss();
                                    }
                                });
                            }
                            dialogForm.show();
                        }

                    }
                });


            }
        });
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringList);
        myToDoList.setAdapter(myAdapter);

        Button BNBack = findViewById(R.id.BNBack);
        if (BNBack != null) {
            BNBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}