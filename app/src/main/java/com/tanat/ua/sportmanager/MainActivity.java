package com.tanat.ua.sportmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Integer>  ID = new ArrayList<Integer>();
    public static ArrayList<String> NAME_PERIOD = new ArrayList<String>();
    public static ArrayList<Integer> START_WEGIHT = new ArrayList<Integer>();
    public static ArrayList<Integer> END_WEGIHT = new ArrayList<Integer>();
    public static ArrayList<String> DATE = new ArrayList<String>();
    public static ArrayList<String> DESCRIPRION_TEXT = new ArrayList<String>();
    Button button_add, button_change, button_open;
    Spinner spinner;
    TextView text_des;
    static DataBase dataBase;
    static SQLiteDatabase db;
    String[] NAME_LIST;

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
        if (NAME_PERIOD != null) upgrade(NAME_PERIOD.size()-1);

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
                intent.putExtra("option","Add new");
                startActivityForResult(intent, 0);
                break;
            case (R.id.button_change):
                intent.putExtra("option","Change");
                intent.putExtra("namePeriod",(String)(spinner.getSelectedItem()));
                startActivityForResult(intent, 1);
                break;
            case (R.id.button_open):
                intent.putExtra("namePeriod",(String)(spinner.getSelectedItem()));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent date) {
        super.onActivityResult(requestCode, resultCode, date);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        String toastText = null;

        if (resultCode == RESULT_OK) {
            if (date == null) return;
            String option = date.getStringExtra("option");
            if (option.equals("insert")) {
                loadLastRow();
                upgrade(NAME_PERIOD.size() - 1);
                toastText = "Period added";
            }
            if (option.equals("update")){

                upgrade(NAME_PERIOD.size() - 1);
                toastText = "Period upgrade";
            }
            if (option.equals("delete")) {

                upgrade(NAME_PERIOD.size() - 2);
                toastText = "Removal done";
            }
            toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void loadPeriodsTable(){
        dataBase = new DataBase(this);
        db = dataBase.getWritableDatabase();
        Cursor cursor = db.query("Periods", null, null, null, null, null, "_id");
        if (cursor.moveToFirst()) {
  /*        int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");
            int startWegihtIndex = cursor.getColumnIndex("start_wegiht");
            int endWegihtIndex = cursor.getColumnIndex("end_wegiht");
            int dateIndex = cursor.getColumnIndex("date");
            int descriptionIndex = cursor.getColumnIndex("description");
*/
            int i = 0;
            do {
                ID.add(cursor.getInt(cursor.getColumnIndex("_id")));
                NAME_PERIOD.add(cursor.getString(cursor.getColumnIndex("name")));
                START_WEGIHT.add(cursor.getInt(cursor.getColumnIndex("start_wegiht")));
                END_WEGIHT.add(cursor.getInt(cursor.getColumnIndex("end_wegiht")));
                DATE.add(cursor.getString(cursor.getColumnIndex("date")));
                DESCRIPRION_TEXT.add(cursor.getString(cursor.getColumnIndex("description")));

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

    public void loadLastRow(){
        dataBase = new DataBase(this);
        db = dataBase.getWritableDatabase();
        Cursor cursor = db.query("Periods", null, null, null, null, null, "_id");

        if (cursor.moveToLast()) {
                ID.add(cursor.getInt(cursor.getColumnIndex("_id")));
                NAME_PERIOD.add(cursor.getString(cursor.getColumnIndex("name")));
                START_WEGIHT.add(cursor.getInt(cursor.getColumnIndex("start_wegiht")));
                END_WEGIHT.add(cursor.getInt(cursor.getColumnIndex("end_wegiht")));
                DATE.add(cursor.getString(cursor.getColumnIndex("date")));
                DESCRIPRION_TEXT.add(cursor.getString(cursor.getColumnIndex("description")));

/*            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");
            int startWegihtIndex = cursor.getColumnIndex("start_wegiht");
            int endWegihtIndex = cursor.getColumnIndex("end_wegiht");
            int dateIndex = cursor.getColumnIndex("date");
            int descriptionIndex = cursor.getColumnIndex("description");

            ID.add(cursor.getInt(idIndex));
            NAME_PERIOD.add(cursor.getString(nameIndex));
            START_WEGIHT.add(cursor.getInt(startWegihtIndex));
            END_WEGIHT.add(cursor.getInt(endWegihtIndex));
            DATE.add(cursor.getString(dateIndex));
            DESCRIPRION_TEXT.add(cursor.getString(descriptionIndex));*/

        } else
            Log.d("mLog", "0 rows");
        dataBase.close();
    }

    protected void upgrade(int item_num){
        NAME_LIST = NAME_PERIOD.toArray(new String[NAME_PERIOD.size()]);

        if (NAME_LIST != null){
            text_des.setText("Start wegiht: " + START_WEGIHT.get(item_num)
                    + "\nEnd wegiht: " + END_WEGIHT.get(item_num)
                    + "\nDate: " + DATE.get(item_num)
                    + "\n" + DESCRIPRION_TEXT.get(item_num));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, NAME_LIST);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(item_num);}
    }


}