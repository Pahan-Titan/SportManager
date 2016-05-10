package com.tanat.ua.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Integer>  ID;
    public static ArrayList<String> NAME_PERIOD, START_WEGIHT, END_WEGIHT, DATE, DESCRIPRION_TEXT;
    Button button_add, button_change, button_open;
    Spinner spinner;
    TextView text_des;
    static DataBase dataBase;
    static SQLiteDatabase db;
    String[] NAME_LIST;

    public void loadPeriodsTable(){
        dataBase = new DataBase(this);
        db = dataBase.getReadableDatabase();
        //db = dataBase.getWritableDatabase();

        Cursor cursor = db.query("Periods", null, null, null, null, null, "_id");
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");
            int startWegihtIndex = cursor.getColumnIndex("start_wegiht");
            int endWegihtIndex = cursor.getColumnIndex("end_wegiht");
            int dateIndex = cursor.getColumnIndex("date");
            int descriptionIndex = cursor.getColumnIndex("description");

            ID = new ArrayList<Integer>();
            NAME_PERIOD = new ArrayList<String>();
            START_WEGIHT = new ArrayList<String>();
            END_WEGIHT = new ArrayList<String>();
            DATE = new ArrayList<String>();
            DESCRIPRION_TEXT = new ArrayList<String>();

            int i = 0;
            do {
                ID.add(cursor.getInt(idIndex));
                NAME_PERIOD.add(cursor.getString(nameIndex));
                START_WEGIHT.add(cursor.getString(startWegihtIndex));
                END_WEGIHT.add(cursor.getString(endWegihtIndex));
                DATE.add(cursor.getString(dateIndex));
                DESCRIPRION_TEXT.add(cursor.getString(descriptionIndex));

                Log.d("mLog",
                        "ID = " + ID.get(i)
                                + ", name = " + NAME_PERIOD.get(i)
                                + ", date = " + DATE.get(i));

                i++;
            } while (cursor.moveToNext());
        } else
            Log.d("mLog", "0 rows");
        dataBase.close();
    }

    protected void upgrade(int item_num){
        NAME_LIST = NAME_PERIOD.toArray(new String[NAME_PERIOD.size()]);

        text_des.setText("Start wegiht: " + START_WEGIHT.get(item_num)
                + "\nEnd wegiht: " + END_WEGIHT.get(item_num)
                + "\nDate: " + DATE.get(item_num)
                + "\n" + DESCRIPRION_TEXT.get(item_num));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NAME_LIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(item_num);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(this);
        button_change = (Button) findViewById(R.id.button_change);
        button_change.setOnClickListener(this);
        button_open = (Button) findViewById(R.id.button_open);
        button_open.setOnClickListener(this);

        text_des = (TextView) findViewById(R.id.text_description);

        spinner = (Spinner) findViewById(R.id.spinner);

        loadPeriodsTable();
        upgrade(NAME_PERIOD.size()-1);

 /*     NAME_LIST = NAME_PERIOD.toArray(new String[NAME_PERIOD.size()]);

        int i = NAME_PERIOD.size()-1;
        text_des.setText("Start wegiht: " + START_WEGIHT.get(i)
                + "\nEnd wegiht: " + END_WEGIHT.get(i)
                + "\nDate: " + DATE.get(i)
                + "\n" + DESCRIPRION_TEXT.get(i));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NAME_LIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(NAME_PERIOD.size()-1);*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                upgrade(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CreateNewPeriodActivity.class);
        switch (v.getId()){
            case (R.id.button_add):
                startActivity(intent);
                break;
            case (R.id.button_change):
                intent.putExtra("namePeriodChange",(String)(spinner.getSelectedItem()));
                break;
            case (R.id.button_open):
                intent = new Intent();
                intent.putExtra("namePeriod",(String)(spinner.getSelectedItem()));
                break;

        }
    }
}