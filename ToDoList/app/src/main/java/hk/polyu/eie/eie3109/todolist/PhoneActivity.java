package hk.polyu.eie.eie3109.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {
    private ListView myPhoneList;
    private SimpleCursorAdapter myCursorAdapter;

    private String PhoneNumberAll = new String("");
    private HashMap<String, String> PhoneList = new HashMap<String, String>();
    private ArrayList<String> NameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        myPhoneList = findViewById(R.id.LVPhoneList);
        showContacts();

        Button BNList = findViewById(R.id.BNListAll);
        if (BNList != null) {
            BNList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), PhoneNumberAll, Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button BNBack = findViewById(R.id.BN_Back);
        if (BNBack != null) {
            BNBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else {
            //Code to query the phone numbers
            //initialize content resolver object to work content provider
            final ContentResolver cr = getContentResolver();

            // Read contacts
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));

                            if (PhoneList.get(name) == null){
                                NameList.add(name);
                                PhoneList.put(name,phoneNo);
                                PhoneNumberAll += "\n" + phoneNo;
                            }
                            else{
                                String tem = PhoneList.get(name);
                                tem = tem + "\n" + phoneNo;
                                PhoneList.put(name,tem);
                                PhoneNumberAll += "\n" + phoneNo;
                            }
                        }
                        pCur.close();
                    }
                }
            }


            myCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item, cur,
                    new String[] {ContactsContract.Contacts.DISPLAY_NAME},
                    new int[] {R.id.TVRow},0);

            myPhoneList.setAdapter(myCursorAdapter);

            //display toast massage
            myPhoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = NameList.get(i);
                    String PhoneNumber = PhoneList.get(name);
                    Log.i("TAG", PhoneNumber);
                    Toast.makeText(getApplicationContext(), PhoneNumber, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Permission is granted
                showContacts();
            }
            else {
                Toast.makeText(getApplicationContext(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}