package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TaskAddition extends AppCompatActivity {

    Database database;
    EditText title, note;
    ImageButton saveButton, backButton;
    Date dateObj;
    SimpleDateFormat sDate;
    String id, date, titleData, noteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_addition);

        Intent startIntent = getIntent();

        initialize();
        setListeners();
    }

    @SuppressLint("SimpleDateFormat")
    public void initialize() {
        database = new Database(this);
        title = (EditText) findViewById(R.id.editText1);
        note = (EditText) findViewById(R.id.editText);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        dateObj = new Date();
        sDate = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        date = sDate.format(dateObj);
        id = UUID.randomUUID().toString();
    }

    public void setListeners() {
        saveButton.setOnClickListener(view->{readAndSaveData();});
        backButton.setOnClickListener(view->{mainPage();});
    }

    public void readAndSaveData() {
        titleData = title.getText().toString();
        noteData = note.getText().toString();

        if(titleData.length()>0 && noteData.length()>0) {
            Boolean check = database.insertData(id, titleData, noteData, date);
            if (check) {
                Toast.makeText(this, "Note successfully saved", Toast.LENGTH_LONG).show();
                title.setText("");
                note.setText("");
                mainPage();
            }
            else
                Toast.makeText(this, "Note could not be saved", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Title and Note cannot be left empty", Toast.LENGTH_LONG).show();
    }

    public void mainPage() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}