package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListener{

    TextView emptyText;
    Button addIcon;
    Database database;
    NotesAdapter notesAdapter;
    RecyclerView recyclerView;
    Cursor result;
    NotesData notesData;
    List<NotesData> notesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        loadData();
    }

    public void initializeViews() {
        emptyText = (TextView) findViewById(R.id.textView);
        addIcon = (Button) findViewById(R.id.addButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        database = new Database(this);
    }

    public void fillNotesData(String id, String title, String note, String time) {
        notesData = new NotesData();
        notesData.title = title;
        notesData.note = note;
        notesData.id = id;
        notesData.date = time;
        notesDataList.add(notesData);
    }

    public void loadData() {
        result = database.getData();
        checkEmptiness(result.getCount()==0);
        if(result.getCount()>0) {
            notesDataList = new ArrayList<>();
            while (result.moveToNext()) {
                fillNotesData(result.getString(0), result.getString(1), result.getString(2), result.getString(3));
            }
            notesAdapter = new NotesAdapter(notesDataList, this);
            recyclerView.setAdapter(notesAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void checkEmptiness(Boolean value) {
        if(value)
            emptyText.setVisibility(View.VISIBLE);
        else
            emptyText.setVisibility(View.INVISIBLE);
    }

    public void tasksPage(View view) {
        Intent taskIntent = new Intent(this, TaskAddition.class);
        startActivity(taskIntent);
    }

    @Override
    public void onNotesClicked(NotesData notesData) {
        Intent taskIntent = new Intent(this, TaskAddition.class);
        taskIntent.putExtra("id", notesData.id);
        startActivity(taskIntent);
    }

    @Override
    public void onNotesLongClicked(NotesData notesData, int position) {
        deleteDatabase(notesData, position);
    }

    public void deleteDatabase(NotesData notesList, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to delete the note?");
        builder.setTitle("Delete");
        builder.setCancelable(false);
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int nom) {
                        Boolean check = database.deleteRows(notesList.id);
                        if (check) {
                            Toast.makeText(MainActivity.this, "Note successfully deleted", Toast.LENGTH_LONG).show();
                            loadData();
                            notesAdapter.removeItem(position);
                        }
                        else
                            Toast.makeText(MainActivity.this, "Note could not be deleted", Toast.LENGTH_LONG).show();
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
}