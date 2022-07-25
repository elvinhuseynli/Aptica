package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
    ImageButton saveButton, backButton, deleteButton;
    Date dateObj;
    SimpleDateFormat sDate;
    String id, date, titleData, updateId, noteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_addition);

        Intent startIntent = getIntent();
        Bundle extras = startIntent.getExtras();
        if(extras!=null)
            updateId = extras.getString("id");

        initialize();
        setListeners();

        if(updateId != null){
            loadData(updateId);
        }
    }

    public void loadData(String updateId) {
        Cursor result = database.getCurrentDataId(updateId);
        result.moveToNext();
        title.setText(result.getString(1));
        note.setText(result.getString(2));
        date = result.getString(3);
        saveButton.setOnClickListener(view -> updateDatabase());
        deleteButton.setOnClickListener(view -> deleteDatabase());
    }

    @SuppressLint("SimpleDateFormat")
    public void initialize() {
        database = new Database(this);
        title = (EditText) findViewById(R.id.editText1);
        note = (EditText) findViewById(R.id.editText);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton);
        dateObj = new Date();
        sDate = new SimpleDateFormat("dd MMM yyyy HH:mm");
        date = sDate.format(dateObj);
        id = UUID.randomUUID().toString();
    }

    public void setListeners() {
        saveButton.setOnClickListener(view->{readAndSaveData();});
        backButton.setOnClickListener(view->{mainPage();});
        deleteButton.setOnClickListener(view->{deleteData();});
    }

    @SuppressLint("SimpleDateFormat")
    public void updateDatabase() {
        titleData = title.getText().toString();
        noteData = note.getText().toString();
        dateObj = new Date();
        sDate = new SimpleDateFormat("dd MMM yyyy HH:mm");
        date = sDate.format(dateObj);
        if(titleData.length()>0 && noteData.length()>0) {
            Boolean check = database.updateDatabase(updateId, titleData, noteData, date);
            if (check) {
                Toast.makeText(this, "Note successfully updated", Toast.LENGTH_LONG).show();
                title.setText("");
                note.setText("");
                mainPage();
            }
            else
                Toast.makeText(this, "Note could not be updated", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Title and Note cannot be left empty", Toast.LENGTH_LONG).show();
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

    public void deleteDatabase() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskAddition.this);
        builder.setMessage("Are you sure you want to delete the note?");
        builder.setTitle("Delete");
        builder.setCancelable(false);
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int nom) {
                        Boolean check = database.deleteRows(updateId);
                        if (check) {
                            Toast.makeText(TaskAddition.this, "Note successfully deleted", Toast.LENGTH_LONG).show();
                            title.setText("");
                            note.setText("");
                            mainPage();
                        }
                        else
                            Toast.makeText(TaskAddition.this, "Note could not be deleted", Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int nom) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskAddition.this);
        builder.setMessage("Are you sure you want to delete the note?");
        builder.setTitle("Delete");
        builder.setCancelable(false);
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int nom) {
                        Toast.makeText(TaskAddition.this, "Note successfully deleted", Toast.LENGTH_LONG).show();
                        title.setText("");
                        note.setText("");
                        mainPage();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int nom) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mainPage() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}