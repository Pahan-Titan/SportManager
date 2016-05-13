package com.tanat.ua.sportmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateNewPeriodActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_save, button_delete;
    EditText editName, editWeight, editDate, editDescription;
    static DataBase dataBase;
    static SQLiteDatabase db;
    String date, NAME, OPTION;
    private  String [] months = {"January","February","March","April","May","June","July",
            "August","September","October", "November","December"};
    private boolean insert_bool;
    private static int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_period);

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(this);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_delete.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);
        editWeight = (EditText) findViewById(R.id.editWeight);
        editDate = (EditText) findViewById(R.id.editDate);
        editDescription = (EditText) findViewById(R.id.editDescription);

        dataBase = new DataBase(this);
        db = dataBase.getWritableDatabase();

        Calendar calendar = new GregorianCalendar();
        date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH))
                + " " + months[calendar.get(Calendar.MONTH)]
                + " " + Integer.toString(calendar.get(Calendar.YEAR));
        editDate.setText(date);

        Intent intent = getIntent();

        OPTION = intent.getStringExtra("option");
        NAME = intent.getStringExtra("namePeriod");

        if (OPTION.equals("Add new")){
            setTitle("Add new");
            insert_bool = true;
        }
        if (OPTION.equals("Change")){
            setTitle("Change");
            insert_bool = false;
            loadDate(NAME);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", editName.getText().toString());
        contentValues.put("start_wegiht", editWeight.getText().toString());
        contentValues.put("end_wegiht", 0);
        contentValues.put("date", date);
        contentValues.put("description", editDescription.getText().toString());

/*        switch (v.getId()){
            case R.id.button_save:
                if (option_number == false) {
                    DataBase.insert(contentValues, "periods");
                } else {
                    String where = "_id = " + ID;
                    DataBase.update(contentValues, "periods", where);
                }
                finish();
                break;
            case R.id.button_delete:
                String where = "_id = " + ID;
                DataBase.delete("periods", where);
                finish();
                break;
        }*/
/*нужно реализовать проверку на пустоту*/
        switch (v.getId()){
            case R.id.button_save:
                if (editName.getText().equals(" ")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "The name can not be null", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (insert_bool == true) {
                        insert(contentValues, "periods");
                        intent.putExtra("option", "insert");
                        setResult(RESULT_OK, intent);
                    } else {
                        String where = "_id = " + ID;
                        update(contentValues, "periods", where);
                        intent.putExtra("option", "update");
                        setResult(RESULT_OK, intent);
                    }
                    finish();
                }
                break;
            case R.id.button_delete:
                String where = "_id = " + ID;
                delete("periods", where);
                intent.putExtra("option", "delete");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void loadDate(String name) {
        dataBase = new DataBase(this);
        db = dataBase.getReadableDatabase();

        String selection = "name = " + name;
        Cursor cursor = db.query("Periods", null, selection, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");
            int startWegihtIndex = cursor.getColumnIndex("start_wegiht");
            int dateIndex = cursor.getColumnIndex("date");
            int descriptionIndex = cursor.getColumnIndex("description");

            ID = cursor.getInt(idIndex);
            editName.setText(cursor.getString(nameIndex));
            editWeight.setText(cursor.getString(startWegihtIndex));
            editDescription.setText(cursor.getString(descriptionIndex));
            editDate.setText(cursor.getString(dateIndex));

        } else {
            Log.d("mLog","cursor = null");
        }
 //       dataBase.close();
    }

    public static void insert(ContentValues contentValues, String nameTable){
        db.insert(nameTable, null, contentValues);
    }

    public static void update (ContentValues contentValues, String nameTable, String where){
        db.update(nameTable, contentValues, where, null);

        Log.d("mLog","update " + Integer.toString(db.update("periods", contentValues, where, null)));
    }

    public static void delete (String nameTable, String where){
        db.delete(nameTable, where, null);

        Log.d("mLog","delete " + where);
    }
}