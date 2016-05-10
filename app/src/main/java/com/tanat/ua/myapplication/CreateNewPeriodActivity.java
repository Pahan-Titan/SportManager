package com.tanat.ua.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.GregorianCalendar;

public class CreateNewPeriodActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_save;
    EditText editName, editWeight, editDescription;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_period);

        button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(this);

        editName = (EditText) findViewById(R.id.editName);
        editWeight = (EditText) findViewById(R.id.editWeight);
        editDescription = (EditText) findViewById(R.id.editDescription);

        dataBase = new DataBase(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", editName.getText().toString());
        contentValues.put("start_wegiht", editWeight.getText().toString());
        contentValues.put("end_wegiht", 0);
        contentValues.put("date", new java.util.Date().toString());
        contentValues.put("description", editDescription.getText().toString());
        db.insert("periods", null, contentValues);
        finish();
    }
}